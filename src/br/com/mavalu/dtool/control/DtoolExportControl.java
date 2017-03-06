/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.control;

import br.com.mavalu.dtool.DtoolJFrame;
import br.com.mavalu.dtool.export.ExportControl;
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
     * @param expAllInFolderOrLikeServer Se exportContent for true, e este
     * parametro também for True o conteúdo deve ser importado dentro de um
     * folder. Se false, o conteúdo deve ser expotado na mesma extrutura do
     * servidor.
     * @param dctmFolderExtruture Se expAllInFolderOrLikeServer é false,
     * especifica qual das possíveis extruturas deve ser exportada. Caso a
     * estrutura excolhida não exista, exportara a extrutura
     */
    public static void exportQueryGrid(String csvFile, List<String[]> rowsList, List<String> columnsList, boolean exportContent, boolean expAllInFolderOrLikeServer, int dctmFolderExtruture, DtoolJFrame dtoolJFrame, long breakCSV, boolean exportServerPath, boolean breakCSVByIchronicleid) throws FileNotFoundException, UnsupportedEncodingException, IOException, Exception {

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
        String columnI_chronicle_id = "i_chronicle_id";
        int columnR_idFound = -1;
        int columnI_chronicle_idFound = -1;
        boolean breakCSVTemp = false;
        boolean columnNameFound = false;
        long item = 0; //Númera as lihas exportadas 
        long erros = 0;//Conta o número de exports com erro;

        dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT_COUNT, false, new String[]{"" + item + " - Erros: " + erros
        });

        String line = "Item";
        String header = "Item";
        String value = "";
        //Processa a lista de colunas
        for (int i = 0; i < columnsList.size(); i++) {

            value = columnsList.get(i);
            header += ";" + value;

            //Valida se as colunas estão presentes
            if (value.equals(columnI_chronicle_id)) {
                columnI_chronicle_idFound = i;//Utilizo a posição para pegar o valor depois na linha;
            }
            if (value.equals(columnR_id)) {
                columnR_idFound = i;//Utilizo a posição para pegar o valor depois na linha;
            }
            if (value.equals(columnName)) {
                columnNameFound = true;
            }
        }

        if (exportServerPath) {
            header += ";server_path";
        }

        if (exportContent) {
            if (!columnNameFound || columnR_idFound < 0) {
                throw new Exception("Para exportação do conteúdo, a query deve retornar os campos Object_name e r_object_id");
            }
            if (breakCSVByIchronicleid && columnI_chronicle_idFound < 0) {
                throw new Exception("Para exportação quebrando CSV por I_chronicle_id a coluna deve existir");
            }
            header += ";file_path;error";

        }

        csv.write(header);
        csv.newLine();

        Iterator<String[]> rows = rowsList.iterator();
        String[] row = null;
        String lastChronicle_id = null;

        //Processa os arquivos retornados
        while (rows.hasNext()) {

            lastChronicle_id = row != null && columnI_chronicle_idFound != -1 ? row[columnI_chronicle_idFound] : null;

            row = rows.next();

            //Gera um novo arquivo se o usuário solicitou quebra e se é para quebrar por chronicle_id
            if (rows.hasNext() && breakCSV > 0 && item != 0 && ((item % breakCSV) == 0 || breakCSVTemp)) {

                if (breakCSVByIchronicleid && row[columnI_chronicle_idFound].equals(lastChronicle_id)) {//Finaliza o arquivo atual
                    breakCSVTemp = true;//Verifica novamente na próxima linha
                } else {
                    csv.flush();
                    csv.close();
                    //Cria o próximo arquivo
                    fileNumber++;
                    finalName = beforeDot + "_" + fileNumber + afterDot;
                    fileOutput = new File(finalName);
                    csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput), "ISO-8859-1"));
                    //Insere o cabeçalho na próxima planilha
                    csv.write(header);
                    csv.newLine();

                    breakCSVTemp = false; //Retorna ao contador original do break
                }
            }

            ++item;
            line = String.valueOf(item);//Número da linha
            //Copia os valores da linha para gerar o output
            for (int i = 0; i < row.length; i++) {
                line += ";" + row[i].replaceAll("\\;|\\r|\\n", " ");
            }

            String path = "";
            //Exporta o conteúdo
            try {
                //carrega o caminho do servidor     
                if (exportServerPath) {
                    path = DocumentumUseful.apiExecSize(row[columnR_idFound]);
                    line += ";" + path;
                }

                //Aloca thread, se não houver thread disponível, aguarda uma.
                if (exportContent) {
                    //exporta o arquivo
                    path = DocumentumUseful.exportDocument(fileOutput.getParent(), row[columnR_idFound], item, dctmFolderExtruture, expAllInFolderOrLikeServer);
                    line += ";" + path;
                }
                //Exibe o andamento para cada x itens processados
                //if ((item % 10L) == 0) {
                dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT_COUNT, false, new String[]{"" + item + " - Erros: " + erros
                });

                //Percorre a fila de retorno, verificando se o item da frente já foi retornado, isso garante que o output sempre seja o mesmo da query na tela.                
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
     * Expotar o resultado apresentado no grid utilizando Thread
     *
     * @param csvFile Path para o arquivo que receberá os indices
     * @param rowsList Lista de linhas retornada pela query
     * @param columnsList Lista de colunas retornada pela query
     * @param exportContent Se True, esportar o conteúdo
     * @param expAllInFolderOrLikeServer Se exportContent for true, e este
     * parametro também for True o conteúdo deve ser importado dentro de um
     * folder. Se false, o conteúdo deve ser expotado na mesma extrutura do
     * servidor.
     * @param dctmFolderExtruture Se expAllInFolderOrLikeServer é false,
     * especifica qual das possíveis extruturas deve ser exportada. Caso a
     * estrutura excolhida não exista, exportara a extrutura
     */
    public static ExportControl exportQueryGridThreads(String csvFile, List<String[]> rowsList, List<String> columnsList, boolean exportContent, boolean expAllInFolderOrLikeServer, int dctmFolderExtruture, DtoolJFrame dtoolJFrame, long breakCSV, boolean expFolder, String p_exportPath, long p_numberOfThreads) throws FileNotFoundException, UnsupportedEncodingException, IOException, Exception {

        if (exportContent) {
            DtoolLogControl.log("Iniciando processo de Exportação (COM CONTEÚDO)", Level.INFO);
        } else {
            DtoolLogControl.log("Iniciando processo de Exportação ", Level.INFO);
        }

        String columnR_id = "r_object_id";
        String columnName = "object_name";
        int columnR_idFound = -1;
        boolean columnNameFound = false;

        String line = "Item";
        String header = "Item";
        String value = "";
        //Processa a lista de colunas
        for (int i = 0; i < columnsList.size(); i++) {

            value = columnsList.get(i);
            header += ";" + value;

            //Valida se as colunas estão presentes
            if (value.equals(columnR_id)) {
                columnR_idFound = i;//Utilizo a posição para pegar o valor depois na linha;
            }
            if (value.equals(columnName)) {
                columnNameFound = true;
            }
        }

        if (!columnNameFound || columnR_idFound < 0) {
            throw new Exception("Para exportação do conteúdo, a query deve retornar os campos Object_name e r_object_id");
        }

        header += ";file_path;error";

        Iterator<String[]> rows = rowsList.iterator();

        // Chamdo o 
        //public ExportControl(Iterator p_inputsLines, String p_path, int p_dctmFolderExtruture, boolean p_expAllInFolderOrLikeServer, int p_columnID) throws IOException, DfException {
        return new ExportControl(rows, rowsList.size(), csvFile, header, dctmFolderExtruture, expAllInFolderOrLikeServer, columnR_idFound, breakCSV, dtoolJFrame, expFolder, p_exportPath, p_numberOfThreads);

    }

}
