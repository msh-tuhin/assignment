package com.sht.apimodels;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("/api/login/")
    Call<TokenResponse> getAuthenticationToken(@Body UserCredentials userCredentials);

    // testing api
    @POST("/api/v0/recruiting-entities/")
    // @POST("/api/v1/recruiting-entities/")
    Call<PayloadResponse> sendPayload(@Header("Authorization") String token, @Body Payload payload);

    @Multipart
    @PUT("/api/file-object/{file_id}/")
    Call<FileUploadResponse> uploadCV(@Path("file_id") String file_id,
                                      @Header("Authorization") String token,
                                      @Part MultipartBody.Part file);
}
