package com.example.fieldbuzzassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.sht.apimodels.APIInterface;
import com.sht.apimodels.CvFile;
import com.sht.apimodels.ErrorBody;
import com.sht.apimodels.FileUploadResponse;
import com.sht.apimodels.Payload;
import com.sht.apimodels.PayloadResponse;
import com.sht.apimodels.TokenResponse;
import com.sht.inputvalidators.AddressValidator;
import com.sht.inputvalidators.CGPAValidator;
import com.sht.inputvalidators.CurrentWorkValidator;
import com.sht.inputvalidators.EmailValidator;
import com.sht.inputvalidators.ExpectedSalaryValidator;
import com.sht.inputvalidators.ExperienceValidator;
import com.sht.inputvalidators.FieldBuzzRefValidator;
import com.sht.inputvalidators.GithubURLValidator;
import com.sht.inputvalidators.GraduationYearValidator;
import com.sht.inputvalidators.IInputValidator;
import com.sht.inputvalidators.NameValidator;
import com.sht.inputvalidators.PhoneValidator;
import com.sht.inputvalidators.UniversityNameValidator;
import com.sht.utils.RealPathUtil;
import com.sht.utils.UniqueID;
import com.sht.utils.UniqueIDTypes;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataInputActivity extends AppCompatActivity {

    final int READ_EXTERNAL_STORAGE_PERM_CODE = 1;
    final int CV_CHOOSE_REQUEST_CODE = 2;

    String token;

    String mFilePath;
    boolean isCVSelected = false;

    ScrollView formScrollView;
    TextInputLayout nameTextInputLayout;
    TextInputEditText nameTextInputEditText;
    TextInputLayout emailTextInputLayout;
    TextInputEditText emailTextInputEditText;
    TextInputLayout phoneTextInputLayout;
    TextInputEditText phoneTextInputEditText;
    TextInputLayout addressTextInputLayout;
    TextInputEditText addressTextInputEditText;
    TextInputLayout universityNameTextInputLayout;
    TextInputEditText universityNameTextInputEditText;
    TextInputLayout graduationYearTextInputLayout;
    TextInputEditText graduationYearTextInputEditText;
    TextInputLayout cgpaTextInputLayout;
    TextInputEditText cgpaTextInputEditText;
    TextInputLayout experienceTextInputLayout;
    TextInputEditText experienceTextInputEditText;
    TextInputLayout currentWorkTextInputLayout;
    TextInputEditText currentWorkTextInputEditText;
    Spinner jobPositionSpinner;
    TextInputLayout expectedSalaryTextInputLayout;
    TextInputEditText expectedSalaryTextInputEditText;
    TextInputLayout fbRefTextInputLayout;
    TextInputEditText fbRefTextInputEditText;
    TextInputLayout githubURLTextInputLayout;
    TextInputEditText githubURLTextInputEditText;
    LinearLayout progressLinearLayout;
    TextView progressTextView;
    TextView cvFileNameTV;
    ImageButton removeCVFileIB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);

        token = getIntent().getStringExtra(IntentExtraKeys.AUTH_TOKEN);
        // sendPayload(token);

        formScrollView = findViewById(R.id.form_scrollview);
        nameTextInputLayout = findViewById(R.id.name_input_layout);
        nameTextInputEditText = findViewById(R.id.name_editText);
        emailTextInputLayout = findViewById(R.id.email_input_layout);
        emailTextInputEditText = findViewById(R.id.email_editText);
        phoneTextInputLayout = findViewById(R.id.phone_input_layout);
        phoneTextInputEditText = findViewById(R.id.phone_editText);
        addressTextInputLayout = findViewById(R.id.address_input_layout);
        addressTextInputEditText = findViewById(R.id.address_editText);
        universityNameTextInputLayout = findViewById(R.id.university_input_layout);
        universityNameTextInputEditText = findViewById(R.id.university_editText);
        graduationYearTextInputLayout = findViewById(R.id.graduation_year_input_layout);
        graduationYearTextInputEditText = findViewById(R.id.graduation_year_editText);
        cgpaTextInputLayout = findViewById(R.id.cgpa_input_layout);
        cgpaTextInputEditText = findViewById(R.id.cgpa_editText);
        experienceTextInputLayout = findViewById(R.id.experience_input_layout);
        experienceTextInputEditText = findViewById(R.id.experience_editText);
        currentWorkTextInputLayout = findViewById(R.id.current_work_place_input_layout);
        currentWorkTextInputEditText = findViewById(R.id.current_work_place_input_editText);
        jobPositionSpinner = findViewById(R.id.job_position_spinner);
        expectedSalaryTextInputLayout = findViewById(R.id.expected_salary_input_layout);
        expectedSalaryTextInputEditText = findViewById(R.id.expected_salary_input_editText);
        fbRefTextInputLayout = findViewById(R.id.fieldbuzz_reference_input_layout);
        fbRefTextInputEditText = findViewById(R.id.fieldbuzz_reference_input_editText);
        githubURLTextInputLayout = findViewById(R.id.github_url_input_layout);
        githubURLTextInputEditText = findViewById(R.id.github_url_input_editText);
        progressLinearLayout = findViewById(R.id.progress_linear_layout);
        progressTextView = findViewById(R.id.progress_textView);
        cvFileNameTV = findViewById(R.id.cv_file_nameTV);
        removeCVFileIB = findViewById(R.id.remove_file_imageButton);

        bindSpinner(jobPositionSpinner, R.array.job_roles);
        Log.i("selection", jobPositionSpinner.getSelectedItem().toString());
        attachTextChangedListeners();
        attachFocusChangedListeners();
    }

    public void viewClicked(View view) {
        if(view.getId() == R.id.submit_button){
            boolean areInputsValid = validateInputs();
            if(areInputsValid){
                showProgressLayout(R.string.data_upload_progress_text);
                sendPayload(token);
            }
        } else if(view.getId() == R.id.cv_file_nameTV){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                // Permission not available
                // ask for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_PERM_CODE);
            } else{
                // permission available
                startCVChooseActivity();
            }
        } else if(view.getId() == R.id.remove_file_imageButton){
            removeCVNameAndImageButton();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == READ_EXTERNAL_STORAGE_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // permission granted
                startCVChooseActivity();

            } else{
                // permission denied

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CV_CHOOSE_REQUEST_CODE){
                Uri contentUri = getCVFileUri(data);
                Log.i("URI", contentUri.toString());
                mFilePath = getPathFromContentUri(contentUri);
                if (mFilePath != null) {
                    if(isFileSizeValid(mFilePath)){
                        isCVSelected = true;
                        showCVNameAndImageButton(mFilePath);
                    } else {
                        Toast.makeText(this, R.string.cv_file_exceed_size, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.cv_selection_error_msg, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void bindSpinner(Spinner spinner, int stringArrayResId){
        String[] jobRoles = getResources().getStringArray(stringArrayResId);
        ArrayAdapter<String> jobRolesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, jobRoles);

        spinner.setAdapter(jobRolesAdapter);
    }

    private void attachTextChangedListeners(){

        /* attach textChangedListeners to all TextInputEditTexts
         * except those which are not required and does not need
         * further validation if provided */

        attachTextChangedListener(nameTextInputEditText, nameTextInputLayout, new NameValidator());
        attachTextChangedListener(emailTextInputEditText, emailTextInputLayout, new EmailValidator());
        attachTextChangedListener(phoneTextInputEditText, phoneTextInputLayout, new PhoneValidator());
        attachTextChangedListener(universityNameTextInputEditText, universityNameTextInputLayout, new UniversityNameValidator());
        attachTextChangedListener(graduationYearTextInputEditText, graduationYearTextInputLayout, new GraduationYearValidator());
        attachTextChangedListener(cgpaTextInputEditText, cgpaTextInputLayout, new CGPAValidator());
        attachTextChangedListener(experienceTextInputEditText, experienceTextInputLayout, new ExperienceValidator());
        attachTextChangedListener(expectedSalaryTextInputEditText, expectedSalaryTextInputLayout, new ExpectedSalaryValidator());
        attachTextChangedListener(githubURLTextInputEditText, githubURLTextInputLayout, new GithubURLValidator());
    }

    private void attachTextChangedListener(TextInputEditText textInputEditText,
                                           TextInputLayout textInputLayout,
                                           IInputValidator inputValidator){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                validateField(input, textInputLayout, inputValidator);
            }
        });
    }

    private void attachFocusChangedListeners(){

        /* call attachFocusChangedListener for required fields */

        attachFocusChangedListener(nameTextInputEditText, nameTextInputLayout);
        attachFocusChangedListener(emailTextInputEditText, emailTextInputLayout);
        attachFocusChangedListener(phoneTextInputEditText, phoneTextInputLayout);
        attachFocusChangedListener(universityNameTextInputEditText, universityNameTextInputLayout);
        attachFocusChangedListener(graduationYearTextInputEditText, graduationYearTextInputLayout);
        attachFocusChangedListener(expectedSalaryTextInputEditText, expectedSalaryTextInputLayout);
        attachFocusChangedListener(githubURLTextInputEditText, githubURLTextInputLayout);
    }

    private void attachFocusChangedListener(TextInputEditText textInputEditText,
                                            TextInputLayout textInputLayout){

        /* called for required fields only
         * for the case when TextInputEditText gets focus,
         * no changes are made and loses focus*/

        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String inputText = ((TextInputEditText) v).getText().toString().trim();
                if(!hasFocus && inputText.isEmpty()){
                    /* loses focus and TextInputEditText is empty */
                    textInputLayout.setError(getString(R.string.field_required_error_msg));
                }
            }
        });
    }

    private Uri getCVFileUri(Intent data){
        Uri contentUri = null;
        if (data != null){
            Uri uri = data.getData();
            if (uri != null){
                Log.i("cv_source", "data");
                contentUri = uri;
                Log.i("Content-Uri", contentUri.toString());
            } else if (data.getClipData() != null){
                Log.i("cv_source", "clipdata");
                ClipData clipData = data.getClipData();
                contentUri = clipData.getItemAt(0).getUri();
                Log.i("Content-Uri", contentUri.toString());
            }
        }
        return contentUri;
    }

    private String getPathFromContentUri(Uri contentUri){
        if(contentUri == null) return null;
        String path = RealPathUtil.getRealPath(this, contentUri);
        return path;
    }

    /* checks if file size is <= 4 MB*/
    private boolean isFileSizeValid(String filePath){
        // file size must not be more than 4 MB
        int maxSize = 4 * 1024 * 1024;
        File file = new File(filePath);
        return file.length() <= maxSize;
    }

    private void showCVNameAndImageButton(String filePath){
        File file = new File(filePath);
        cvFileNameTV.setText(file.getName());
        cvFileNameTV.setClickable(false);
        cvFileNameTV.setFocusable(false);
        removeCVFileIB.setVisibility(View.VISIBLE);
        removeCVFileIB.setClickable(true);
        removeCVFileIB.setFocusable(true);
    }

    private void removeCVNameAndImageButton(){
        cvFileNameTV.setText(R.string.ask_cv_file);
        cvFileNameTV.setClickable(true);
        cvFileNameTV.setFocusable(true);
        removeCVFileIB.setVisibility(View.GONE);
        removeCVFileIB.setClickable(false);
        removeCVFileIB.setFocusable(false);
        isCVSelected = false;
    }

    /* get the time when the user data was first uploaded to FieldBuzz server
    *  in case it's the first time return current timestamp in milliseconds
    *  and save it to the shared preferences*/
    private Long getCreationTime(){
        SharedPreferences sharedPreferences = getSharedPreferences(this.getString(R.string.creation_time_sp),
                Context.MODE_PRIVATE);
        long creationTime = sharedPreferences.getLong("creation_time", -1L);
        if(creationTime == -1L){
            long time = System.currentTimeMillis();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("creation_time", time);
            editor.apply();
            return time;
        }
        return creationTime;
    }

    private Payload getPayload(){
        Payload payload = new Payload();

        UniqueID uniqueID = new UniqueID(this, UniqueIDTypes.PERSON);
        payload.setTsyncId(uniqueID.getUniqueID());

        payload.setName(nameTextInputEditText.getText().toString());
        payload.setEmail(emailTextInputEditText.getText().toString());
        payload.setPhone(phoneTextInputEditText.getText().toString());
        payload.setFullAddress(addressTextInputEditText.getText().toString());
        payload.setNameOfUniversity(universityNameTextInputEditText.getText().toString());
        payload.setGraduationYear(graduationYearTextInputEditText.getText().toString());
        payload.setCgpa(cgpaTextInputEditText.getText().toString());
        payload.setExperienceInMonths(experienceTextInputEditText.getText().toString());
        payload.setCurrentWorkPlaceName(currentWorkTextInputEditText.getText().toString());
        payload.setApplyingIn(jobPositionSpinner.getSelectedItem().toString());
        payload.setExpectedSalary(Integer.parseInt(expectedSalaryTextInputEditText.getText().toString()));
        payload.setFieldBuzzReference(fbRefTextInputEditText.getText().toString());
        payload.setGithubProjectUrl(githubURLTextInputEditText.getText().toString());

        CvFile cv_file = new CvFile();
        uniqueID.setType(UniqueIDTypes.CV_FILE);
        cv_file.setTsyncId(uniqueID.getUniqueID());

        payload.setCvFile(cv_file);
        payload.setOnSpotCreationTime(getCreationTime());
        payload.setOnSpotUpdateTime();
        return payload;
    }

    private void sendPayload(String token){
        // Payload payload = getDummyPayload();
        Payload payload = getPayload();

        Call<PayloadResponse> call = ApiConfig.getApiInstance().sendPayload("Token " + token, payload);
        call.enqueue(new Callback<PayloadResponse>() {
            @Override
            public void onResponse(Call<PayloadResponse> call, Response<PayloadResponse> response) {
                Log.i("response_code", Integer.toString(response.code()));
                Log.i("response_message", response.raw().toString());
                PayloadResponse payloadResponse = response.body();
                if(payloadResponse != null){
                    try{
                        Log.i("message", payloadResponse.getMessage());
                        Log.i("payload_name", payloadResponse.getName());
                        Log.i("payload_email", payloadResponse.getEmail());
                        Log.i("payload_phone", payloadResponse.getPhone());
                        Log.i("payload_address", payloadResponse.getFullAddress());
                        Log.i("payload_uni", payloadResponse.getNameOfUniversity());
                        Log.i("payload_grad_year", Integer.toString(payloadResponse.getGraduationYear()));
                        Log.i("payload_cgpa", Float.toString(payloadResponse.getCgpa()));
                        Log.i("payload_experience", Integer.toString(payloadResponse.getExperienceInMonths()));
                        Log.i("payload_workplace", payloadResponse.getCurrentWorkPlaceName());
                        Log.i("payload_position", payloadResponse.getApplyingIn());
                        Log.i("payload_salary", Integer.toString(payloadResponse.getExpectedSalary()));
                        Log.i("payload_fb_ref", payloadResponse.getFieldBuzzReference());
                        Log.i("payload_github", payloadResponse.getGithubProjectUrl());
                        Log.i("file_id", Integer.toString(payloadResponse.getCvFile().getId()));
                        Log.i("payload_update_time", Long.toString(payloadResponse.getOnSpotUpdateTime()));
                        Log.i("payload_creation_time", Long.toString(payloadResponse.getOnSpotCreationTime()));
                        Log.i("payload_tsync", payloadResponse.getTsyncId());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    uploadCV(Integer.toString(payloadResponse.getCvFile().getId()), token);
                } else{
                    Toast.makeText(DataInputActivity.this, R.string.data_upload_failed, Toast.LENGTH_SHORT)
                            .show();
                    Gson gson = new Gson();
                    try{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().string(), ErrorBody.class);
                        Log.i("data_upload_problem", errorBody.getMessage());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PayloadResponse> call, Throwable t) {
                Toast.makeText(DataInputActivity.this, R.string.data_upload_failed, Toast.LENGTH_SHORT)
                        .show();
                Log.e("reason2", t.getMessage());
            }
        });
    }

    private void uploadCV(String fileId, String token){
        if(mFilePath != null){
            showProgressLayout(R.string.file_upload_progress_text);
            File file = new File(mFilePath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // this works too
            // RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file",
                    file.getName(), requestBody);
            Call<FileUploadResponse> call = ApiConfig.getApiInstance().uploadCV(fileId, "Token " + token, multipartBody);
            call.enqueue(new Callback<FileUploadResponse>() {
                @Override
                public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                    hideProgressLayout();
                    FileUploadResponse fileUploadResponse = response.body();
                    if(fileUploadResponse != null){
                        Toast.makeText(DataInputActivity.this, R.string.file_upload_success, Toast.LENGTH_SHORT)
                                .show();
                        Log.i("file_upload_response", fileUploadResponse.getMessage());
                        Log.i("file_upload_tsync", fileUploadResponse.getTsyncId());
                        Log.i("file_upload_file", fileUploadResponse.getFile());
                        Log.i("file_upload_ct", Long.toString(fileUploadResponse.getDateCreated()));
                        Log.i("file_upload_ut", Long.toString(fileUploadResponse.getLastUpdated()));
                    } else{
                        Toast.makeText(DataInputActivity.this, R.string.file_upload_failed, Toast.LENGTH_SHORT)
                                .show();
                        Gson gson = new Gson();
                        try {
                            ErrorBody errorBody = gson.fromJson(response.errorBody().string(), ErrorBody.class);
                            Log.i("file_upload_problem", errorBody.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                    hideProgressLayout();
                    Toast.makeText(DataInputActivity.this, R.string.file_upload_failed, Toast.LENGTH_SHORT)
                            .show();
                    Log.i("file_upload_fail_reason", t.getMessage());
                    Log.i("file_upload", "failed");
                }
            });
        }

    }

    private boolean validateInputs(){
        boolean bool = true;

        /* there are some redundant calls in here */
        bool = bool & validateField(nameTextInputEditText.getText().toString(), nameTextInputLayout,
                new NameValidator());
        bool = bool & validateField(emailTextInputEditText.getText().toString(), emailTextInputLayout,
                new EmailValidator());
        bool = bool & validateField(phoneTextInputEditText.getText().toString(), phoneTextInputLayout,
                new PhoneValidator());
        bool = bool & validateField(addressTextInputEditText.getText().toString(), addressTextInputLayout,
                new AddressValidator());
        bool = bool & validateField(universityNameTextInputEditText.getText().toString(), universityNameTextInputLayout,
                new UniversityNameValidator());
        bool = bool & validateField(graduationYearTextInputEditText.getText().toString(), graduationYearTextInputLayout,
                new GraduationYearValidator());
        bool = bool & validateField(cgpaTextInputEditText.getText().toString(), cgpaTextInputLayout,
                new CGPAValidator());
        bool = bool & validateField(experienceTextInputEditText.getText().toString(), experienceTextInputLayout,
                new ExperienceValidator());
        bool = bool & validateField(currentWorkTextInputEditText.getText().toString(), currentWorkTextInputLayout,
                new CurrentWorkValidator());
        bool = bool & validateField(fbRefTextInputEditText.getText().toString(), fbRefTextInputLayout,
                new FieldBuzzRefValidator());
        bool = bool & validateField(expectedSalaryTextInputEditText.getText().toString(), expectedSalaryTextInputLayout,
                new ExpectedSalaryValidator());
        bool = bool & validateField(githubURLTextInputEditText.getText().toString(), githubURLTextInputLayout,
                new GithubURLValidator());
        bool = bool & verifyCVFileSelected();
        return bool;
    }

    private boolean validateField(String input, TextInputLayout textInputLayout, IInputValidator inputValidator){
        boolean isValid = inputValidator.isValid(input);
        if(isValid){
            textInputLayout.setError(null);
            return true;
        }else{
            textInputLayout.setError(getString(inputValidator.getErrorMsgResID()));
            return false;
        }
    }

    private boolean verifyCVFileSelected(){
        if(!isCVSelected){
            Toast.makeText(this, R.string.cv_missing_error_msg, Toast.LENGTH_LONG).show();
        }
        return isCVSelected;
    }

    private void startCVChooseActivity(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setType("application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(Intent.createChooser(intent, "Select CV"), CV_CHOOSE_REQUEST_CODE);
        }
    }

    private void showProgressLayout(int resID){
        formScrollView.setVisibility(View.GONE);
        progressTextView.setText(resID);
        progressLinearLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgressLayout(){
        progressLinearLayout.setVisibility(View.GONE);
        formScrollView.setVisibility(View.VISIBLE);
    }

}