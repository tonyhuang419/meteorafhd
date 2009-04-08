package ibe;

import java.util.Date;

/** 航班查询条件* */
public class SearchHanBanModel {
	/** 航空公司代码* */
	private String airlineCode = "ALL";

	/** 航班号** */
	private String airlineNo;

	/** 到达城市的三字码* */
	private String dst;

	/** 起城市三字码* */
	private String org;

	/** 单程票价* */
	private String singlePrice;

	/** 往返票价* */
	private String roundPrice;

	/** 航线类型* */
	private String lineType;

	/** 机型** */
	private String planestyle;

	/** *旅客类型* */
	private String passType;

	private Date date;

	private String sdate;

	/** 是否是直飞航班* */
	private boolean direct = false;

	/** 是否是电子机票* */
	private boolean e_ticket = false;

	/** 预定此舱位的字符形式* */
	private char cabinType;
	/**三种基本舱位类型**/
    private char cabinTYPE;
	public char getCabinTYPE() {
		return cabinTYPE;
	}

	public void setCabinTYPE(char cabinTYPE) {
		this.cabinTYPE = cabinTYPE;
	}

	public boolean getDirect() {
		return direct;
	}

	public void setDirect(boolean direct) {
		this.direct = direct;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public SearchHanBanModel() {

	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getRoundPrice() {
		return roundPrice;
	}

	public void setRoundPrice(String roundPrice) {
		this.roundPrice = roundPrice;
	}

	public String getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(String singlePrice) {
		this.singlePrice = singlePrice;
	}

	public boolean getE_ticket() {
		return e_ticket;
	}

	public void setE_ticket(boolean e_ticket) {
		this.e_ticket = e_ticket;
	}

	public String getPlanestyle() {
		return planestyle;
	}

	public void setPlanestyle(String planestyle) {
		this.planestyle = planestyle;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public char getCabinType() {
		return cabinType;
	}

	public void setCabinType(char cabinType) {
		this.cabinType = cabinType;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getAirlineNo() {
		return airlineNo;
	}

	public void setAirlineNo(String airlineNo) {
		this.airlineNo = airlineNo;
	}
	
}
