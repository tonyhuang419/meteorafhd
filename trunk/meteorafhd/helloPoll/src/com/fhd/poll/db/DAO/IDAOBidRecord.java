package com.fhd.poll.db.DAO;

import java.util.List;

import com.fhd.poll.db.BidRecord;

public interface IDAOBidRecord {
	public void save(BidRecord bidRecord);
	public List<BidRecord> read();
	public void delete(int bidID);
	public void update(BidRecord didRecord);
	public List seeBidRecord(int mjID);
	public long getMaxBidPrice(int mjID);
	
	public BidRecord getBidRecord(int id);
}
