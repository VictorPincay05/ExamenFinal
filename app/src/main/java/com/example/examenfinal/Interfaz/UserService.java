package com.example.examenfinal.Interfaz;

import com.example.examenfinal.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("?results=20")
    Call<UserResponse> getUsers();
}
