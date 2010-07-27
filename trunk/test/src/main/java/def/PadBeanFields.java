package def;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public class PadBeanFields extends org.springframework.beans.BeanUtils{

	/**
	 * src属性覆盖objPaded属性，如果属性为null，不覆盖 
	 */
	static public Object padBean(Object src ,  Object objPaded ,String excludeField[]){

		BeanWrapper padedBean = new BeanWrapperImpl(false);
		BeanWrapper tarBean = new BeanWrapperImpl(false);
		padedBean.setWrappedInstance(objPaded);
		tarBean.setWrappedInstance(src);

		Class paded = objPaded.getClass();
		Class tar = src.getClass();

		if( paded.equals(tar)){  
			Field tarFields[] = tar.getDeclaredFields();      
			Field.setAccessible(tarFields, true); 

			int length = tarFields.length;
			int mark=0;
			for(int i = 0;i < length; i++){
				try{
					if(excludeField!=null){
						for(int j=0; j<excludeField.length;j++){
							String fieldName = excludeField[j];
							if( tarFields[i].getName().equals(fieldName) ){
								mark=1;
								break;
							}
						}
						if(mark==1){
							mark=0;
							continue;
						}
					}
					if( tarFields[i].get(src)!=null  ){
						//避免属性第一个字母大写 调用方法makeFristLetterLower
						padedBean.setPropertyValue( makeFristLetterLower( tarFields[i].getName())  ,tarFields[i].get(src));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return objPaded;
	}
	
	
	/**
	 * 根据spring改写的方法
	 * source属性覆盖target属性，如果属性为null，不覆盖 
	 */
	public static void padBeanBySpring(Object source, Object target,  String[] ignoreProperties) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (int i = 0; i < targetPds.length; i++) {
			PropertyDescriptor targetPd = targetPds[i];
			if (targetPd.getWriteMethod() != null &&
					(ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source, new Object[0]);
						// value为空，不做覆盖
						if (value != null) { 
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, new Object[] {value});
						}
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}  

	static public String makeFristLetterLower(String str){
		if(str!=null){
			String fristLetter = StringUtils.lowerCase(str.substring(0,1));
			str = fristLetter + str.substring(1,str.length());
		}
		return str;
	}


	static public Object convertObjectToObject(Object dest , Object orig,Map map){

		BeanWrapper destBean = new BeanWrapperImpl(false);
		BeanWrapper origBean = new BeanWrapperImpl(false);
		destBean.setWrappedInstance(dest);
		origBean.setWrappedInstance(orig);

		Map.Entry entry;
		Object key;
		Object value;
		for(Iterator itor = map.entrySet().iterator(); itor.hasNext();){
			entry = (Map.Entry) itor.next(); 
			key = entry.getKey();
			value = entry.getValue(); 
			destBean.setPropertyValue( makeFristLetterLower( value.toString())  , origBean.getPropertyValue(key.toString()) );
		}
		return dest;
	}

}
