/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.test;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfFormat;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorialExport {

   public TutorialExport() {
   }

   public void exportExample() throws DfServiceException, DfException {

      // create a client object using a factory method in DfClientX
      DfClientX clientx = new DfClientX();
      IDfClient client = clientx.getLocalClient();
// call a factory method to create the session manager
      IDfSessionManager sessionMgr = client.newSessionManager();
// create an IDfLoginInfo object and set its fields
      IDfLoginInfo loginInfo = clientx.getLoginInfo();
      loginInfo.setUser("dctmadmin");
      loginInfo.setPassword("Dctm@dmin65");
// set single identity for all docbases
      sessionMgr.setIdentity("RVD01", loginInfo);

      IDfSession mySession = null;
      StringBuffer sb = new StringBuffer("");
      try {
         mySession = sessionMgr.getSession("RVD01");

// Get the object ID based on the object ID string.
         IDfId idObj
                 = mySession.getIdByQualification(
                         "dm_sysobject where r_object_id='" + "09d75823800070d8" + "'"
                 );
// Instantiate an object from the ID.
         IDfSysObject sysObj = (IDfSysObject) mySession.getObject(idObj);
// Create a new client instance.
// Use the factory method to create an IDfExportOperation instance.
         IDfExportOperation eo = clientx.getExportOperation();
// Create a document object that represents the document being exported.
         IDfDocument doc
                 = (IDfDocument) mySession.getObject(new DfId("09d75823800070d8"));
// Create an export node, adding the document to the export operation object.
         IDfExportNode node = (IDfExportNode) eo.add(doc);
// Get the document's format.
         IDfFormat format = doc.getFormat();

         String targetLocalDirectory = "C:/Temp/tassdfasdfasdf";

// If necessary, append a path separator to the targetLocalDirectory value.
         if (targetLocalDirectory.lastIndexOf("/")
                 != targetLocalDirectory.length() - 1
                 && targetLocalDirectory.lastIndexOf("\\")
                 != targetLocalDirectory.length() - 1) {
           // targetLocalDirectory += "/";
         }
// Set the full file path on the local system.

         IDfFolder fd = (IDfFolder) mySession.getObject(doc.getFolderId(0));

         String path = targetLocalDirectory + fd.getFolderPath(0);

         File dir = new File(path);
         dir.mkdirs();

         path += "/" + doc.getObjectName();
         node.setFilePath(path);
// Execute and return results
         if (eo.execute()) {

            System.out.println("Export operation successful." + "\\n" + sb.toString());
         } else {

            System.out.println("Export operation failed." + eo.getErrors());
         }
      } // Handle any exceptions.
      catch (Exception ex) {
         ex.printStackTrace();
         System.out.println("Exception has been thrown: " + ex);
      } // Always, always, release the session in the "finally" clause.
      finally {
         sessionMgr.release(mySession);
      }
   }

   public static void main(String args[]) {
      TutorialExport te = new TutorialExport();
      try {
         te.exportExample();
      } catch (Exception ex) {
         Logger.getLogger(TutorialExport.class.getName()).log(Level.SEVERE, null, ex);
      }

   }

}
