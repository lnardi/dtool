package br.com.mavalu.dtool.export;

import br.com.mavalu.dtool.control.DtoolLogControl;
import br.com.mavalu.useful.DocumentumUsefulThreadSafe;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentumExportControl extends Thread {

    private ExportControl mc = null;
    private boolean isActive = false;
    private boolean isActiveRealTime = false;
    private HashSet<String[]> attributeList = new HashSet<String[]>();

    private DocumentumUsefulThreadSafe documentumUseful = null;
    private HashMap<String, String> formatList = new HashMap<String, String>();
    private HashMap<String, String> pathList = new HashMap<String, String>();
    private boolean pause = false;
    private boolean pauseRealTime = false;
    private long fileProcessedNumber = 0;
    private long lastFileProcessedNumber = 0;
    private long[] totalHourArray = new long[60];
    private long[] totalSizeHourArray = new long[60];
    private long[] totalMinArray = new long[60];
    private long[] totalSizeMinArray = new long[60];

    private int second = 0;
    private int minute = 0;
    private long totalHour = 0;
    private long totalMin = 0;
    private long totalSizeHour = 0;
    private long totalSizeMin = 0;

    private TrheadDocPack tdc = null;
    private String status = "Inativa";
    private long fileProcessedSize = 0;
    private long lastFileProcessedSize = 0;
    private String exportId = "";
    private int item = -1;

    public DocumentumExportControl(ExportControl migrationControl, String docbase, IDfLoginInfo li) throws IOException, DfException {

        mc = migrationControl;

        documentumUseful = new DocumentumUsefulThreadSafe();
        documentumUseful.login(docbase, li);
    }

    @Override
    public void run() {

        String id = null;
        isActive = true;
        isActiveRealTime = true;
        try {
            status = "Login Documentum";
            documentumUseful.loadSession();

            while (isActive) {

                if (!pause) {
                    status = "Processando";
                    tdc = getNextLine();
                    if (tdc != null && tdc.line != null) {
                        try {
                            String path = null;
                            //Guarda o ID para Report
                            exportId = tdc.id;
                            item = tdc.item;
                            if (mc.exportServerPath()) {
                                path = documentumUseful.apiExecSize(tdc.id);
                            } else {
                                //exporta o conteúdo da docbase                                
                                path = documentumUseful.exportDocument(mc.getPath(), mc.getRelativePath(), tdc, mc.getDctmFolderExtruture(), mc.getExpAllInFolderOrLikeServer());
                            }
                            tdc.line += ";" + path;
                            fileProcessedNumber++;
                            fileProcessedSize += tdc.size;
                        } catch (DfException ex) {
                            tdc.success = false;
                            DtoolLogControl.log(ex, Level.SEVERE);
                            String error = ex.getMessage().replace(";", "-");
                            tdc.line += ";/////////ERROR////////;" + error;
                            tdc.error = error;
                        } catch (Exception ex) {
                            DtoolLogControl.log(ex, Level.SEVERE);
                            setStop();
                        } finally {
                            tdc.processed = true;
                        }
                    } else {
                        setStop();
                    }
                } else {
                    status = "Pausada";
                    sleep(3000);
                }
                isActiveRealTime = isActive;
                pauseRealTime = pause;
            }

            status = "Finalizada";

        } catch (InterruptedException ex) {

            DtoolLogControl.log(ex, Level.SEVERE);

        } catch (DfException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
        }

    }

    private void destroyContent(String id) throws DfException {

        documentumUseful.destroy(id);

    }

    private boolean updateLine(String id) {
        // TODO Auto-generated method stub
        return true;
    }

    private TrheadDocPack getNextLine() throws InterruptedException {

        int tryGetNextLine = mc.getTryGetNextLine();
        int time = 300;
        TrheadDocPack tdc = null;
        for (int i = 1; i <= tryGetNextLine && tdc == null; i++) {
            tdc = mc.getNextLine();
            if (tdc == null && isActive) {
                sleep(time * i);
            }

        }
        return tdc;
    }

    public synchronized void setStop() {
        if (this.isActive) {
            status = "Finalizando";
        }
        this.isActive = false;
    }

    public synchronized void setPause(boolean p) {
        this.pause = p;
        if (this.pause) {
            status = "Aguarndando processamento";
        }
    }

    public boolean getPause() {
        return this.pause;
    }

    public long getFileProcessedNumber() {
        return fileProcessedNumber;
    }

    public long getFileProcessedSize() {
        return fileProcessedSize;
    }

    /**
     * Deve ser executada a cada segundo
     */
    public void updateStatistics() {

        long lastSecondProcessedNumber = fileProcessedNumber - lastFileProcessedNumber;
        long lastSecondProcessedSize = fileProcessedSize - lastFileProcessedSize;

        if (second == 60) {
            second = 0;
        }
        if (minute == 60) {
            minute = 0;
        }
        totalMin = (totalMin - totalMinArray[second]) + lastSecondProcessedNumber;
        totalSizeMin = (totalSizeMin - totalSizeMinArray[second]) + lastSecondProcessedSize;
        totalMinArray[second] = lastSecondProcessedNumber;
        totalSizeMinArray[second] = lastSecondProcessedSize;

        totalHour = (totalHour - totalHourArray[minute]) + totalMin;
        totalSizeHour = (totalSizeHour - totalSizeHourArray[minute]) + totalSizeMin;
        totalHourArray[minute] = totalMin;
        totalSizeHourArray[minute] = totalSizeMin;

        if (second == 59) {
            minute++;
        }

        second++;

        lastFileProcessedNumber = fileProcessedNumber;
        lastFileProcessedSize = fileProcessedSize;
    }

    public long getTotalHour() {
        return totalHour;
    }

    public long getTotalMinute() {
        return totalMin;
    }

    public long getSizeTotalHour() {
        return totalSizeHour;
    }

    public long getSizeTotalMinute() {
        return totalSizeMin;
    }

    public String getsStatus() {
        /**
         * String status = "Ativa"; if (isActiveRealTime && pauseRealTime) { status = "Pausada"; } else if (!isActiveRealTime) { status = "Inativa"; }
         *
         * return status;
         *
         */
        return status;
    }

    public boolean isActive() {

        return isActiveRealTime;

    }

    public String getExportId() {
        return exportId;
    }

    public int getItem() {
        return item;
    }

}
