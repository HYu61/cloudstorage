package com.udacity.jwdnd.course1.cloudstorage.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotation is used for validated the password.
 * The length of the password must between @min default is 6 to @max default is 20,
 * and must contain the uppercase, lowercase letters, digits and at least one of the following characters !@#$%^&*;
 * author: Heng Yu
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {


    String regexp() default "";

    String message() default "Password format is invalid!";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
