package com.restaurant.restaurant.validator;

import com.restaurant.restaurant.dto.RegistrationRequestDto;
import com.restaurant.restaurant.entity.Role;


public class UserValidator implements Validator<RegistrationRequestDto> {

    private String NAME_REGEX = "[а-яА-ЯёЁa-zA-Z '-]+$";
    private String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?!.*\\s).*$";
    private String EMAIL_REGEX = "([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)";
    private String PHONE_REGEX = "^[+]{1}[0-9]{1}[(]{0,1}[0-9]{3}[)]{0,1}[-\\s\\./0-9]{7}$" ;
    private String ADDRESS_REGEX = "^[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0-9]*";

    @Override
    public boolean validate(RegistrationRequestDto entity) {

        return validateString(entity.getEmail(),EMAIL_REGEX) && validateString(entity.getPassword(),PASSWORD_REGEX) &&
                validateString(entity.getName(),NAME_REGEX) && validateString(entity.getLastname(),NAME_REGEX) &&
                validateString(entity.getAddress(),ADDRESS_REGEX) && validateString(entity.getPhonenumber(),PHONE_REGEX) &&
                (entity.getRole().equals(Role.USER.name()) || entity.getRole().equals(Role.ADMIN.name()));
    }
}
