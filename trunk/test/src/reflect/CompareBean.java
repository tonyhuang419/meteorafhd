package reflect;

import java.lang.reflect.Field;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CompareBean {


	public void trav(Object src , Object compare){
		int fieldsLength = -1;

		Class srcBean = src.getClass();
		Class compareBean = compare.getClass();
//		System.out.println(srcBean.getName());
//		System.out.println(compareBean.getName());
		//check type
		if( !srcBean.equals(compareBean)){
			System.out.println("pity!type not match...classtype error");
			return ;
		}else{
			System.out.println("great!type  match success");
		}

		Field srcFields[] = srcBean.getDeclaredFields(); 
		Field compareFields[] = compareBean.getDeclaredFields(); 

//		check fields length
		if(srcFields.length != compareFields.length){
			System.out.println("pity!fields not match...length error");
			return ;
		}else{
			System.out.println("greate!fields length match success");
			fieldsLength = srcFields.length;
		}
		System.out.println("-------------------------------");
//		System.out.println(fields.length);
//		fields[0].setAccessible(true);
//		System.out.println(fields[0].get(obj));
//		fields[1].setAccessible(true);
//		System.out.println(fields[1].getLong(obj));

		BeanWrapper srcBW = new BeanWrapperImpl(src);
		BeanWrapper compareBW = new BeanWrapperImpl(compare);

		for(int i = 0;i < fieldsLength; i++){
//			System.out.println(fieldsLength);
//			System.out.println(srcBW.getPropertyValue(srcFields[i].getName()));
//			System.out.println(compareBW.getPropertyValue(compareFields[i].getName()));

			Object srcPropertyValue = srcBW.getPropertyValue(srcFields[i].getName());
			Object comparePropertyValue = compareBW.getPropertyValue(compareFields[i].getName());
			//如果一个为空
			if(srcPropertyValue == null){
				//比较另一个是否也为空
				if( comparePropertyValue == null){
					//匹配成功..都为空
					System.out.println("great!"+srcFields[i].getName()+" type match success：they are all null");
				}else{
					//匹配不成功..一个不为空
					System.out.println("pity!"+srcFields[i].getName()+" type match failed：one of they is not null");
				}
				System.out.println("-------------------------------");

			}else{
				if(srcPropertyValue.equals(comparePropertyValue)){
					//匹配成功
					System.out.println("great!"+srcFields[i].getName()+" type match success：");				
				}else{
					//匹配不成功
					System.out.println("pity!"+srcFields[i].getName()+" type match failed：");
				}

				System.out.println("scr field:" + srcFields[i].getName()+" value is -" + srcPropertyValue.toString());
				System.out.println("compare field:" + compareFields[i].getName()+" value is -" + comparePropertyValue.toString());
				System.out.println("-------------------------------");
			}
		}


	}

}
