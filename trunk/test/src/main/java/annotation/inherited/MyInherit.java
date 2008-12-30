package annotation.inherited;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited  
@Retention(RetentionPolicy.RUNTIME)   
public @interface MyInherit {   
	String value();   
}
