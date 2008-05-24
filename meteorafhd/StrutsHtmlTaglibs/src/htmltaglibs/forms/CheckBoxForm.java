package htmltaglibs.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CheckBoxForm extends ActionForm {

  // Default bean constructor
  public CheckBoxForm() { }

  // For <html:checkbox> sample code
  private boolean checkbox1;
  public boolean getCheckbox1() { return this.checkbox1; }
  public void setCheckbox1(boolean checkbox1) { this.checkbox1 = checkbox1;}

  // For <html:checkbox> sample code
  private boolean checkbox2;
  public boolean getCheckbox2() { return this.checkbox2; }
  public void setCheckbox2(boolean checkbox2) { this.checkbox2 = checkbox2;}

  // For <html:multibox> sample code
  private String strArray[] = new String[0];
  public String[] getStrArray() { return (this.strArray); }
  public void setStrArray(String strArray[]) { this.strArray = strArray;}

  // For <html:radio> sample code
  private String radioVal = "";
  public String getRadioVal() { return (this.radioVal); }
  public void setRadioVal(String radioVal) { this.radioVal = radioVal;}

  public void reset(ActionMapping mapping, HttpServletRequest request) {

    this.setCheckbox1(false);
 //   Note: With checkbox2 never reset here, it won't ever appear "unset"
 //   this.setCheckbox2(false);

    this.strArray = new String[0];
    this.radioVal = "";
  }
}
