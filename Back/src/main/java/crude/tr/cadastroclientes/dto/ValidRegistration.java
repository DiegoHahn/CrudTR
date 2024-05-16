package crude.tr.cadastroclientes.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CPFValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRegistration{
    String message() default "Número de registro inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
