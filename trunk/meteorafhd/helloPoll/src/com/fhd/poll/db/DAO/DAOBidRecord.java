package com.fhd.poll.db.DAO;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fhd.poll.db.BidRecord;


public class DAOBidRecord extends HibernateDaoSupport implements IDAOBidRecord{

	@Override
	public void save(BidRecord bidRecord){
		getHibernateTemplate().save(bidRecord);
	}

	@Override
	public List<BidRecord> read(){
		return getHibernateTemplate().find("from BidRecord");
	}

	@Override
	public void delete(int bidID){	
		getHibernateTemplate().delete(getHibernateTemplate().get(BidRecord.class , bidID));
	}

	@Override
	public void update(BidRecord bidRecord){
		getHibernateTemplate().update(bidRecord);
	}

	@Override
	public List seeBidRecord(final int mjID) {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,SQLException  {
				Query query = session.createSQLQuery("select Poll.mjname,whobuy,price,biddate " +
						"from BidRecord inner join Poll " +
						"on BidRecord.mjid = Poll.mjid where BidRecord.mjid = :mjid");
				query.setParameter("mjid",mjID);
				return query.list();
			}
		}); 
	}

	@Override
	public long getMaxBidPrice(int mjID){
		List list = getHibernateTemplate().find("select max(price) from BidRecord where mjid = ? ", mjID);
		if(list.get(0)!=null){
			Object obj = list.get(0);
			return Long.parseLong(obj.toString());
		}
		else{
			return 0;
		}
	}
	
	@Override
	public BidRecord getBidRecord(int id){
		return (BidRecord)getHibernateTemplate().get(BidRecord.class, id);
	}
}