package edu.handong.csee.se.sugarbag.plugin.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Prints the value of the annotated variable whenever it is modified. 
 */
@Retention(RetentionPolicy.CLASS)
@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER, 
          ElementType.LOCAL_VARIABLE })
public @interface Debug {
    
}
