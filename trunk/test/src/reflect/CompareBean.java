package reflect;

import java.lang.reflect.Field;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CompareBean {


	public void trav(Object src , Object compare){
		int fieldsLength = -1;
		try {
			Class srcBean = src.getClass();
			Class compareBean = compare.getClass();
//			System.out.println(srcBean.getName());
//			System.out.println(compareBean.getName());
			//check type
			if( !srcBean.equals(compareBean)){
				System.out.println("type not match...classtype error");
				return ;
			}else{
				System.out.println("type  match success");
			}
				
			Field srcFields[] = srcBean.getDeclaredFields(); 
			Field compareFields[] = compareBean.getDeclaredFields(); 
			
//			check fields length
			if(srcFields.length != compareFields.length){
				System.out.println("fields not match...length error");
				return ;
			}else{
				System.out.println("fields length match success");
				fieldsLength = srcFields.length;
			}
			System.out.println("-------------------------------");
//			System.out.println(fields.length);
//			fields[0].setAccessible(true);
//			System.out.println(fields[0].get(obj));
//			fields[1].setAccessible(true);
//			System.out.println(fields[1].getLong(obj));

			BeanWrapper srcBW = new BeanWrapperImpl(src);
			BeanWrapper compareBW = new BeanWrapperImpl(compare);

			for(int i = 0;i < fieldsLength; i++){
//				System.out.println(fieldsLength);
//				System.out.println(srcBW.getPropertyValue(srcFields[i].getName()));
//				System.out.println(compareBW.getPropertyValue(compareFields[i].getName()));
				
				Object srcPropertyValue = srcBW.getPropertyValue(srcFields[i].getName());
				Object comparePropertyValue = compareBW.getPropertyValue(compareFields[i].getName());

				if(srcPropertyValue.equals(comparePropertyValue)){
					//匹配成功
					System.out.println(srcFields[i].getName()+" type match success：");				
				}else{
					//匹配不成功
					System.out.println(srcFields[i].getName()+" type match failed：");
				}
				System.out.println("scr field:" + srcFields[i].getName()+" value is -" + srcPropertyValue.toString());
				System.out.println("compare field:" + compareFields[i].getName()+" value is -" + comparePropertyValue.toString());
				System.out.println("-------------------------------");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
