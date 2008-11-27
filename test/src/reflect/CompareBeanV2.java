package reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CompareBeanV2 {
	protected Log logger = LogFactory.getLog(this.getClass());

	public List<CompareContext> compare(Object src , Object target,String excludeField[]){
		List<CompareContext> ccList = new ArrayList<CompareContext> ();
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

				if( String.class.equals( srcObj.getClass()) ){
					String srcTmp  = srcObj.toString().trim();
					String tarTmp  = tarObj.toString().trim();
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
