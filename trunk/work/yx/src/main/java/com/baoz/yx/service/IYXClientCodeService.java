package com.baoz.yx.service;

import com.baoz.yx.entity.YXClientCode;

public interface IYXClientCodeService {
	/**
	 * 根据主健查找客户
	 * @param idNo
	 * @return
	 */
public YXClientCode getYXClientCode(Long idNo);
}
