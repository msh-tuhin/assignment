package com.example.fieldbuzzassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.sht.apimodels.APIInterface;
import com.sht.apimodels.ErrorBody;
import com.sht.apimodels.TokenResponse;
import com.sht.apimodels.UserCredentials;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    LinearLayout inputLinearLayout;
    LinearLayout progressLinearLayout;
    TextInputLayout usernameTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    TextInputEditText usernameTextInputEditText;
    TextInputEditText passwordTextInputEditText;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputLinearLayout = findViewById(R.id.input_linear_layout);
        progressLinearLayout = findViewById(R.id.progress_linear_layout);
        usernameTextInputLayout = findViewById(R.id.username_input_layout);
        passwordTextInputLayout = findViewById(R.id.password_input_layout);
        usernameTextInputEditText = findViewById(R.id.username_editText);
        passwordTextInputEditText = findViewById(R.id.password_editText);
        signInButton = findViewById(R.id.button_signIn);

        usernameTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                usernameTextInputLayout.setError(null);
                signInButton.setEnabled( !( s.toString().isEmpty()
                        | passwordTextInputEditText.getText().toString().isEmpty()));
            }
        });

        passwordTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordTextInputLayout.setError(null);
                signInButton.setEnabled( !(s.toString().isEmpty()
                        | usernameTextInputEditText.getText().toString().isEmpty()));
            }
        });
    }

    public void viewClicked(View view) {
        if(view.getId() == R.id.button_signIn){
            Log.i("clicked", "log in button");
            inputLinearLayout.setVisibility(View.GONE);
            progressLinearLayout.setVisibility(View.VISIBLE);

            Call<TokenResponse> call = ApiConfig.getApiInstance().getAuthenticationToken(getUserCredentials());
            call.enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    TokenResponse tokenResponse = response.body();
                    if(tokenResponse != null){
                        Log.i("TOKEN", tokenResponse.token);
                        Intent intent = new Intent(MainActivity.this, DataInputActivity.class);
                        intent.putExtra(IntentExtraKeys.AUTH_TOKEN, tokenResponse.token);
                        startActivity(intent);
                        MainActivity.this.finish();
                    } else{
                        handleBadRequest(response);
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                    usernameTextInputEditText.setText("");
                    passwordTextInputEditText.setText("");
                    signInButton.setEnabled(false);
                    Log.e("login_fail_reason", t.getMessage());
                }
            });
        }
    }

    private UserCredentials getUserCredentials(){
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.username = usernameTextInputEditText.getText().toString();
        userCredentials.password = passwordTextInputEditText.getText().toString();
        return userCredentials;
    }

    /* for hard-coding user credentials */
    private UserCredentials getUserCredentialsFilled(){
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.username = "username";
        userCredentials.password = "password";
        return userCredentials;
    }

    /* called when the login api call returns a response without a token
    *  there was a problem with the provided user credentials  */
    private void handleBadRequest(Response<TokenResponse> response){
        progressLinearLayout.setVisibility(View.GONE);
        inputLinearLayout.setVisibility(View.VISIBLE);
        try {
            Gson gson = new Gson();
            ErrorBody errorBody = gson.fromJson(response.errorBody().string(), ErrorBody.class);
            Log.i("response", errorBody.getMessage());
            Toast.makeText(MainActivity.this, errorBody.getMessage(), Toast.LENGTH_LONG)
                    .show();
            if(errorBody.getMessage().equals("Username does not exist in the system.")){
                usernameTextInputEditText.setText("");
                passwordTextInputEditText.setText("");
                usernameTextInputLayout.setError(errorBody.getMessage());
            } else if(errorBody.getMessage().equals("Password is incorrect for provided username.")){
                passwordTextInputEditText.setText("");
                passwordTextInputLayout.setError(errorBody.getMessage());
            } else{
                usernameTextInputEditText.setText("");
                passwordTextInputEditText.setText("");
            }
            signInButton.setEnabled(false);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, R.string.username_password_wrong, Toast.LENGTH_LONG)
                    .show();
            usernameTextInputEditText.setText("");
            passwordTextInputEditText.setText("");
            signInButton.setEnabled(false);
            e.printStackTrace();
        }
    }
}