package htmltaglibs.forms;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import htmltaglibs.beans.CustomerBean;


public class HtmlSelectForm extends ActionForm {

  private CustomerBean customers[];
  public CustomerBean [] getCustomers() { return this.customers; }
  public void setCustomers(CustomerBean [] customers) {
    this.customers = customers;

  }

  private CustomerBean cust = new CustomerBean();
  public CustomerBean getCust() { return cust; }
  public void setCust(CustomerBean cust) { this.cust = cust; }

  private int custId;
  public int getCustId() { return this.custId; }
  public void setCustId(int custId) { this.custId = custId; }

  private String colors[];
  public String [] getColors() { return this.colors; }
  public void setColors(String [] colors) { this.colors = colors; }

  // Default bean constructor
  public HtmlSelectForm() {

    customers = new CustomerBean[3];

    for (int i=0; i<3 ; i++ ) {
      customers[i] = new CustomerBean();
      customers[i].setCustId(i);
    }

    customers[0].setName("Tom");
    customers[1].setName("Linda");
    customers[2].setName("Jane");
  }

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    this.cust = new CustomerBean();
    this.colors = new String[0];
    this.custId = -1;
  }

  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

     ActionErrors errors = new ActionErrors();

     // if custId is -1, then no customer was chosen yet.

     if (custId == -1) {
       return errors;
     }

     /*
      * No real validation done. Just set values based on input.
      */

     this.customers[this.custId].setFavColors(this.colors);
     this.cust = this.customers[this.custId];

     return errors;
  }
}