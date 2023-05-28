package edu.handong.csee.se.sugarbag.plugin;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER})
public @interface Positive {
}
