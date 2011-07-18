package com.javaeye.i2534;

import java.util.Date;

import com.javaeye.i2534.annotations.Id;
import com.javaeye.i2534.annotations.None;
import com.javaeye.i2534.annotations.Table;

@Table(name = "demo")
public class TestVO {

	@Id("id")
	private String biaoShi;

	private int count;

	private char flag;

	private Date time;
	@None
	private String other;

	public String getBiaoShi() {
		return biaoShi;
	}

	public void setBiaoShi(String biaoShi) {
		this.biaoShi = biaoShi;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
