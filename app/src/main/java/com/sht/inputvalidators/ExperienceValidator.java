package com.sht.inputvalidators;

import com.example.fieldbuzzassignment.R;

public class ExperienceValidator implements IInputValidator{

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
            int experience = Integer.parseInt(input);
            if(experience < 0 | experience > 100){
                errorMsgResID = R.string.experience_error_msg;
                return false;
            }
        } catch (NumberFormatException e){
            // experience is not an integer
            errorMsgResID = R.string.experience_error_msg;
            return false;
        }
        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }
}
