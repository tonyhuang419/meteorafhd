package htmltaglibs.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;


public class FormBasicForm extends ActionForm
{

    // Default bean constructor
    public FormBasicForm() { }

    private String status;
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    private String hiddenValue;
    public String getHiddenValue() { return this.hiddenValue; }
    public void setHiddenValue(String hiddenValue) { this.hiddenValue = hiddenValue; }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
       status=null;
       hiddenValue=null;
    }

}