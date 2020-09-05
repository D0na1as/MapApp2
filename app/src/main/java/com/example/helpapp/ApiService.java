package com.example.helpapp;

import com.example.helpapp.Data.Company;
import com.example.helpapp.Data.Statistic;
import com.example.helpapp.Objects.CompanyNodes;
import com.example.helpapp.Objects.Node;
import com.example.helpapp.Objects.Recipient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/getNodes")
    Call<List<Recipient>> getNodes();

    @GET("/getStatistic")
    Call<Statistic> getStatistic();

    @GET("/getCompanies")
    Call<List<Company>> getCompanies();

    @FormUrlEncoded
    @POST("/getLocation")
    Call<Recipient> getLocation(@Field("street") String email, @Field("house_nr") int house_nr, @Field("city") String city);

    @FormUrlEncoded
    @POST("/checkEmail")
    Call<Recipient> checkEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("/reset")
    Call<ResponseBody> reset(@Field("email") String email);

    @FormUrlEncoded
    @POST("/delete")
    Call<ResponseBody> delete(@Field("id") int id);

    @FormUrlEncoded
    @POST("/login")
    Call<Recipient> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/registerRecipient")
    Call<ResponseBody> registerRecipient(@Field("email") String email,
                                         @Field("password") String password,
                                         @Field("company_name") String company_name,
                                         @Field("ac_person") String ac_person,
                                         @Field("phone") String phone,
                                         @Field("street") String street,
                                         @Field("house_nr") int house_nr,
                                         @Field("city") String city,
                                         @Field("longitude") double longitude,
                                         @Field("latitude") double latitude);

    @FormUrlEncoded
    @POST("/updateRecipient")
    Call<Recipient> updateRecipient(@Field("id") int id,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("company_name") String company_name,
                                       @Field("ac_person") String ac_person,
                                       @Field("phone") String phone,
                                       @Field("caps") int caps,
                                       @Field("gloves") int gloves,
                                       @Field("goggles") int goggles,
                                       @Field("masks") int masks,
                                       @Field("shoe_covers") int shoe_covers,
                                       @Field("suits") int suits,
                                       @Field("priority") int priority);


}
