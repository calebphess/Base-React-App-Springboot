package com.zetech.thingapp.thingapp.util;

import org.springframework.util.StringUtils;

import com.zetech.thingapp.thingapp.exceptions.ValidationErrors;

public final class StringContentValidator
{
//hidden ctr to force use of static methods
    private StringContentValidator()
    {
        super();
    }

    public static boolean validate(String value, String property, String field, boolean required, int maxLength,
            ValidationErrors errors)
    {
        boolean isEmpty = !StringUtils.hasLength(value);
        if (required && isEmpty)
        {
            errors.addError(property, field + " cannot be empty.");
            return false;
        }
        if (maxLength < value.length())
        {
            errors.addError(property, field + " cannot exceed " + maxLength + " characters.");
            return false;
        }
        // TODO someday support PM and blocking html

        return true;
    }

    public static boolean validateEmail(String email,  String property, String field, boolean required, int maxLength, ValidationErrors errors)
    {
        // TODO: replace this with an email validation api
        // validation by RFC 5322
        String validation_regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        boolean isEmpty = !StringUtils.hasLength(email);

        if (required && isEmpty)
        {
            errors.addError(property, field + " cannot be empty.");
            return false;
        }

        if (!email.matches(validation_regex))
        {
            errors.addError(property, "Email address is invalid.");
            return false;
        }

        if (maxLength < email.length())
        {
            errors.addError(property, field + " cannot exceed " + maxLength + " characters.");
            return false;
        }

        return true;
    }

}
