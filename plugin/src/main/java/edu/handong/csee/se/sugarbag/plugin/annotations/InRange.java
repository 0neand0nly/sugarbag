package edu.handong.csee.se.sugarbag.plugin;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER})
public @interface InRange {
	int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
}