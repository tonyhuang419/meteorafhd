package com.fhdone.demo2012.service.impl;

import org.springframework.stereotype.Service;

import com.fhdone.demo2012.service.SearchService;
import com.fhdone.demo2012.utils.lucene.SearchUtils;


@Service("searchService")
public class SearchServiceImpl implements SearchService {

	public void searchLoginfo(String key) {
		try {
			SearchUtils.search(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
