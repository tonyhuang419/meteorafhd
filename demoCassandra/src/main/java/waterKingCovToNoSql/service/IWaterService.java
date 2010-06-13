package waterKingCovToNoSql.service;

import java.util.List;

import waterKingCovToNoSql.entity.Board;


public interface IWaterService {

	public List<Board> doGeBoard( int min , int max );

	public void copyMySQLToCassandra( int min , int max );

}
