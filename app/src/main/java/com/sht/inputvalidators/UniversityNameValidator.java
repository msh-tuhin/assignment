package com.sht.inputvalidators;

import com.example.fieldbuzzassignment.R;

public class UniversityNameValidator implements IInputValidator{

    private final boolean isRequired = true;
    private int errorMsgResID;

    /* max length restriction is imposed directly on UI (TextInputEditText)*/
    /* only checking if empty */

    @Override
    public boolean isValid(String input) {
        input = input.trim();
        if (isRequired && input.isEmpty()){
            errorMsgResID = R.string.field_required_error_msg;
            return false;
        }
        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }

}
