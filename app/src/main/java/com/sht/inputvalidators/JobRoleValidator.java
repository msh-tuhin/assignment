package com.sht.inputvalidators;

public class JobRoleValidator implements IInputValidator{

    private final boolean isRequired = true;
    private int errorMsgResID;

    /* may be used in future */
    /* input field is a choice field with two options */
    /* option 1 is selected by default */
    /* does not need any validation */

    @Override
    public boolean isValid(String input) {
        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }
}
