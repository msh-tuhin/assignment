package com.sht.inputvalidators;

import com.example.fieldbuzzassignment.R;

public class GraduationYearValidator implements IInputValidator {

    private final boolean isRequired = true;
    private int errorMsgResID;

    /* max length restriction is imposed directly on UI (TextInputEditText)*/
    /* checking if empty */
    /* checking for valid data type and range*/

    @Override
    public boolean isValid(String input) {
        input = input.trim();

        /* checking if  empty */
        if (isRequired && input.isEmpty()){
            errorMsgResID = R.string.field_required_error_msg;
            return false;
        }

        /* checking for valid data type and range*/
        try{
            int gradYear = Integer.parseInt(input);
            if(gradYear < 2015 | gradYear > 2020){
                errorMsgResID = R.string.invalid_grad_year_error_msg;
                return false;
            }
        } catch (NumberFormatException e){
            // graduation year is not an integer
            errorMsgResID = R.string.invalid_grad_year_error_msg;
            return false;
        }

        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }
}
