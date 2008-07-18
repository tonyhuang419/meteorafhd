package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.SysItem;

public interface IHrService {
	public String getMainProjectCode(String proectlevel);

	public String getProjectCode(String mainprojectcode);

	public String getCode(String projperty,String idxcode,String projectcode);

	public List<SysItem> getSysItems(String itemid);
}
