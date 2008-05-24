package fhd.right.action;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

public class ToolBidReocrdAction extends BaseAjax {
	
	public String create(int mjid,long price,Date bidDate,String whoBuy,HttpSession session){
		if(session.getAttribute("username").equals("")){
			return "未登录";
		}		
		if(price <= pollDAOBean.getMaxPrice(mjid) || price <= bidRecordBean.getMaxBidPrice(mjid)){	
			return "什么鸟价，太低！";
		}
		
		bidRecord.setMjid(mjid);
		bidRecord.setPrice(price);
		bidRecord.setWhoBuy(whoBuy);
		bidRecord.setBidDate(bidDate);
		bidRecordBean.save(bidRecord);
		
		poll = pollDAOBean.getPoll(mjid);
		poll.setPrice(price);
		pollDAOBean.update(poll);
		return "出价完成";
	}
	
	public String showBidRecord(int mjid,HttpSession session){
		if(session.getAttribute("username").equals("")){
			return "未登录";
		}
		
		String recordStr = "";
		Iterator itt = bidRecordBean.seeBidRecord(mjid).iterator();
		if(itt.hasNext()){
			while(itt.hasNext()){
				recordStr += "<tr>";
				Object[] ser = (Object[])itt.next();	
				String mjname = (String)ser[0];
				recordStr += "<td>" + mjname + "</td>";		
				String whobuy = String.valueOf(ser[1]);
				recordStr += "<td>" +  whobuy + "</td>";		
				String price = String.valueOf(ser[2]);
				recordStr +=  "<td>" + price + "</td>";			
				String date = String.valueOf(ser[3]);
				recordStr +=  "<td>" + date+ "</td>";	
				recordStr += "</tr>";
			}
			return recordStr;
		}
		else{
			return "无出价记录";
		}
	}
}