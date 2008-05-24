package action;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.jsonplugin.annotations.JSON;

public class JsonAction {
	private  String name;
	private  String pwd;
	private  String age;

	private int[] ints = {10, 20};
	private Map map = new HashMap();
	private String customName = "custom";


	public int[] getInts() {
		return ints;
	}
	public void setInts(int[] ints) {
		this.ints = ints;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	@JSON(name="newName")
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String execute(){
		map.put("name", "fhdone");
		return "success";
	}
}
