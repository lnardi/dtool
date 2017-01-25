package br.com.mavalu.dtool.export;

import br.com.mavalu.dtool.DtoolJFrame;
import br.com.mavalu.dtool.control.DtoolLogControl;
import br.com.mavalu.useful.DocumentumUseful;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.documentum.fc.common.DfException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

public class ExportControl extends Thread {

    private static Logger logger = Logger.getLogger(ExportControl.class.getName());

    private int item = 0;
    private int erros = 0;
    private boolean active = true;
    private int numberOfTryGetNextLine;
    private int numberOfThreads = 0;
    private List<DocumentumExportControl> dIControlTheadList = null;
    private int processedLine = 0;
    private Iterator<String[]> inputsLines = null;
    private int dctmFolderExtruture = 0;
    private boolean expAllInFolderOrLikeServer = false;
    private String csvFileName = null;
    private String header = null;
    private int columnID = 0;
    private Queue<TrheadDocPack> processedLines = new LinkedList<TrheadDocPack>();
    private BufferedWriter csvOutput = null;
    private BufferedWriter csvErrorOutput = null;
    private int fileNumber = 1;//Define o número do arquivo csv
    private long breakCSV = 0;
    private DtoolJFrame dtoolJFrame = null;
    private String exportPath = null;

    public ExportControl(Iterator p_inputsLines, String p_csvFile, String p_header, int p_dctmFolderExtruture, boolean p_expAllInFolderOrLikeServer, int p_columnID, long p_breakCSV, DtoolJFrame p_dtoolJFrame, Boolean p_expFolder) throws IOException, DfException {

        inputsLines = p_inputsLines;
        dctmFolderExtruture = p_dctmFolderExtruture;
        expAllInFolderOrLikeServer = p_expAllInFolderOrLikeServer;
        csvFileName = p_csvFile;
        header = p_header;
        columnID = p_columnID;
        breakCSV = p_breakCSV;
        dtoolJFrame = p_dtoolJFrame;

        numberOfThreads = 1;
        numberOfTryGetNextLine = 4;
        dIControlTheadList = new ArrayList<DocumentumExportControl>();
        exportPath = (new File(csvFileName)).getParent();//Caminho onde os arquivos serão exportados
        if (p_expFolder) {
            exportPath = exportPath + "\\FILES";
        }
        loadThreads();

    }

    public String getPath() {
        return exportPath;
    }

    public boolean getExpAllInFolderOrLikeServer() {
        return expAllInFolderOrLikeServer;
    }

    public int getDctmFolderExtruture() {
        return dctmFolderExtruture;
    }

    public int geColumnID() {
        return columnID;
    }

    private void loadThreads() throws IOException, DfException {

        DocumentumExportControl dIC = null;
        for (int i = 0; i < numberOfThreads; i++) {
            dIC = new DocumentumExportControl(this, DocumentumUseful.getDocbase(), DocumentumUseful.getIDfLoginInfo());
            dIControlTheadList.add(dIC);
        }
    }

    public void startExportThreads() {

        for (DocumentumExportControl i : dIControlTheadList) {
            i.start();
        }
    }

    public int getTryGetNextLine() {
        return numberOfTryGetNextLine;

    }

    public boolean isActive() {
        return active;

    }

    public synchronized void increaseProcessedLine() {
        processedLine++;
    }

    public List<DocumentumExportControl> getDIControlTheadList() {
        return dIControlTheadList;
    }

    public void updateStatistics() {
        for (DocumentumExportControl i : dIControlTheadList) {
            i.updateStatistics();
        }
    }

    public int getNumberOfThreads() {
        return dIControlTheadList.size();

    }

    public synchronized TrheadDocPack getNextLine() {

        TrheadDocPack tdc = new TrheadDocPack();

        if (inputsLines.hasNext()) {
            String[] row = inputsLines.next();
            tdc.item = ++item;
            tdc.id = row[columnID];
            tdc.line = String.valueOf(item);//Número da linha
            //Copia os valores da linha para gerar o output
            for (int i = 0; i < row.length; i++) {
                tdc.line += ";" + row[i].replaceAll("\\r|\\n", " ");
            }

            processedLines.offer(tdc);
        }
        return tdc;
    }

