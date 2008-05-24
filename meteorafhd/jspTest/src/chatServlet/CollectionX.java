package chatServlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CollectionX {
	static private List<Info> infoList = new ArrayList<Info>();
	
	synchronized static public void addList(String name,String info,String time){
		infoList.add(new Info(name,info,time));
	}
	
	static public Iterator<Info> readList(){
		Iterator<Info> it = infoList.listIterator();
		return it;
	}
	
	
	static private List<String> infoUser = new ArrayList<String>();
	
	synchronized static public void addNameList(String uname){
		infoUser.add(uname);
	}
	
	static public Iterator<String> readNameList(){
		Iterator<String> it = infoUser.listIterator();
		return it;
	}
	
}
