package pl.learning.spring.model.user.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	private String firstFieldName;
	private String secondFieldName;
	private String message;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object object, final ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			Object firstObj = BeanUtils.getProperty(object, firstFieldName);
			Object secondObj = BeanUtils.getProperty(object, secondFieldName);
			valid = firstObj != null && firstObj.equals(secondObj);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			valid = false;
		}

		if (!valid) {
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(firstFieldName)
					.addConstraintViolation().disableDefaultConstraintViolation();
		}

		return valid;
	}
}