package tswaterking.waterking.service.impl;

import org.springframework.orm.jdo.support.JdoDaoSupport;

import tswaterking.waterking.entity.Board;
import tswaterking.waterking.service.IBoardService;


//@Service("boardService")
public class BoardService extends JdoDaoSupport implements IBoardService{

	@Override
	public Board get(Long boardID){
		return (Board)getJdoTemplate().getObjectById(boardID);
	}
	
	@Override
	public Long saveOrUpdate(Board board) {
		getJdoTemplate().makePersistent(board);
		return board.getId();
	}

	@Override
	public void delete(Long boardID) {
		getJdoTemplate().deletePersistent(this.get(boardID));

	}

}
