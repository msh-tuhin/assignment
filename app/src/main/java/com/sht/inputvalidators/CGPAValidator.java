package com.sht.inputvalidators;

import com.example.fieldbuzzassignment.R;

public class CGPAValidator implements IInputValidator {

    private final boolean isRequired = false;
    private int errorMsgResID;

    /* checking for valid data type and range*/

    @Override
    public boolean isValid(String input) {
        input = input.trim();

        if(!isRequired && input.isEmpty()){
            return true;
        }

        /* checking for valid data type and range*/
        try{
            float cgpa = Float.parseFloat(input);
            if(cgpa < 2.0f | cgpa > 4.0f){
                errorMsgResID = R.string.cgpa_error_msg;
                return false;
            }
        } catch (NumberFormatException e){
            // graduation year is not a valid number
            errorMsgResID = R.string.cgpa_error_msg;
            return false;
        }
        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }
}
