package statistics.Action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import statistics.dept.Statistics;


public class StatisticsAction implements SessionAware {
	private Map session;
	private Statistics s;
	
	@Override
	public void setSession(Map session) {
		this.session = session;	
	}
	
	public String execute(){
		s = new Statistics();
		session.put("statistics_dept_list", s.getListXIterator2());
		return "success";
	}
}
