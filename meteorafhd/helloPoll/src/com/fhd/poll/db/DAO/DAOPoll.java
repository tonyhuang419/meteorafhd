package com.fhd.poll.db.DAO;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fhd.poll.db.Poll;

public class DAOPoll extends HibernateDaoSupport implements IDAOPoll{

	@Override
	public void save(Poll poll){
		getHibernateTemplate().save(poll);
		//手动异常，回滚测试用
		/*
		try{
			new ThrowDemo();
		}catch(TestException e){
			e.printStackTrace();
		}
		*/
	}

	@Override
	public List<Poll> read(){
		return getHibernateTemplate().find("from Poll");
	}

	@Override
	public void delete(int mjid){	
		getHibernateTemplate().delete(getHibernateTemplate().get(Poll.class , mjid));	
	}

	@Override
	public void update(Poll poll){
		getHibernateTemplate().update(poll);	
	}

	@Override
	public long getMaxPrice(int mjid){
		List list = getHibernateTemplate().find("select price from Poll where id = ? " , mjid);
		if(list.get(0)!=null){
			Object obj = list.get(0);
			return Long.parseLong(obj.toString());
		}
		else{
			return -1;
		}
	}

	@Override
	public boolean isReName(String mjName){
		List list = getHibernateTemplate().find("from Poll where name = ? " , mjName);
		if(list.size()!=0){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Poll getPoll(int mjid){
		return (Poll)getHibernateTemplate().get(Poll.class, mjid);
	}
}
//自定义异常
/*
class TestException extends Exception{
	TestException(){
		super("i am a test Exception");
	}
}

class ThrowDemo{
	ThrowDemo() throws TestException{
		throw new TestException();
	}
}
*/
