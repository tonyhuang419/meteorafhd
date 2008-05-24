package stuInfo;

import java.util.ArrayList;
import java.util.List;

public class stu {
	static private List<String> stuNameList = new ArrayList();
	static private List<String> stuNoList = new ArrayList();

	
	static void addStu(String _sname,String  _sno){
		stuNameList.add(_sname);
		stuNoList.add(_sno);
	}

	static public List<String> getStuNameList() {
		return stuNameList;
	}

	static public List<String> getStuNoList() {
		return stuNoList;
	}

}
