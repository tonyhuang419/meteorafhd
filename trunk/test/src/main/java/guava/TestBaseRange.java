package guava;

import org.junit.Test;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

public class TestBaseRange {
	@Test
	public void testRange(){
		System.out.println("open:"+Range.open(1, 10));
		System.out.println("closed:"+ Range.closed(1, 10));
		System.out.println("closedOpen:"+ Range.closedOpen(1, 10));
		System.out.println("openClosed:"+ Range.openClosed(1, 10));    
		System.out.println("greaterThan:"+ Range.greaterThan(10));
		System.out.println("atLeast:"+ Range.atLeast(10));
		System.out.println("lessThan:"+ Range.lessThan(10));
		System.out.println("atMost:"+ Range.atMost(10));
		System.out.println("all:"+ Range.all());
		System.out.println("closed:"+Range.closed(10, 10));
		System.out.println("closedOpen:"+Range.closedOpen(10, 10));
		//会抛出异常
		System.out.println("open:"+Range.open(10, 10));
	}

	@Test
	public void testRange2(){
		System.out.println("downTo:"+Range.downTo(4, BoundType.OPEN));
		System.out.println("upTo:"+Range.upTo(4, BoundType.CLOSED));
		System.out.println("range:"+Range.range(1, BoundType.CLOSED, 4, BoundType.OPEN)); 
	}

	@Test
	public void testContains3(){
		System.out.println(Range.closed(1, 3).contains(2)); 
		System.out.println(Range.closed(1, 3).contains(4)); 
		System.out.println(Range.lessThan(5).contains(5)); 
		System.out.println(Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3))); 
	}
}
