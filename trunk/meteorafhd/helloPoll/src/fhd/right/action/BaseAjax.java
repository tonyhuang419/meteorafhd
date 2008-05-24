//该类在applicationContext-LoginAction.xml中初始化

package fhd.right.action;

import com.fhd.poll.db.BidRecord;
import com.fhd.poll.db.Poll;
import com.fhd.poll.db.DAO.IDAOBidRecord;
import com.fhd.poll.db.DAO.IDAOPoll;

public class BaseAjax {
	protected Poll poll = null;
	protected BidRecord bidRecord = null;
	protected IDAOPoll pollDAOBean = null; 
	protected IDAOBidRecord bidRecordBean = null;
	
	
	public void setPollDAOBean(IDAOPoll pollDAOBean) {
		this.pollDAOBean = pollDAOBean;
	}
	public void setBidRecordBean(IDAOBidRecord bidRecordBean) {
		this.bidRecordBean = bidRecordBean;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public void setBidRecord(BidRecord bidRecord) {
		this.bidRecord = bidRecord;
	}
	

//	BaseAjax(){
//		ApplicationContext context = new FileSystemXmlApplicationContext("WebRoot/WEB-INF/applicationContext-DB.xml");
//		pollDAOBean = (IDAOPoll)context.getBean("DAOPoll");
//		bidRecordBean = (IDAOBidRecord)context.getBean("DAOBidRecord");
//	}
	
}
