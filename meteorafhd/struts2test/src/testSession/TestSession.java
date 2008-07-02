package testSession;

import com.opensymphony.xwork2.ActionContext;

public class TestSession {
	
	
	private String name = null;
	private int age = 0;
	
	public String execute()  { 
		ActionContext ctx = ActionContext.getContext();
		if(ctx.getSession().containsKey("name")){
			this.name = (String)ctx.getSession().get("name");
		}else{
			this.name = "没有用户名";
		}
		if(ctx.getSession().containsKey("age")){
			this.age = (Integer)ctx.getSession().get("age");
		}else{
			this.age = -1;
		}
		
		return "success";  
	}
	
	
	public String refresh(){
		return "refresh";
	}
	
	
	@SuppressWarnings("unchecked")
	public void processSession(){
		ActionContext ctx = ActionContext.getContext();
		ctx.getSession().put("name", this.name);
		ctx.getSession().put("age", this.age);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
