package chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class info{
	String name;
	String info;
	info(String name,String info){
		this.name = name;
		this.info = info;
	}	
}

public class Collection {
	static List<info> infoList = new ArrayList<info>();
	
	synchronized static public void addList(String name,String info){
		infoList.add(new info(name,info));
	}
	
	static public Iterator<info> readList(){
		Iterator<info> it = infoList.listIterator();
		return it;
	}
	
	
	static List<String> infoUser = new ArrayList<String>();
	
	synchronized static public void addNameList(String uname){
		infoUser.add(uname);
	}
	
	static public Iterator<String> readNameList(){
		Iterator<String> it = infoUser.listIterator();
		return it;
	}
	
}
