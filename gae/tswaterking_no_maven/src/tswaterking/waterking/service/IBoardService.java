package tswaterking.waterking.service;

import tswaterking.waterking.entity.Board;

public interface IBoardService {
	
	public Board get(Long boardID);

	public Long saveOrUpdate(Board board);

	public void delete(Long boardID);


}
