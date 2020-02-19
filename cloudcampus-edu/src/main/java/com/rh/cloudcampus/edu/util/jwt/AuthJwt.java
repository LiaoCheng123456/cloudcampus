package com.rh.cloudcampus.edu.util.jwt;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthJwt {
    String[] value() default {};

   // String Authorization();

    String[] roles() default {};
}
