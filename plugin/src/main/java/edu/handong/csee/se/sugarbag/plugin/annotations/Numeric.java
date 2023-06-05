package edu.handong.csee.se.sugarbag.plugin.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Numeric {
    double min() default Double.NEGATIVE_INFINITY;
    double max() default Double.POSITIVE_INFINITY;
    String numericType() default "int"; // expandible to be "float", "double", etc.
}
