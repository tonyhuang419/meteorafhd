package guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class GuavaDemo {
	public static void main(String[] args) {
		biMap();
		function();
	}

//	https://code.google.com/p/guava-libraries/wiki/NewCollectionTypesExplained#BiMap
	public static void biMap(){
		BiMap<Integer,String> bi = HashBiMap.create();
		BiMap<String,Integer> bi2 = HashBiMap.create();
		bi.put(1, "A");
		bi.put(2, "B");
		System.out.println(bi.keySet());
		System.out.println(bi.get(1));
		System.out.println(bi.get(2));
		bi2 = bi.inverse();
		System.out.println(bi2.keySet());
		System.out.println(bi2.get("A"));
		System.out.println(bi2.get("B"));
	}

	//	https://code.google.com/p/guava-libraries/wiki/FunctionalExplained
	public static void function(){
		
		Function<String, Integer> lengthFunction = new Function<String, Integer>() {
			public Integer apply(String string) {
				return string.length();
			}
		};
		System.out.println(lengthFunction.apply("1234567890"));


		Predicate<String> allCaps = new Predicate<String>() {
			public boolean apply(String string) {
				return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string);
			}
		};
		System.out.println(allCaps.apply("ABCDEFG"));
	}

}
