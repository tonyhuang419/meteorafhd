package annotation;

//注解Annotation类不能显示(explicit)extends或implements任何类   
//不定义任何属性就叫maket annotation   
public @interface DefineAnnotation {   

	//定义一个属性,有属性的话，必须赋值，除非有默认default   
	public String value() default "aaa";   

}   

