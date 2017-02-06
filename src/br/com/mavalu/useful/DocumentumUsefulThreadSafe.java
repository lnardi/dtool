package br.com.mavalu.useful;

import br.com.mavalu.dtool.export.TrheadDocPack;
import static br.com.mavalu.useful.DocumentumUseful.getRepsitoryDosFormatFromMimeType;
import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocumentumUsefulThreadSafe {

    private IDfClientX cx = new DfClientX();
    private IDfClient dfClient = null;
    private IDfSessionManager sessionMgr = null;
    private String loginDocbase = null;
    private final HashMap<String, String> mimeTypeList = new HashMap<String, String>();
    private final HashMap<IDfCollection, IDfSession> sessionList = new HashMap<IDfCollection, IDfSession>();
    private IDfSession localSession = null;

    /**
     * @param q String com a query
     * @param type Define o tipo de query. 0 para select e 1 para update.
     * Existem outros tipos que podem ser implementados.
     * @param top Defini se a query utilizar o return_top e a quantidade
     * @return Cole��o de objetos
     * @throws DfException
     */
    public IDfCollection executarQuery(String q, int type, String top) throws DfException {
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

    public void close(IDfCollection col) throws DfException {

        IDfSession session = sessionList.remove(col);

        col.close();

        sessionMgr.release(session);

    }

    public void login(String docbase, String userName, String password) throws DfException {

        if (docbase == null || userName == null || docbase.isEmpty() || userName.isEmpty()) {
            throw new DfException("Docbase, username ou Senha n�o foram inseridos");
        }

        loginDocbase = docbase;
        // now login
        if (dfClient == null) {
            dfClient = cx.getLocalClient();
        }

        if (dfClient != null) {
            IDfLoginInfo li = new DfLoginInfo();
            li.setUser(userName);
            li.setPassword(password);
            // li.setDomain(domain);

            sessionMgr = dfClient.newSessionManager();
            sessionMgr.setIdentity(loginDocbase, li);
        }

    }

    public void login(String docbase, IDfLoginInfo li) throws DfException {

        if (docbase == null || li == null || docbase.isEmpty()) {
            throw new DfException("Docbase, username ou Senha n�o foram inseridos");
        }

        loginDocbase = docbase;
        // now login
        if (dfClient == null) {
            dfClient = cx.getLocalClient();
        }

        if (dfClient != null) {

            sessionMgr = dfClient.newSessionManager();
            sessionMgr.setIdentity(loginDocbase, li);
        }

    }

    public void closeSession() {

    }

    public String getHostName() throws DfException {

        String value = null;

        IDfTypedObject teste = dfClient.getDocbrokerMap();
        value = teste.getString("host_name");

        return value;
    }

    public boolean createFolder(String name, String link) throws DfException {

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

    public IDfDocument createDocument(String name, String fileLink, String folderLink, String documentumType)
            throws DfException {

        String ext = fileLink.substring(fileLink.length() - 3, fileLink.length());

        String contentType = getRepsitoryFormatFromMimeType(ext);

        if (contentType == null) {
            throw new DfException("O formato do documento � desconhecido pelo Documentum");
        }

        localSession = sessionMgr.getSession(loginDocbase);

        IDfDocument document = null;

        document = (IDfDocument) localSession.newObject(documentumType == null ? "dm_document" : documentumType);
        if (document != null) {
            document.setObjectName(name);
            document.setContentType(contentType);
            document.setFile(fileLink);
            document.link(folderLink);
            document.save();
        }

        return document;
    }

    public void release(IDfSession session) {

        if (session != null) {
            sessionMgr.release(session);
        }

        if (localSession != null) {
            sessionMgr.release(localSession);
            localSession = null;
        }
    }

    public boolean verifyFolderByPath(String path) throws DfException {

        IDfSession session = sessionMgr.getSession(loginDocbase);
        IDfFolder folder = null;

        try {

            folder = session.getFolderByPath(path);
        } finally {
            sessionMgr.release(session);
        }

        return folder != null;

    }

    public List<String> getDobaseList() throws DfException {

        // now login
        IDfClientX cx = new DfClientX();
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

    public String apiExec(String q) throws DfException {

        IDfSession session = sessionMgr.getSession(loginDocbase);
        try {

            return session.getObject(new DfId(q)).dump();

        } finally {
            sessionMgr.release(session);
        }

    }

    public String apiExecSize(String q) throws DfException {

        IDfSession session = sessionMgr.getSession(loginDocbase);
        try {
            return session.apiGet("getpath", q);
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
    public String getRepsitoryFormatFromMimeType(String ext) throws DfException {

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

    public void main(String[] args) {
        /*
		 * try { long startTime = System.currentTimeMillis(); // long sT =
		 * System.currentTimeMillis(); DocumentumUseful.login("CMR01",
		 * "dctmadmin", "Dctm@dmin65");
		 * 
		 * System.out.println(String.format("%.2f", (System.currentTimeMillis() -
		 * startTime) / 1000.0) + " ==> Login no documento"); startTime =
		 * System.currentTimeMillis();
		 * 
		 * DocumentumUseful.getDobaseList();
		 * 
		 * System.out.println(String.format("%.2f", (System.currentTimeMillis() -
		 * startTime) / 1000.0) + " ==> Login no documento"); startTime =
		 * System.currentTimeMillis();
		 * 
		 * DocumentumUseful.getRepsitoryFormatFromMimeType("txt");
		 * 
		 * System.out.println(String.format("%.2f", (System.currentTimeMillis() -
		 * startTime) / 1000.0) + " ==> Login no documento");
		 * 
		 * } catch (DfException e) {
		 * 
		 * e.printStackTrace(); }
         */
    }

    public void getData_Ticket(String hex) {

        // converting 80002023 to decimal = 2147491875
        // subtract 2^32: 2147491875 � 4294967296 = -2147475421
        double dec = Long.parseLong(hex, 16);// Long.decode(hex);

        dec = dec - 4294967296.0;

        System.out.println(String.format("%.0f", dec));

    }

    public String getPath(String data_ticket) {

        // -2147474649 + 2^32 = (-2147474649 + 4294967296) = 2147492647
        // converting 2147492647 to hex = 80002327
        Long dec = Long.parseLong(data_ticket) + 4294967296l;// Long.decode(hex);

        String value = Long.toString(dec, 16);

        // String hex = Long.toString(2147492647l, 16);
        // String b64 = Transcoder.transcode(hex);
        String ret[] = value.split("(?<=\\G.{2})");

        // System.out.println(ret);
        String path = "";

        for (int i = 0; i < ret.length; i++) {

            path += ret[i];

            if (i < (ret.length - 1)) {
                path += "\\";
            }

        }

        return path;

    }

    public void destroy(String id) throws DfException {

        IDfSession session = sessionMgr.getSession(loginDocbase);

        IDfDocument document = (IDfDocument) session.getObject(new DfId(id));

        document.destroyAllVersions();

        release(session);

    }

    //Faz o primeiro Login
    public void loadSession() throws DfException {
        IDfSession session = sessionMgr.getSession(loginDocbase);
        release(session);
    }

    public String exportDocument(String path, String relativePath, TrheadDocPack tdc, int dctmFolderExtruture, boolean expAllInFolderOrLikeServer) throws DfException {

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
                    = (IDfDocument) session.getObject(new DfId(tdc.id));
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

            String fileName = tdc.item + "_" + name;

            //Garante que o path mais o nome do arquivo não excedam 256 caracteres
            if ((folderPath.length() + fileName.length()) > 256) {

                String contentType = doc.getString("a_content_type");
                String dosExtention = getRepsitoryDosFormatFromMimeType(contentType);

                fileName = tdc.item + "." + dosExtention;

                if ((folderPath.length() + fileName.length()) > 256) {
                    throw new DfException("Error - Nome do arquivo mais o path escolhido excederam o tamanho máximo suportado pelo sistema(256).");
                }
            }
            folderPath += "/" + fileName;

            documentPath += "/" + fileName;
            if (relativePath != null) {
                documentPath = "/" + relativePath + documentPath;
            }

            File file = new File(folderPath);

            //Só exporta se o arquivo não existe.
            if (!(file.exists() && file.length() == doc.getContentSize())) {
                //Pega o tamanho
                //TODO - Descomentar

                tdc.size = doc.getContentSize();
                // Create an export node, adding the document to the export operation object.
                IDfExportNode node = (IDfExportNode) eo.add(doc);

                node.setFilePath(folderPath);

                if (!eo.execute()) {

                    throw new DfException("Exportação falhou ==> /n/r " + eo.getErrors());
                }
                /**
                 * ////TODO - REMOVER, É SÓ PARA TESTES Writer writer = null;
                 * try { writer = new BufferedWriter(new OutputStreamWriter( new
                 * FileOutputStream(folderPath), "utf-8"));
                 * writer.write("Something"); } catch (IOException ex) {
                 * ex.printStackTrace(); } finally { try { writer.close(); }
                 * catch (Exception ex) { } }
                 *
                 */

            }
        } finally {
            sessionMgr.release(session);
        }

        return documentPath;
    }

}
