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
//		�����������Ϊͼ�񣬸�ʽΪjpeg
		//res.setContentType("image/jpg");
		try {
			HttpSession session = req.getSession(true);
//			�������������Ӧ�ͻ��˶����������У����ɵ�ͼƬ�а���6���ַ�
			//String v = RandomGraphic.createInstance(6).drawAlpha(RandomGraphic.GRAPHIC_JPEG,res.getOutputStream());
			String v = RandomGraphic.createInstance(4).drawNumber(RandomGraphic.GRAPHIC_JPEG,res.getOutputStream());
//			���ַ�����ֵ������session�У����ں��û��ֹ��������֤��Ƚϣ��Ƚϲ��ֲ��Ǳ��������ص㣬����
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