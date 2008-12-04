package def;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;




public class MapTest {

	public void testMain(){
//		test1();
		test2();
//		test3();
	}

	public void test1(){
		Map map = new HashMap();
		map.put(1, "a");
		map.put(2, "b");
		map.put(3, "c");
		map.put(4, "d");
		Set keys1 = map.keySet();
		Set keys2 = map.keySet();
		Set keys3 = map.keySet();

		keys1.remove(1);
		System.out.println(keys1);
		System.out.println(keys2);
		System.out.println(keys3);	   
	}

	public void test2(){
		Map m1 = new HashMap();
		m1.put(1, "1k");
		m1.put(2, "2k");

//		System.out.println(m1.get(1));
//		System.out.println(m1.get(2));

		System.out.println(m1.keySet().size());
		
		for(Object m: m1.keySet()){
			System.out.println(m+":" + m1.get(m)); 
		}


		Iterator it = m1.keySet().iterator();
		while(it.hasNext()){
			Object o =  it.next();
			System.out.print(o+":");
			System.out.println(m1.get(o));
		}
	}
	
	public void test3(){
		String str1="";
		String str2="";
		
		Map<Long,String> m1 = new HashMap<Long,String>();
		m1.put(1L, "1str");
		m1.put(2L, "2str");
		
		
		for(Object m: m1.keySet()){
			System.out.println(m+":" + m1.get(m));
			str1 = str1+ m +",";
			str2 = str2+ m1.get(m)+",";
		}
		System.out.println(str1);
		System.out.println(str2);
		
		Iterator it = m1.keySet().iterator();
		while(it.hasNext()){
			Object o =  it.next();
			System.out.print(o+":");
			System.out.println(m1.get(o));
		}

		
	}

	public static void main(String[] args) {

		new MapTest().testMain();

	}
}
