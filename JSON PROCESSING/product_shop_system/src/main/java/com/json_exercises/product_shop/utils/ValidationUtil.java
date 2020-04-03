package com.json_exercises.product_shop.utils;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationUtil  {
    <T> boolean isValid(T entity);

    <T> Set<ConstraintViolation<T>> getViolation(T entity);
}
