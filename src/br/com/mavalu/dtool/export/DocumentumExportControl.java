package br.com.mavalu.dtool.export;

import br.com.mavalu.useful.DocumentumUsefulThreadSafe;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfTime;
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
    private long[] totalHourArray = new long[60];
    private int second = 0;
    private int minute = 0;
    private long[] totalMinArray = new long[60];
    private long totalHour = 0;
    private long totalMin = 0;
    private long lastFileProcessedNumber = 0;
    private TrheadDocPack tdc = null;

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

            while (isActive) {

                if (!pause) {
                    tdc = getNextLine();
                    if (tdc != null && tdc.line != null) {
                        try {
                            //importa o conteúdo para docbase
                            String path = documentumUseful.exportDocument(mc.getPath(), tdc.id, tdc.item, mc.getDctmFolderExtruture(), mc.getExpAllInFolderOrLikeServer());
                            tdc.line += ";" + path;
                            fileProcessedNumber++;
                        } catch (DfException ex) {
                            tdc.success = false;
                            Logger.getLogger(DocumentumExportControl.class.getName()).log(Level.SEVERE, null, ex);                            
                            String error = ex.getMessage().replace(";", "-");
                            tdc.line += ";/////////ERROR////////;" + error;
                            tdc.error = error;
                        } finally {
                            tdc.processed = true;
                        }
                    } else {
                        setStop();
                    }
                } else {
                    sleep(3000);
                }
                isActiveRealTime = isActive;
                pauseRealTime = pause;
            }

        } catch (InterruptedException e) {

            e.printStackTrace();

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
        this.isActive = false;
    }

    public synchronized void setPause(boolean p) {
        this.pause = p;
    }

    public boolean getPause() {
        return this.pause;
    }

    public long getFileProcessedNumber() {
        return fileProcessedNumber;
    }

    /**
     * Deve ser executada a cada segundo
     */
    public void updateStatistics() {

        long lastSecondProcessedNumber = fileProcessedNumber - lastFileProcessedNumber;

        if (second == 60) {
            second = 0;
        }
        if (minute == 60) {
            minute = 0;
        }
        totalMin = (totalMin - totalMinArray[second]) + lastSecondProcessedNumber;
        totalMinArray[second] = lastSecondProcessedNumber;

        totalHour = (totalHour - totalHourArray[minute]) + totalMin;
        totalHourArray[minute] = totalMin;

        if (second == 59) {
            minute++;
        }

        second++;

        lastFileProcessedNumber = fileProcessedNumber;
    }

    public long getTotalHour() {
        return totalHour;
    }

    public long getTotalMinute() {
        return totalMin;
    }

    public String getsStatus() {
        String status = "Ativa";
        if (isActiveRealTime && pauseRealTime) {
            status = "Pausada";
        } else if (!isActiveRealTime) {
            status = "Inativa";
        }

        return status;

    }

    public boolean isActive() {

        return isActiveRealTime;

    }

}
