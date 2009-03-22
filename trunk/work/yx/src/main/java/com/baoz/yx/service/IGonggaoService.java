package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.Gonggao;

public interface IGonggaoService {

	/**
	 *删除公告
	 */
	public List<String>   delGongGaos( List<Gonggao>  gonggaoList );


}
