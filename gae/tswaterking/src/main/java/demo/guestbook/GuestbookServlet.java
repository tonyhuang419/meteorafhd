package demo.guestbook;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 5289380004980450200L;
	private static final Logger logger = Logger.getLogger(GuestbookServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
		this.doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		logger.info("SignGuestbookServlet doPost");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String content = req.getParameter("content");
		if (content == null) {
			content = "(No greeting)";
		}
		if (user != null) {
			logger.info("Greeting posted by user " + user.getNickname() + ": " + content);
		} else {
			logger.info("Greeting posted anonymously: " + content);
		}
		resp.sendRedirect("/demo/guestbook/guestbook.jsp");
	}
}
