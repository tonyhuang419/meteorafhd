package com.fhdone.demo2012.service.impl;

import org.springframework.stereotype.Service;

import com.fhdone.demo2012.service.SearchService;
import com.fhdone.demo2012.utils.lucene.SearchUtils;


@Service("searchService")
public class SearchServiceImpl implements SearchService {

	public boolean searchLoginfo(String key) {
		try {
			SearchUtils.search2(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
