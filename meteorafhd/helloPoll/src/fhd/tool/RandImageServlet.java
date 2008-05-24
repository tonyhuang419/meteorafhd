package fhd.tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RandImageServlet extends HttpServlet {
	
	public RandImageServlet() {
		super();
	}
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
//		设置输出内容为图像，格式为jpeg
		//res.setContentType("image/jpg");
		try {
			HttpSession session = req.getSession(true);
//			将内容输出到响应客户端对象的输出流中，生成的图片中包含6个字符
			//String v = RandomGraphic.createInstance(6).drawAlpha(RandomGraphic.GRAPHIC_JPEG,res.getOutputStream());
			String v = RandomGraphic.createInstance(4).drawNumber(RandomGraphic.GRAPHIC_JPEG,res.getOutputStream());
//			将字符串的值保留在session中，便于和用户手工输入的验证码比较，比较部分不是本文讨论重点，故略
			session.setAttribute("checkNO",v);
//			System.out.println(s.getAttribute("v").toString());
//			System.out.println(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//static boolean checkNO(String _checkNO){
		//HttpSession s =
	//}
}