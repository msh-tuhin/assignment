package com.sht.inputvalidators;

public interface IInputValidator {
    boolean isValid(String input);
    int getErrorMsgResID();
}
