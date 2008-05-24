import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

class Info implements Comparable
{
	String people;
	String book;
	String city;
	String thing;
	Info(String _people,String _book,String _city,String _thing){
		people = _people;
		book = _book;
		city = _city;
		thing = _thing;
	}
	public int compareTo(Object o){
		Info s = (Info)o;
		if(this.people==s.people)
			return 0;
		else if(this.people.compareTo(s.people)>0)
			return 1;
		else
			return -1;
	}
}


public class CollectionX {
	public static  void print(Collection<? extends Object> _set){
		Iterator<? extends Object> it = _set.iterator();
		Info infotemp;
		while(it.hasNext()){
			Object o = it.next();
			infotemp = (Info)o;
			System.out.println(infotemp.people+"\t"+infotemp.book+"\t"+infotemp.city+"\t"+infotemp.thing);
		}
	}
	public static void main(String[] args) throws Exception {
		TestDAO.test("select * from love") ; 
	}
}


class TestDAO {
	static void test(String sql) throws Exception{
		Set<Info> set = new TreeSet<Info>();
		Info temp;
		DAO.setConnection();
		ResultSet rs = DAO.getResultSet(sql);
		ResultSetMetaData md = rs.getMetaData();
		for(int i=1;i<=md.getColumnCount();i++)
			System.out.print(md.getColumnName(i)+"\t");
		System.out.println("");
		while(rs.next()){
			temp = new Info(rs.getString("people"),rs.getString("book"),rs.getString("city"),rs.getString("thing"));
			set.add(temp);
		}
		CollectionX.print(set);
		rs.close();
	}
}
