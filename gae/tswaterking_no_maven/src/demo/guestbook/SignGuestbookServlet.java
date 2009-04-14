package demo.guestbook;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class SignGuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 4554837471683184923L;

	//private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());


	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String content = req.getParameter("content");
		Date date = new Date();
		Greeting greeting = new Greeting(user, content, date);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(greeting);
		} finally {
			pm.close();
		}

		resp.sendRedirect("/demo/guestbook.jsp");
	}

}
