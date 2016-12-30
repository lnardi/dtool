/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.control;

import br.com.mavalu.dtool.DtoolJFrame;
import br.com.mavalu.useful.DocumentumUseful;
import com.documentum.fc.common.DfException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author q1D55V6G
 */
public class DtoolExportControl {

   public static void processExport(File path, List<String[]> rowList, List<String> columnList, int column) throws IOException, DfException {

      //Executa a query, se já não foi executada.
      //Para cada documento, escreve o arquivo csv.
      //Verifica se o folder existe.
      //Exporta os arquivos.
      //DocumentumUseful.exportDocument(path, rowList, column);
   }

   /**
    * Expotar o resultado apresentado no grid
    *
    * @param csvFile Path para o arquivo que receberá os indices
    * @param rowsList Lista de linhas retornada pela query
    * @param columnsList Lista de colunas retornada pela query
    * @param exportContent Se True, esportar o conteúdo
    * @param expAllInFolderOrLikeServer Se exportContent for true, e este parametro também for True o conteúdo deve ser
    * importado dentro de um folder. Se false, o conteúdo deve ser expotado na mesma extrutura do servidor.
    * @param dctmFolderExtruture Se expAllInFolderOrLikeServer é false, especifica qual das possíveis extruturas deve
    * ser exportada. Caso a estrutura excolhida não exista, exportara a extrutura
    */
   public static void exportQueryGrid(String csvFile, List<String[]> rowsList, List<String> columnsList, boolean exportContent, boolean expAllInFolderOrLikeServer, int dctmFolderExtruture, DtoolJFrame dtoolJFrame, long breakCSV) throws FileNotFoundException, UnsupportedEncodingException, IOException, Exception {

      long startTime = System.currentTimeMillis();
      if (exportContent) {
         DtoolLogControl.log("Iniciando processo de Exportação (COM CONTEÚDO)", Level.INFO);
      } else {
         DtoolLogControl.log("Iniciando processo de Exportação ", Level.INFO);
      }

      //SE houver necessidade de quebrar oas arquivos 
      BufferedWriter csvError = null;
      String afterDot = null;
      String beforeDot = null;
      String finalName = null;
      String finalNameError = null;
      int fileNumber = 1;
      afterDot = csvFile.substring(csvFile.length() - 4);
      beforeDot = csvFile.substring(0, csvFile.length() - 4);
      finalNameError = beforeDot + "_ERROR" + afterDot;

      if (breakCSV > 0) {
         finalName = beforeDot + "_" + fileNumber + afterDot;
      } else {
         finalName = csvFile;
      }

      File fileOutput = new File(finalName);
      File fileOutputError = new File(finalNameError);

      BufferedWriter csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput), "ISO-8859-1"));

//new java.io.BufferedWriter(new java.io.FileWriter(file));
      String columnR_id = "r_object_id";
      String columnName = "object_name";
      int columnR_idFound = -1;
      boolean columnNameFound = false;
      long item = 0; //Númera as lihas exportadas 
      long erros = 0;//Conta o número de exports com erro;

      dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT_COUNT, false, new String[]{"" + item + " - Erros: " + erros
      });

      String line = "Item";
      String value = "";
      //Processa a lista de colunas
      for (int i = 0; i < columnsList.size(); i++) {

         value = columnsList.get(i);
         line += ";" + value;

         //Valida se as colunas estão presentes
         if (value.equals(columnR_id)) {
            columnR_idFound = i;//Utilizo a posição para pegar o valor depois na linha;
         }
         if (value.equals(columnName)) {
            columnNameFound = true;
         }
      }

      //TODO - Após embratel remover o item anomalia
      //line += ";anomalia;file_path";
      if (exportContent) {
         if (!columnNameFound || columnR_idFound < 0) {
            throw new Exception("Para exportação do conteúdo, a query deve retornar os campos Object_name e r_object_id");
         }

         line += ";file_path;error";

      }

      csv.write(line);
      csv.newLine();

      Iterator<String[]> rows = rowsList.iterator();
      String[] row = null;

      //Processa os arquivos retornados
      while (rows.hasNext()) {
         row = rows.next();
         ++item;
         line = String.valueOf(item);//Número da linha
         //Copia os valores da linha para gerar o output
         for (int i = 0; i < row.length; i++) {
            line += ";" + row[i];
         }

         String path = "";
         //Exporta o conteúdo
         try {

            //TODO - remover após embratel
           // String nomeOffline = row[2];
           // String nomeOffLineSprited[] = nomeOffline.split("#");
          //  String pathOffline = ".\\" + nomeOffLineSprited[0] + "#" + nomeOffLineSprited[1] + "\\" + nomeOffline;
          //  line += ";" + DocumentumUseful.getAnomalia(row[columnR_idFound]) + ";" + pathOffline;

            /////
            if (exportContent) {
               //exporta o arquivo
               path = DocumentumUseful.exportDocument(fileOutput.getParent(), row[columnR_idFound], item, dctmFolderExtruture, expAllInFolderOrLikeServer);

               line += ";" + path;
            }
            //Exibe o andamento para cada x itens processados
            //if ((item % 10L) == 0) {
            dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT_COUNT, false, new String[]{"" + item + " - Erros: " + erros
            });

            csv.write(line);
            csv.newLine();
            //}

         } catch (DfException ex) {

            String error = ex.getMessage().replace(";", "-");
            line += ";/////////ERROR////////;" + error;
            DtoolLogControl.log("Erro da linha: " + line, Level.WARNING);
            DtoolLogControl.log(ex, Level.WARNING);
            ++erros;

            if (csvError == null) {
               csvError = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputError), "ISO-8859-1"));
               csvError.write("Item;r_object_id;error");
               csvError.newLine();
            }
            csvError.write(item + ";" + row[columnR_idFound] + ";" + error);

            csvError.newLine();

         }

         if (rows.hasNext() && breakCSV > 0 && (item % breakCSV) == 0) {

            //Finaliza o arquivo atual
            csv.flush();
            csv.close();
            //Cria o próximo arquivo
            fileNumber++;
            finalName = beforeDot + "_" + fileNumber + afterDot;
            fileOutput = new File(finalName);
            csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput), "ISO-8859-1"));
         }

      }

      if (csvError != null) {

         csvError.flush();

         csvError.close();
      }

      csv.flush();

      csv.close();

      DtoolLogControl.log("Foram exportados: " + (item - erros) + " items. Erros encontrados: " + erros, Level.INFO);

      DtoolLogControl.log("Processo de Exportação finalizado com sucesso - Tempo: " + ((System.currentTimeMillis() - startTime) / 1000) + " Segundos", Level.INFO);

   }

   /**
    * Executa a query e expotar o resultado
    *
    * @param query Query a ser executada
    * @param csvFile Path para o arquivo que receberá os indices
    * @param exportContent Se True, esportar o conteúdo
    * @param expAllInFolderOrLikeServer Se exportContent for true, e este parametro também for True o conteúdo deve ser
    * importado dentro de um folder. Se false, o conteúdo deve ser expotado na mesma extrutura do servidor.
    */
   public static void exportQuery(String query, String csvFile, boolean exportContent, boolean expAllInFolderOrLikeServer) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

}
