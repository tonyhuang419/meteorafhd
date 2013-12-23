package guava;

import org.junit.Test;

import com.google.common.base.Objects;

public class ObjectTest {
	
	@Test
	public void hashcodeTest() {
		System.out.println(Objects.hashCode("a"));
		System.out.println(Objects.hashCode("a"));
		System.out.println(Objects.hashCode("a","b"));
		System.out.println(Objects.hashCode("b","a"));
		System.out.println(Objects.hashCode("a","b","c"));

		Person person=new Person("peida",23);
		System.out.println(Objects.hashCode(person));
		System.out.println(Objects.hashCode(person));
	}

	@Test
	public void toStringTest() {
		System.out.println(Objects.toStringHelper(this).add("x", 1).toString());
		System.out.println(Objects.toStringHelper(Person.class).add("x", 1).toString());

		Person person=new Person("peida",23);
		String result = Objects.toStringHelper(Person.class)
				.add("name", person.name)
				.add("age", person.age).toString();      
		System.out.print(result);
	}
}
