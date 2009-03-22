package com.baoz.yx.tools.compare;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baoz.yx.tools.compare.beans.CompareContext;

public class CompareBeanTools {
	protected Log logger = LogFactory.getLog(this.getClass());

	@SuppressWarnings("unchecked")
	public List<CompareContext> compare(Object src , Object target,String excludeField[]){
		List<CompareContext> ccList = new ArrayList<CompareContext> ();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,##0.00");
		Object srcObj ;
		Object tarObj ;
		Class srcClass = src.getClass();
		Class targetClass = target.getClass();


		if( !srcClass.equals(targetClass)){
			logger.info("类型不匹配");
			return null;
		}else{
			logger.info("类型匹配成功");
		}

		Field srcFields[] = srcClass.getDeclaredFields(); 	
		Field targetFields[] = targetClass.getDeclaredFields(); 	
		int lengthSrc = srcFields.length;
		Field.setAccessible(srcFields, true); 
		Field.setAccessible(targetFields, true); 

		int mark=0;
		for(int i = 0;i < lengthSrc; i++){

			try{
				if(excludeField!=null){
					for(String fieldName : excludeField){
						if( srcFields[i].getName().equals(fieldName) ){
							mark=1;
							break;
						}
					}
					if(mark==1){
						mark=0;
						continue;
					}
				}

				srcObj = srcFields[i].get(src);
				tarObj = targetFields[i].get(target);
				if(srcObj==null) { srcObj = ""; }
				if(tarObj==null) { tarObj = ""; }
				String srcTmp="";
				String tarTmp="";

				if( String.class.equals( srcObj.getClass()) && String.class.equals( tarObj.getClass())  ){
					srcTmp  = srcObj.toString().trim();
					tarTmp  = tarObj.toString().trim();
					if( !srcTmp.equals(tarTmp) ){
						ccList.add(new CompareContext( srcFields[i].getName(), srcTmp, tarTmp ));
					}
				}
				else if ( Date.class.equals( tarObj.getClass()) ||  Date.class.equals( srcObj.getClass())  ){
					if(null!=srcObj && !"".equals(srcObj)){
						srcTmp  = dateFormat.format(srcObj);
					}
					if(null!=tarObj && !"".equals(tarObj)){
						tarTmp  = dateFormat.format(tarObj);
					}
					if( !srcTmp.equals(tarTmp) ){
						ccList.add(new CompareContext( srcFields[i].getName(), srcTmp, tarTmp ));
					}				
				}
				else if ( Double.class.equals( tarObj.getClass()) ||  Double.class.equals( srcObj.getClass())  ){
					if(null!=srcObj && !"".equals(srcObj)){
						srcTmp  = decimalFormat.format(srcObj);
					}
					if(null!=tarObj && !"".equals(tarObj)){
						tarTmp  = decimalFormat.format(tarObj);
					}
					if( !srcTmp.equals(tarTmp) ){
						ccList.add(new CompareContext( srcFields[i].getName(), srcTmp, tarTmp ));
					}				
				}
				else{
					if( ! srcObj.equals(tarObj) ){
						ccList.add(new CompareContext( srcFields[i].getName(), srcObj.toString(), tarObj .toString()));
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}			
		return ccList;
	}
}
