package com.sht.inputvalidators;

public class CurrentWorkValidator implements IInputValidator{

    private final boolean isRequired = false;
    private int errorMsgResID;

    /* max length restriction is imposed directly on UI (TextInputEditText)*/
    /* does not need any more validation */

    @Override
    public boolean isValid(String input) {
        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }
}
