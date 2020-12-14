package com.sht.inputvalidators;

import android.util.Patterns;

import com.example.fieldbuzzassignment.R;

public class EmailValidator implements IInputValidator{

    private final boolean isRequired = true;
    private int errorMsgResID;

    /* max length restriction is imposed directly on UI (TextInputEditText)*/
    /* checking if empty */
    /* checking for valid email format*/
    @Override
    public boolean isValid(String input) {
        input = input.trim();

        /* checking if empty */
        if (isRequired && input.isEmpty()){
            errorMsgResID = R.string.field_required_error_msg;
            return false;
        }

        /* checking for valid email format*/
        String[] arr = input.split("\\.");

        /* TODO Check apache common validators
         * TODO maybe use a custom regex for email validation */
        /* FieldBuzz api does not accept email top domain of length<2
         * but this matcher recognizes top domain of length 1 as valid  */
        if(!(Patterns.EMAIL_ADDRESS.matcher(input).matches() &&
                arr[arr.length-1].length() > 1)){
            errorMsgResID = R.string.invalid_email_error_msg;
            return false;
        }
        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }
}
