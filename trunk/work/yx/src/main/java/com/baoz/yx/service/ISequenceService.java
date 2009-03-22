package com.baoz.yx.service;

import com.baoz.yx.utils.SequenceKey;

/**
 * 类ISequenceService.java的实现描述：获得序列好值
 * @author xurong Jul 23, 2008 11:16:42 AM
 */
public interface ISequenceService {
	public Long getNextValue(SequenceKey key);
}