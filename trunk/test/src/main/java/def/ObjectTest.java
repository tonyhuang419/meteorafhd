package def;
import java.util.ArrayList;
import java.util.List;


public class ObjectTest {

	public void test(Object...x){
		for(Object obj:x)
			System.out.print(obj+" ");
	}

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		new ObjectTest().test("a","b","c","d","r");

	}
}
