package com.fhdone.demo2012.service._impl.lucene;

import org.springframework.stereotype.Service;

import com.fhdone.demo2012.service.luncene.SearchService;
import com.fhdone.demo2012.utils.lucene.SearchUtils;


@Service("searchService")
public class SearchServiceImpl implements SearchService {

	public boolean searchLoginfo(String key) {
		try {
			SearchUtils.search(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
