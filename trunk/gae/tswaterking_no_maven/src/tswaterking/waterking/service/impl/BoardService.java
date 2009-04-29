package tswaterking.waterking.service.impl;



import javax.jdo.PersistenceManager;

import tswaterking.demo.guestbook.PMF;
import tswaterking.waterking.entity.Board;
import tswaterking.waterking.service.IBoardService;


//@Service("boardService")
public class BoardService implements IBoardService{

	@Override
	public Board get(Long boardID){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Board board = (Board)pm.getObjectById(boardID);
		pm.close();
		return board;
	}

	@Override
	public Long saveOrUpdate(Board board) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(board);
		pm.close();
		return board.getId();
	}

	@Override
	public void delete(Long boardID) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(this.get(boardID));
		pm.close();
	}

}
