package com.sht.inputvalidators;

import android.util.Patterns;

import com.example.fieldbuzzassignment.R;

public class GithubURLValidator implements IInputValidator{

    private final boolean isRequired = true;
    private int errorMsgResID;

    /* max length restriction is imposed directly on UI (TextInputEditText)*/
    /* checking if empty */
    /* checking for valid url format*/

    @Override
    public boolean isValid(String input) {

        /* checking if empty */
        if (isRequired && input.isEmpty()){
            errorMsgResID = R.string.field_required_error_msg;
            return false;
        }

        /* checking for valid url format
        /* TODO Check apache common validators
        /* TODO maybe use a custom regex for url validation */
        /* this matcher does not allow ftp schemes */
        if(!Patterns.WEB_URL.matcher(input.trim()).matches()){
            errorMsgResID = R.string.github_url_error_msg;
            return false;
        }

        return true;
    }

    @Override
    public int getErrorMsgResID() {
        return errorMsgResID;
    }
}
