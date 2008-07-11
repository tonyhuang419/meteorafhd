package commons.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.collections.iterators.LoopingIterator;
import org.apache.commons.lang.StringUtils;

public class IteratorUsage {

	public static void main(String[] args) {
		demoIteratorUsage();
	}



	public static void demoIteratorUsage() {
		System.out.println(StringUtils.center(" demoClosureUsage ", 40, "="));
		// data setup
		String[] weekDays = {
				"Monday", "Tuesday", "Wednesday",
				"Thursday", "Friday", "Saturday", "Sunday"
		};

		List weekDayList = Arrays.asList(weekDays);
		// workdays

		Iterator iter1 = new ArrayListIterator(weekDays, 0, 5);
		printColl("Partial:", iter1, 5);

		// loop
		Iterator iter2 = new LoopingIterator(weekDayList);
		printColl("Loop:", iter2, 10); 

		// looping workdays
		Predicate notWeekendPredicate = new Predicate() {
			public boolean evaluate(Object obj) {
				String str = (String) obj;
				if ("Saturday".equalsIgnoreCase(str)) {
					return false;
				}

				if ("Sunday".equalsIgnoreCase(str)) {
					return false;
				}
				return true;
			}
		};

		Iterator iter3 = new FilterIterator(
				new LoopingIterator(weekDayList),
				notWeekendPredicate
		);

		printColl("No Weekends loop:", iter3, 12);
		System.out.println(StringUtils.repeat("=", 40));
	}


	public static void printColl(String label, Iterator iter, int maxCount) {
		if (StringUtils.isNotBlank(label)) {
			System.out.println(label);
		}

		int i = 0;
		while (iter.hasNext() && i < maxCount) {
			System.out.println("# " + iter.next() + " #");
			i++;
		}
	}
}
