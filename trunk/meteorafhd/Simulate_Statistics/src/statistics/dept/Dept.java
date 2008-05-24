package statistics.dept;

import java.text.NumberFormat;

public abstract class Dept{
	private String deptName;
	private double target;
	private double accomplish;
	private String precentage;
	private double balance;
	
	public Dept(String deptName,Dept dept[]){
		this.deptName = deptName;
		for(int i=0;i<dept.length;i++){
			this.target += dept[i].getTarget();
			this.accomplish += dept[i].getAccomplish();
		}
		this.setPrecentage(Tool.c_precentage(target, accomplish));
		this.setBalance(Tool.balance(target, accomplish));
	}
	
	public Dept(String deptName,double target,double accomplish){
		this.setDeptName(deptName);
		this.setTarget(target);
		this.setAccomplish(accomplish);
		this.setPrecentage(Tool.c_precentage(target, accomplish));
		this.setBalance(Tool.balance(target, accomplish));
	}
	public double getTarget() {
		return target;
	}
	public void setTarget(double target) {
		this.target = target;
	}
	public double getAccomplish() {
		return accomplish;
	}
	public void setAccomplish(double accomplish) {
		this.accomplish = accomplish;
	}
	public String getPrecentage() {
		return precentage;
	}
	public void setPrecentage(String precentage) {
		this.precentage = precentage;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}	
}
