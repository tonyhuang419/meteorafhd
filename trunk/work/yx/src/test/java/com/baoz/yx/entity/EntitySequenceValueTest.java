package com.baoz.yx.entity;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.EntitySequenceTest.SysIdMapping;

/**
 * 类EntitySequenceValueTest.java的实现描述：TODO 类实现描述
 * 
 * @author xurong Sep 27, 2008 11:26:44 AM
 */
public class EntitySequenceValueTest extends YingXiaoBaseTest {
	IYXCommonDao yxCommonDao;

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
	}

	public IYXCommonDao getYxCommonDao() {
		return yxCommonDao;
	}

	public void setYxCommonDao(IYXCommonDao commonDao) {
		this.yxCommonDao = commonDao;
	}

	public void testSequenceValue() throws Exception {
		File classDir = new File(this.getClass().getClassLoader().getResource("log4j.properties").getPath()).getParentFile();
		File entityClassDir = new File(classDir,"com\\baoz\\yx\\entity");
		Collection<File> classList = FileUtils.listFiles(entityClassDir, new String[]{"class"}, true);
		List<SysIdMapping> mlist = new ArrayList<SysIdMapping>();
		for (File file : classList) {
			String classNamePath = file.getPath().substring(classDir.getPath().length()+1);
			String className = StringUtils.replace(FilenameUtils.getFullPath(classNamePath), File.separator, ".")+FilenameUtils.getBaseName(classNamePath);
			Class entityClass = Class.forName(className);
			SysIdMapping m = new SysIdMapping();
			m.setEntityClass(entityClass);
			m.setTableName(getTableName(entityClass));
			m.setSysId(getSysId(entityClass));
			m.setPkColumnName(getPkColumnName(entityClass));
			mlist.add(m);
		}
		Collections.sort(mlist,new Comparator<SysIdMapping>(){
			public int compare(SysIdMapping o1, SysIdMapping o2) {
				if(o1.getSysId() != null && o2.getSysId() != null){
					return o1.getSysId().compareTo(o2.getSysId());
				}else if(o1.getSysId() == null){
					return -1;
				}else{
					return 1;
				}
			}
		});
		System.out.println("");
		for (SysIdMapping sysIdMapping : mlist) {
			if(sysIdMapping.getSysId() != null){
				try {
					Number sMaxValue = (Number) yxCommonDao.uniqueSQLResult("select s.tableid from sysid s where s.id = ? ", sysIdMapping.getSysId());
					Number tMaxValue = (Number) yxCommonDao.uniqueSQLResult("select max("+sysIdMapping.getPkColumnName()+") from "+sysIdMapping.getTableName());
					if((sMaxValue == null && tMaxValue != null ) || ( sMaxValue.longValue() < tMaxValue.longValue() )){
						System.out.println(StringUtils.rightPad(sysIdMapping.getEntityClass().getSimpleName()+"", 40)+
						StringUtils.rightPad(sysIdMapping.getTableName()+"", 40)+
						StringUtils.rightPad(sysIdMapping.getPkColumnName()+"", 25)+
						StringUtils.rightPad(sysIdMapping.getSysId()+"", 5)+
						StringUtils.rightPad(sMaxValue+"", 5)+
						StringUtils.rightPad(tMaxValue+"", 5));
					}
				} catch (NullPointerException e) {
					//忽略
				}catch (RuntimeException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	private String getTableName(Class entityClass){
		Table annoTable = (Table)entityClass.getAnnotation(Table.class);
		if(annoTable != null){
			return annoTable.name();
		}
		return null;
	}
	
	private String getSysId(Class entityClass){
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			TableGenerator annoTable = (TableGenerator)field.getAnnotation(TableGenerator.class);
			if(annoTable != null){
				return annoTable.pkColumnValue();
			}
		}
		return null;
	}

	private String getPkColumnName(Class entityClass){
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			Id id = (Id)field.getAnnotation(Id.class);
			if(id != null){
				Column col = field.getAnnotation(Column.class);
				if(col != null && StringUtils.isNotEmpty(col.name())){
					return col.name();
				}else{
					return field.getName();
				}
			}
		}
		return null;
	}
}
