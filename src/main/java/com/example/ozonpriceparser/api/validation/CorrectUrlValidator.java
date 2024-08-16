package com.example.ozonpriceparser.api.validation;

import com.example.ozonpriceparser.api.validation.annotations.CorrectUrlConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CorrectUrlValidator implements ConstraintValidator<CorrectUrlConstraint, String> {
    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        return url.startsWith("https://www.ozon.ru/product/");
    }
}
