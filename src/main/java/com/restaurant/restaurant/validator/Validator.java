package com.restaurant.restaurant.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public interface Validator<T> {

    boolean validate(T entity);

    default  boolean validateString(String string, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
