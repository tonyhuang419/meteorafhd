package statistics.dept;

import java.util.Comparator;

public class DeptCompator implements Comparator<Dept> {
	public int compare(Dept e1,Dept e2){
		if(e1.getDeptName()=="�ܼ�" || e2.getDeptName()=="�ܼ�")
			return 0;
		else
			return e1.getDeptName().compareTo(e2.getDeptName());
	}
}