package partner.chainOfResponsibility;

public class Girl {
	public static void main(String[] args){
		Boy boy=new Boy(false,false,true);//这个boy没有车，也没有房，不过很有责任心
		Handler handler=new CarHandler(new HouseHandler(new ResponsibilityHandler(null)));//也可以使用setHanlder方法
		handler.handleRequest(boy);
	}
}
