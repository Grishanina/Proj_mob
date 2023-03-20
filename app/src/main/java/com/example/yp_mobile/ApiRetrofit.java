package com.example.yp_mobile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRetrofit {
    @POST("user/login")
    Call<MaskaUser> createUser(@Body UserModel userModel);
}
