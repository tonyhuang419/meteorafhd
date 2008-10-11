package ioc2;

import java.util.List;
import java.util.Map;

public class SomeBean {
	private String[] someStrArray;
	private ObjectSet[] someObjArray;
	private List someList;
	private Map someMap;
	public SomeBean()
	{}

	public List getSomeList() {
		return someList;
	}

	public void setSomeList(List someList) {
		this.someList = someList;
	}

	public Map getSomeMap() {
		return someMap;
	}

	public void setSomeMap(Map someMap) {
		this.someMap = someMap;
	}

	public ObjectSet[] getSomeObjArray() {
		return someObjArray;
	}

	public void setSomeObjArray(ObjectSet[] someObjArray) {
		this.someObjArray = someObjArray;
	}
	public String[] getSomeStrArray() {
		return someStrArray;
	}
	public void setSomeStrArray(String[] someStrArray) {
		this.someStrArray = someStrArray;
	}
}
