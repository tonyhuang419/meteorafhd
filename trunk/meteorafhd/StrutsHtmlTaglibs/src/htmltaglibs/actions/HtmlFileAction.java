package htmltaglibs.actions;

import htmltaglibs.forms.HtmlFileForm;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class HtmlFileAction extends Action
{

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    throws Exception {

        String dir=servlet.getServletContext().getRealPath("/upload");

        HtmlFileForm hff = (HtmlFileForm) form;

        // org.apache.struts.upload.FormFile contains the uploaded file
        FormFile file = hff.getFile();

        // If no file was uploaded (e.g. first form load), then display View
        /*
         * 防止提交为空文件情况 file.getFileName().trim().equals("")
         */
        if (file == null || file.getFileName().trim().equals("") ) {
        	return mapping.findForward("success");

        }

        // Get the name and file size
        String fname = file.getFileName();
        String size = Integer.toString(file.getFileSize()) + " bytes";

        InputStream streamIn = file.getInputStream();
        OutputStream streamOut = new FileOutputStream(dir + "/"+fname);

        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
            streamOut.write(buffer, 0, bytesRead);
        }

        streamOut.close();
        streamIn.close();
        // Populate the form bean with the results for display in the View
        hff.setFname(fname);
        hff.setSize(size);

        // Clean up our toys when done playing
        file.destroy();

        // Forward to default display
        return mapping.findForward("success");

    }

}