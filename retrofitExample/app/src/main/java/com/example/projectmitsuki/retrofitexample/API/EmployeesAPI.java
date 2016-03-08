package com.example.projectmitsuki.retrofitexample.API;




import com.example.projectmitsuki.retrofitexample.model.Employee;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public interface EmployeesAPI {

    /*Retrofit get annotation with our URL
       And our method that will return us the list ob Employee
    */
    @GET("/user/")
    public void getAllUsers(Callback<List<Employee>> response);

    @GET("/user/{id}/")
    public void getUserInfo(@Path("id") String id, Callback<Employee> response);

/*
    @FormUrlEncoded
    @POST("/user/new_user/")
    public void postUser(@Body(), Callback<Employee> callback);
*/

    @DELETE("/user/{id}/")
    public void deleteUser(@Path("id") String id, Callback<Employee> response);

    @DELETE("/user/")
    public void deleteAllUser(Callback<Employee> response);
}