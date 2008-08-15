package pinyinOrder;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class User implements Comparable{
	private String name;
	private int age;

	User(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return this.name;
	}

	public int getAge() {
		return this.age;
	}


	public int compareTo(Object other) {
		if (other == null) {
			return 1;
		}
		User u = (User)other;
		Comparator comparator = Collator.getInstance(Locale.CHINA);
		return comparator.compare(name, u.getName());
	}


	public static void main(String[] args) {   
		User user01 = new User("阿三", 18);   
		User user02 = new User("段蕾", 19);   
		User user03 = new User("长江", 11);   
		User user04 = new User("版主", 25); 
		User user05 = new User("版x", 25); 

		Set<User> set = new TreeSet<User>();   

		set.add(user01);   
		set.add(user02);   
		set.add(user03);   
		set.add(user04);  
		set.add(user05);
		for (User user : set) {   
			System.out.print(user.getName() + "---");   
		}   
	}
}