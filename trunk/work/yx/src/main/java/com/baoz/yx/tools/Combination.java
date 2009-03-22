package com.baoz.yx.tools;
import java.util.*;

public class Combination {

	public static List combination(List list, int n) {
		List returnList=new ArrayList();
		int m = list.size();
		if (m < n)
			throw new IllegalArgumentException("Error   m   <   n");
		BitSet bs = new BitSet(m);
		for (int i = 0; i < n; i++)
			bs.set(i, true);
		do {
			returnList.add(printAll(list, bs));
		} while (moveNext(bs, m));
		return returnList;
	}

	private static boolean moveNext(BitSet bs, int m) {
		int start = -1;
		while (start < m)
			if (bs.get(++start))
				break;
		if (start >= m)
			return false;

		int end = start;
		while (end < m)
			if (!bs.get(++end))
				break;
		if (end >= m)
			return false;
		for (int i = start; i < end; i++)
			bs.set(i, false);
		for (int i = 0; i < end - start - 1; i++)
			bs.set(i);
		bs.set(end);
		return true;
	}

	private static List printAll(List array, BitSet bs) {
		List retList=new ArrayList();
		for (int i = 0; i < array.size(); i++)
			if (bs.get(i)){
				retList.add(array.get(i));
				}
		return retList;
	}
}
