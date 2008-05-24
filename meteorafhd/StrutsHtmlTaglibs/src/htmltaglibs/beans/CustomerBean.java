package htmltaglibs.beans;

public class CustomerBean {
  private int custId;
  private String name;
  private String[] favColors=new String[0];
  public CustomerBean() {
  }
  public int getCustId(){
    return this.custId;
  }
  public String getName(){
    return this.name;
  }
  public String[] getFavColors(){
    return this.favColors;
  }

  public void setCustId(int custId){
    this.custId=custId;
  }
  public void setName(String name){
    this.name=name;
  }
  public void setFavColors(String[] favColors){
    this.favColors=favColors;
  }
}