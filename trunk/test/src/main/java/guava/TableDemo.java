package guava;

import org.junit.Test;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Table;

public class TableDemo {
	@Test
	public void TableTest(){
		Table<String, Integer, String> aTable = HashBasedTable.create();  

		for (char a = 'A'; a <= 'C'; ++a) {  
			for (Integer b = 1; b <= 3; ++b) {   
				aTable.put(Character.toString(a), b, String.format("%c%d", a, b));  
			}  
		}  

		System.out.println(aTable.column(2));  
		System.out.println(aTable.row("B"));   
		System.out.println(aTable.get("B", 2));  

		System.out.println(aTable.contains("D", 1));   
		System.out.println(aTable.containsColumn(3));   
		System.out.println(aTable.containsRow("C"));  
		System.out.println(aTable.columnMap()); 
		System.out.println(aTable.rowMap());   

		System.out.println(aTable.remove("B", 3)); 
	}


	@Test
	public  void ClassToInstanceMapTest() {
		ClassToInstanceMap<String> classToInstanceMapString =MutableClassToInstanceMap.create();
		ClassToInstanceMap<Person> classToInstanceMap =MutableClassToInstanceMap.create();

		classToInstanceMapString.put(String.class, "peida");
		System.out.println("string:"+classToInstanceMapString.getInstance(String.class));

		Person person= new Person("peida",20);
		System.out.println("person name :"+person.name+" age:"+person.age);
		classToInstanceMap.putInstance(Person.class,person);
		Person person1=classToInstanceMap.getInstance(Person.class);
		System.out.println("person1 name :"+person1.name+" age:"+person1.age);
	}
}
