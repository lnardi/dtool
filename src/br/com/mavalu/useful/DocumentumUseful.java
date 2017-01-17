/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.useful;

import br.com.mavalu.dtool.control.DtoolLogControl;
import com.documentum.com.DfClientX;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfDocbaseMap;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentumUseful {

    private static IDfClient dfClient = null;
    private static IDfSessionManager sessionMgr = null;
    private static String loginDocbase = null;
    private static HashMap<String, String> mimeTypeList = new HashMap<String, String>();
    private static HashMap<String, String> dosTypeList = new HashMap<String, String>();
    private static HashMap<IDfCollection, IDfSession> sessionList = new HashMap<IDfCollection, IDfSession>();
    private static DfClientX cx;

    /**
     * @param q String com a query
     * @param type Define o tipo de query. 0 para select e 1 para update.
     * Existem outros tipos que podem ser implementados.
     * @param top Defini se a query utilizar o return_top e a quantidade
     * @return Coleção de objetos
     * @throws DfException
     */
    public static IDfCollection executarQuery(String q, int type, String top) throws DfException {
        IDfCollection col = null;
        IDfQuery query = null;
        int queryType = 0;

        switch (type) {
            case 0:
                queryType = DfQuery.DF_READ_QUERY;
            case 1:
                queryType = DfQuery.DF_QUERY;
        }

        if (!top.equals("NO")) {
            String topQuery = " ENABLE (RETURN_TOP " + top + " )";
            q = q + topQuery;
        }
        IDfSession session = sessionMgr.getSession(loginDocbase);

        query = new DfQuery();
        query.setDQL(q);
        col = query.execute(session, queryType);

        sessionList.put(col, session);

        return col;

    }

    public static void close(IDfCollection col) throws DfException {

        IDfSession session = sessionList.remove(col);

        col.close();

        sessionMgr.release(session);

    }

    public static boolean validId(String id) {
        return DfId.isObjectId(id);
    }

    public static void login(String docbase, String userName, String password) throws DfException {
        if (docbase == null || userName == null || docbase.isEmpty() || userName.isEmpty()) {
            throw new DfException("Docbase, username ou Senha não foram inseridos");
        }

        loginDocbase = docbase;
        // now login
        if (dfClient == null) {
            cx = new DfClientX();
            dfClient = cx.getLocalClient();
        }

        if (dfClient != null) {
            IDfLoginInfo li = new DfLoginInfo();
            li.setUser(userName);
            li.setPassword(password);
            // li.setDomain(domain);

            sessionMgr = dfClient.newSessionManager();
            sessionMgr.setIdentity(loginDocbase, li);

            FirstSession fs = new FirstSession();
            fs.setDocbaseName(docbase);
            fs.setIDfSessionManager(sessionMgr);

            Thread t = new Thread(fs);

            t.start();
        }

    }

    public static void closeSession() {

    }

    public static String getHostName() throws DfException {

        String value = null;

        IDfTypedObject teste = dfClient.getDocbrokerMap();
        value = teste.getString("host_name");

        return value;
    }

    public static boolean createFolder(String name, String link) throws DfException {

        boolean status = false;

        IDfSession session = sessionMgr.getSession(loginDocbase);
        try {
            IDfFolder folder = (IDfFolder) session.newObject("dm_folder");
            if (folder != null) {
                folder.setObjectName(name);
                folder.link(link);
                folder.save();
                status = true;
            }

        } finally {
            sessionMgr.release(session);
        }

        return status;

    }

    public static IDfDocument createDocument(String name, String fileLink, String folderLink, String documentumType)
            throws DfException {

        String ext = fileLink.substring(fileLink.length() - 3, fileLink.length());

        String contentType = getRepsitoryFormatFromMimeType(ext);

        if (contentType == null) {
            throw new DfException("O formato do documento é desconhecido pelo Documentum");
        }

        IDfSession session = sessionMgr.getSession(loginDocbase);

        IDfDocument document = null;

        document = (IDfDocument) session.newObject(documentumType == null ? "dm_document" : documentumType);
        if (document != null) {
            document.setObjectName(name);
            document.setContentType(contentType);
            document.setFile(fileLink);
            document.link(folderLink);
            document.save();
        }

        return document;
    }

    public static void release(IDfSession session) {

        sessionMgr.release(session);

    }

    public static boolean verifyFolderByPath(String path) throws DfException {

        IDfSession session = sessionMgr.getSession(loginDocbase);
        IDfFolder folder = null;

        try {

            folder = session.getFolderByPath(path);
        } finally {
            sessionMgr.release(session);
        }

        return folder != null;
    }

    public static List<String> getDobaseList() throws DfException {

        // now login
        cx = new DfClientX();
        // now login
        if (dfClient == null) {
            dfClient = cx.getLocalClient();
        }

        IDfDocbaseMap docbaseMap = dfClient.getDocbaseMap();
        List<String> docbaseList = new ArrayList<String>();
        for (int n = 0; n < docbaseMap.getDocbaseCount(); n++) {
            docbaseList.add(docbaseMap.getDocbaseName(n));
        }

        return docbaseList;

    }

    public static String apiExec(String q) throws DfException {

        IDfSession session = sessionMgr.getSession(loginDocbase);
        try {

            return session.getObject(new DfId(q)).dump();

        } finally {
            sessionMgr.release(session);
        }

    }

    /**
     * Reverse maps a mime-type to a repository format.
     *
     * @param ext The DOS extention whitout dot "."
     * @return The corresponding repository format. If multiple formats are
     * found <br>
     * the first one is returned by default. A <code>null</code> is <br>
     * returned if no format can be found.
     */
    public static String getRepsitoryFormatFromMimeType(String ext) throws DfException {

        ext = ext.trim();

        String contentType = mimeTypeList.get(ext);
        if (contentType != null) {
            return contentType;
        }

        StringBuffer bufFormatQuery = new StringBuffer(32);
        bufFormatQuery.append("select name from dm_format where dos_extension = '");
        bufFormatQuery.append(ext).append("'");
        IDfQuery formatQuery = new DfQuery();
        formatQuery.setDQL(bufFormatQuery.toString());

        IDfCollection formats = null;
        IDfSession session = sessionMgr.getSession(loginDocbase);

        try {

            formats = formatQuery.execute(session, IDfQuery.READ_QUERY);

            if (formats.next()) {
                contentType = formats.getString("name");
                mimeTypeList.put(ext, contentType);
                return contentType;
            } else {
                return null;
            }
        } finally {
            sessionMgr.release(session);
            if (formats != null) {
                formats.close();
            }
        }

    }

    /**
     * Reverse maps a mime-type to a repository format.
     *
     * @param content_type The documentum a_content_type attribute
     * @return The corresponding dos extention.
     */
    public static String getRepsitoryDosFormatFromMimeType(String content_type) throws DfException {

        content_type = content_type.trim();

        String contentType = dosTypeList.get(content_type);
        if (contentType != null) {
            return contentType;
        }

        StringBuffer bufFormatQuery = new StringBuffer(32);
        bufFormatQuery.append("select dos_extension from dm_format where name = '");
        bufFormatQuery.append(content_type).append("'");
        IDfQuery formatQuery = new DfQuery();
        formatQuery.setDQL(bufFormatQuery.toString());

        IDfCollection formats = null;
        IDfSession session = sessionMgr.getSession(loginDocbase);

        try {

            formats = formatQuery.execute(session, IDfQuery.READ_QUERY);

            if (formats.next()) {
                contentType = formats.getString("dos_extension");
                dosTypeList.put(content_type, contentType);
                return contentType;
            } else {
                return null;
            }
        } finally {
            sessionMgr.release(session);
            if (formats != null) {
                formats.close();
            }
        }

    }

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            // long sT = System.currentTimeMillis();
            DocumentumUseful.login("CMR01", "dctmadmin", "Dctm@dmin65");

            System.out.println(String.format("%.2f", (System.currentTimeMillis() - startTime) / 1000.0)
                    + " ==> Login no documento");
            startTime = System.currentTimeMillis();

            DocumentumUseful.getDobaseList();

            System.out.println(String.format("%.2f", (System.currentTimeMillis() - startTime) / 1000.0)
                    + " ==> Login no documento");
            startTime = System.currentTimeMillis();

            DocumentumUseful.getRepsitoryFormatFromMimeType("txt");

            System.out.println(String.format("%.2f", (System.currentTimeMillis() - startTime) / 1000.0)
                    + " ==> Login no documento");

        } catch (DfException e) {

            e.printStackTrace();
        }

    }

    public static void exportDocument(File path, List<String[]> rowList, int position, int dctmFolderExtruture) throws DfException {

        Iterator<String[]> rows = rowList.iterator();
        String[] row = null;

        // Create a new client instance.
        // Use the factory method to create an IDfExportOperation instance.
        IDfExportOperation eo = cx.getExportOperation();
        // Create a document object that represents the document being exported.

        IDfSession session = sessionMgr.getSession(loginDocbase);

        try {

            while (rows.hasNext()) {
                row = rows.next();

                IDfDocument doc
                        = (IDfDocument) session.getObject(new DfId(row[position]));
                // Create an export node, adding the document to the export operation object.
                IDfExportNode node = (IDfExportNode) eo.add(doc);
                IDfId id = null;
                try {

                    id = doc.getFolderId(dctmFolderExtruture);
                } catch (DfException e) {
                    //Tenta exportar a extrutura padrão.
                    id = doc.getFolderId(0);
                }

                IDfFolder fd = (IDfFolder) session.getObject(id);

                String folderPath = path.toString() + "/" + fd.getFolderPath(0);

                folderPath = folderPath.replaceAll("[\\?]", "#");
                folderPath = folderPath.replaceAll("[\\|\\:\\*]", "");

                File dir = new File(folderPath);
                dir.mkdirs();

                String name = doc.getObjectName();
                name = name.replaceAll("[\\?]", "#");
                name = name.replaceAll("[\\|\\:\\*]", "");

                String list[] = name.split("\\.");

                if (list.length == 1) {
                    String contentType = doc.getString("a_content_type");
                    String dosExtention = getRepsitoryDosFormatFromMimeType(contentType);
                    name = name + "." + dosExtention;
                }

                folderPath += "/" + name;

                node.setFilePath(folderPath);
// Execute and return results
                if (eo.execute()) {

                    System.out.println("Export operation successful." + "\\n" + eo.toString());
                } else {

                    System.out.println("Export operation failed." + eo.getErrors());
                }
            }
        } finally {
            sessionMgr.release(session);
        }

    }

    public static String exportDocument(String path, String r_id, long item, int dctmFolderExtruture, boolean expAllInFolderOrLikeServer) throws DfException {

        //TODO - Remover após embratel
        //String anomalia = "";
        String documentPath = "";
        // Create a new client instance.
        // Use the factory method to create an IDfExportOperation instance.
        IDfExportOperation eo = cx.getExportOperation();
        // Create a document object that represents the document being exported.

        IDfSession session = sessionMgr.getSession(loginDocbase);

        try {

            IDfDocument doc
                    = (IDfDocument) session.getObject(new DfId(r_id));
            //VErifica se possui conteúdo.
            if (doc.getContentSize() == 0) {
                return "";
            }

            IDfId id = null;
            String folderPath = null;
            if (!expAllInFolderOrLikeServer) {

                try {

                    id = doc.getFolderId(dctmFolderExtruture);
                } catch (DfException e) {
                    //Tenta exportar a extrutura padrão.
                    id = doc.getFolderId(0);
                }

                IDfFolder fd = (IDfFolder) session.getObject(id);

                String dctmFolderPath = fd.getFolderPath(0);

                dctmFolderPath = dctmFolderPath.replaceAll("[\\?]", "#");
                dctmFolderPath = dctmFolderPath.replaceAll("[\\n\\t\\\\\\:\\*\\?\\\"\\<\\>\\|]", "");

                dctmFolderPath = dctmFolderPath.trim();

                folderPath = path + dctmFolderPath;

                documentPath = dctmFolderPath;

            } else {
                folderPath = path;
            }

            File dir = new File(folderPath);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {

                }
            }

            String name = doc.getObjectName();
            name = name.replaceAll("[\\?]", "#");
            name = name.replaceAll("[\\n\\t\\\\\\/\\:\\*\\?\\\"\\<\\>\\|]", " ");

            String list[] = name.split("\\.");

            if (list.length == 1) {
                String contentType = doc.getString("a_content_type");
                String dosExtention = getRepsitoryDosFormatFromMimeType(contentType);
                if (dosExtention != null) {
                    name = name + "." + dosExtention;
                } else {
                    name = name + "." + "tbd";
                }
            }

            String fileName = item + "_" + name;

            //Garante que o path mais o nome do arquivo não excedam 256 caracteres
            if ((folderPath.length() + fileName.length()) > 256) {

                String contentType = doc.getString("a_content_type");
                String dosExtention = getRepsitoryDosFormatFromMimeType(contentType);

                fileName = item + "." + dosExtention;

                if ((folderPath.length() + fileName.length()) > 256) {
                    throw new DfException("Error - Nome do arquivo mais o path escolhido excederam o tamanho máximo suportado pelo sistema(256).");
                }

            }

            folderPath += "/" + fileName;

            documentPath += "/" + fileName;

            File file = new File(folderPath);          
            
            //Só exporta se o arquivo não existe.
            if (!(file.exists() && file.length() == doc.getContentSize())) {
                // Create an export node, adding the document to the export operation object.
                IDfExportNode node = (IDfExportNode) eo.add(doc);

                node.setFilePath(folderPath);

                if (!eo.execute()) {

                    throw new DfException("Exportação falhou ==> /n/r " + eo.getErrors());
                }

            }

            //TODO - remover após embratel
            /**
             * int length = doc.getValueCount("anomalia");
             *
             * for (int i = 0; i < length; ++i) { anomalia +=
             * doc.getRepeatingString("anomalia", i); if ((i + 1) < length) {
             * anomalia += "|"; } }
             *
             */
            /////
        } finally {
            sessionMgr.release(session);
        }

        //return anomalia + ";" + documentPath;
        return documentPath;
    }

    public static String exportDocument(String path, String r_id) throws DfException {

        String documentPath = null;
        // Create a new client instance.
        // Use the factory method to create an IDfExportOperation instance.
        IDfExportOperation eo = cx.getExportOperation();
        // Create a document object that represents the document being exported.

        IDfSession session = sessionMgr.getSession(loginDocbase);

        try {

            IDfDocument doc
                    = (IDfDocument) session.getObject(new DfId(r_id));
            // Create an export node, adding the document to the export operation object.
            IDfExportNode node = (IDfExportNode) eo.add(doc);

            IDfId id = null;

            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {

                }
            }

            String name = doc.getObjectName();
            name = name.replaceAll("[\\?]", "#");
            name = name.replaceAll("[\\n\\t\\\\\\/\\:\\*\\?\\\"\\<\\>\\|]", " ");

            String list[] = name.split("\\.");

            if (list.length == 1) {
                String contentType = doc.getString("a_content_type");
                String dosExtention = getRepsitoryDosFormatFromMimeType(contentType);
                name = name + "." + dosExtention;
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

            String fileName = dateFormat.format(new Date()) + "_" + name;
            documentPath = path + "/" + fileName;

            System.out.println(documentPath);

            node.setFilePath(documentPath);

            if (!eo.execute()) {
                throw new DfException("Exportação falhou ==> /n/r " + eo.getErrors());
            }
        } finally {
            sessionMgr.release(session);
        }

        return documentPath;
    }

    //TODO - remover após embratel
    /**
     * public static String getAnomalia(String r_id) throws DfException {
     *
     * IDfSession session = sessionMgr.getSession(loginDocbase); String anomalia
     * = ""; try {
     *
     * IDfDocument doc = (IDfDocument) session.getObject(new DfId(r_id));
     *
     * int length = doc.getValueCount("anomalia");
     *
     * for (int i = 0; i < length; ++i) { anomalia +=
     * doc.getRepeatingString("anomalia", i); if ((i + 1) < length) { anomalia
     * += "|"; } } } finally { sessionMgr.release(session); }
     *
     * return anomalia;
     *
     * }
     *
     */
    //Cria uma nova sessão em paralelo enquanto o usário faz outra ações.
    private static class FirstSession
            implements Runnable {

        private static IDfSessionManager sessionMgr = null;
        private static String docbaseName;

        public void setIDfSessionManager(IDfSessionManager sm) {
            sessionMgr = sm;
        }

        public void setDocbaseName(String dn) {
            docbaseName = dn;
        }

        public void run() {

            try {
                sessionMgr.getSession(docbaseName);

                DtoolLogControl.log("Logado com Sucesso!! Sessão Liberada", Level.INFO);

            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(DocumentumUseful.class.getName()).log(Level.SEVERE, null, ex);
                DtoolLogControl.log("Falha no login: " + ex.getMessage(), Level.SEVERE);
            }

        }

    }

}
