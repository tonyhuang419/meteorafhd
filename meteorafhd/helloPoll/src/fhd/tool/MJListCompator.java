package fhd.tool;

import java.util.Comparator;

import com.fhd.poll.db.Poll;

public class MJListCompator implements Comparator {
	Poll p1 = new Poll();
	Poll p2 = new Poll();
	public int compare(Object e1,Object e2){
		p1 = (Poll)e1;
		p2 =(Poll)e2;
		return p2.getId() - p1.getId();
	}
}
