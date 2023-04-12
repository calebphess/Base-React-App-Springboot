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

}
