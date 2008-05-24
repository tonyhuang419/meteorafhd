package htmltaglibs.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import htmltaglibs.forms.FormBasicForm;

public class FormBasicAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception {

        FormBasicForm fbf = (FormBasicForm) form;

        if (isCancelled(request)) {

            /*
             * If request was cancelled, we would clean up any processing
             * that was unfinished and release any resources we may
             * have locked.
             */

            // Set status to reflect that cancel WAS pressed!
            fbf.setStatus("Cancel was pressed!");

            return (mapping.findForward("success"));
        }  else {

            // Set status to reflect that cancel WAS NOT pressed!
            fbf.setStatus("Submit was pressed!");

            return (mapping.findForward("success"));
        }
    }
}