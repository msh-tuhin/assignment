package com.sht.inputvalidators;

import com.example.fieldbuzzassignment.R;

public class ExpectedSalaryValidator implements IInputValidator{

    private final boolean isRequired = true;
    private int errorMsgResID;

    @Override
    public boolean isValid(String input) {
        input = input.trim();

        /* checking if  empty */
        if (isRequired && input.isEmpty()){
            errorMsgResID = R.string.field_required_error_msg;
            return false;
        }

        try{
            int expectedSalary = Integer.parseInt(input);
            if(expectedSalary < 15000 | expectedSalary > 60000){
                errorMsgResID = R.string.expected_salary_error_msg;
                return false;
            }
        } catch (NumberFormatException e){
            // expected salary is not an integer
            errorMsgResID = R.string.expected_salary_error_msg;
            return false;
        }
        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }

}
