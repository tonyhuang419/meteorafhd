package tswaterking.waterking.action;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tswaterking.waterking.entity.Board;
import tswaterking.waterking.service.IBoardService;

public class BoardAction extends HttpServlet {
	private static final long serialVersionUID = -7891023316296544754L;
	
	@Autowired
	@Qualifier("boardService")
	private IBoardService boardService;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		boardService.saveOrUpdate(new Board());
		resp.sendRedirect("");
	}


	public IBoardService getBoardService() {
		return boardService;
	}


	public void setBoardService(IBoardService boardService) {
		this.boardService = boardService;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
