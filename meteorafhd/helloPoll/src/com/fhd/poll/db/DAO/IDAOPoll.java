package com.fhd.poll.db.DAO;

import java.util.List;

import com.fhd.poll.db.BidRecord;
import com.fhd.poll.db.Poll;

public interface IDAOPoll {
	public void save(Poll poll);
	public List<Poll> read();
	public void delete(int mjid);
	public void update(Poll poll);
	
	public long getMaxPrice(int mjid);
	public boolean isReName(String mjName);
	
	public Poll getPoll(int mjid);
}
