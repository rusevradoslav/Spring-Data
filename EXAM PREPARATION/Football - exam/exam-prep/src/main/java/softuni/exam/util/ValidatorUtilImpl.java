package softuni.exam.util;


import javax.validation.Validation;
import javax.validation.Validator;


public class ValidatorUtilImpl implements ValidatorUtil {
    private Validator validator;

    public ValidatorUtilImpl() {
        this.validator = Validation
                .buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <T> boolean isValid(T entity) {
        return this.validator.validate(entity).isEmpty();
    }


}
