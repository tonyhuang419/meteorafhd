package action;


import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;


public class RegistAction extends ActionSupport
{
    private String name;
    private String pwd;
    private int age;
    private Date birth;

   
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public Date getBirth()
	{
		return (this.birth); 
	}


	public void setBirth(Date birth) 
	{
		this.birth = birth; 
	}


//	public String execute() throws Exception
//	{
//            return "success";
//    }
}