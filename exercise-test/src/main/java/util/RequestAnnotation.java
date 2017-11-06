package util;

/**
 * Created by LiXuan on 2017/11/6.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestAnnotation {
    public String uri() default "/";
}
