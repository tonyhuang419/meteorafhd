package guiceDemo.helloworld;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * Indicates we want the blue version of a binding.
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface Blue {
	
}
