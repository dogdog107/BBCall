package com.bbcall.functions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

public class PageInfoToMap {
	public Map<String, Object> pageInfoMap(List pagelist) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		PageInfo page = new PageInfo(pagelist);
		map.put("currentPageNum", page.getPageNum());
		map.put("currentPageSize", page.getPageSize());
		map.put("totalpagesNum", page.getPages());
		map.put("firstPageNum", page.getFirstPage());
		map.put("lastPageNum", page.getLastPage());
		map.put("prePageNum", page.getPrePage());
		map.put("nextPageNum", page.getNextPage());
		map.put("currentStartRow", page.getStartRow());
		map.put("currentEndRow", page.getEndRow());
		map.put("totalRow", page.getTotal());
		
		System.out.println(map);
		
		return map;
	}
}