    public void stopThreads() {

        for (DocumentumExportControl i : dIControlTheadList) {
            i.setStop();
        }
        //Aguarda todas as threads finalizarem;
        boolean isActive = true;

        //inativa a thread de entrada
        active = false;

        //Garante que todas as importação sendo processadas finalizaram e todas as threads inativaram.
        while (isActive) {
            isActive = false;

            for (DocumentumExportControl i : dIControlTheadList) {
                isActive = i.isActive() || isActive;
            }

        }
    }

    public DocumentumExportControl newInportTread() throws IOException, DfException {
        DocumentumExportControl dIC = new DocumentumExportControl(this, DocumentumUseful.getDocbase(), DocumentumUseful.getIDfLoginInfo());
        dIControlTheadList.add(dIC);
        dIC.start();
        return dIC;

    }

    public void pauseThreads() {
        for (DocumentumExportControl i : dIControlTheadList) {
            i.setPause(true);
        }
        active = false;
    }

    public void resumeThreads() {
        for (DocumentumExportControl i : dIControlTheadList) {
            i.setPause(false);
        }
        active = true;
    }

    @Override
    public void run() {
        try {
            while (isActive()) {
                while (processedLines.peek().processed) {

                    TrheadDocPack tdp = processedLines.poll();
                    if (tdp.processed) {
                        if (tdp.success) {
                            processSucess(tdp);
                        } else {
                            processError(tdp);
                        }
                    }

                    dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT_COUNT, false, new String[]{"" + item + " - Erros: " + erros
                    });

                }
                sleep(100);
            }
            //Finaliza os arquivos
            if (csvErrorOutput != null) {
                csvErrorOutput.flush();
                csvErrorOutput.close();
            }
            csvOutput.flush();
            csvOutput.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void processSucess(TrheadDocPack tdp) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        //SE houver necessidade de quebrar oas arquivos 
        File fileOutput = null;
        if (csvOutput == null) {
            fileOutput = new File(getOutputNextFileName());
            csvOutput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput), "ISO-8859-1"));
            csvOutput.write(header);
            csvOutput.newLine();
        }

        //Escreve a linha retornada
        csvOutput.write(tdp.line);
        csvOutput.newLine();
        //Se ainda houverem linhas para retornar, cria um novo arquivo
        if (processedLines.size() > 0 && breakCSV > 0 && (item % breakCSV) == 0) {

            //Finaliza o arquivo atual
            csvOutput.flush();
            csvOutput.close();
            //Cria o próximo arquivo
            fileOutput = new File(getOutputNextFileName());
            csvOutput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput), "ISO-8859-1"));
            //Insere o cabeçalho na próxima planilha
            csvOutput.write(header);
            csvOutput.newLine();
        }
    }

    private String getOutputNextFileName() {
        String fn;
        if (breakCSV > 0) {
            fileNumber++;//Atualiza o contador
            String afterDot = csvFileName.substring(csvFileName.length() - 4);
            String beforeDot = csvFileName.substring(0, csvFileName.length() - 4);
            fn = beforeDot + "_" + fileNumber + afterDot;
        } else {
            fn = csvFileName;
        }

        return fn;
    }

    private String getErrorOutputFileName() {
        String afterDot = csvFileName.substring(csvFileName.length() - 4);
        String beforeDot = csvFileName.substring(0, csvFileName.length() - 4);
        return beforeDot + "_ERROR" + afterDot;
    }

    private void processError(TrheadDocPack tdp) throws UnsupportedEncodingException, IOException {
        DtoolLogControl.log("Erro da linha: " + tdp.line, Level.WARNING);
        String error = tdp.error.replace(";", "-");
        tdp.line += ";/////////ERROR////////;" + error;
        DtoolLogControl.log(error, Level.WARNING);
        ++erros;
        if (csvErrorOutput == null) {
            File fileOutputError = new File(getErrorOutputFileName());
            csvErrorOutput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputError), "ISO-8859-1"));
            csvErrorOutput.write("Item;r_object_id;error");
            csvErrorOutput.newLine();
        }
        csvErrorOutput.write(item + ";" + tdp.id + ";" + error);

        csvErrorOutput.newLine();

    }

    public int getWaitingProcessing() {
        return processedLines.size();
    }
}
