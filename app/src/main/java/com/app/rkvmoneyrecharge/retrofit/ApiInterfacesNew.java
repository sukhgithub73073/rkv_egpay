package com.app.rkvmoneyrecharge.retrofit;

import com.app.rkvmoneyrecharge.models.CommonResponseModel ;
import com.app.rkvmoneyrecharge.models.GetOperatorModel;
import com.app.rkvmoneyrecharge.models.check_plan_model.CheckPlanModel;
import com.app.rkvmoneyrecharge.models.dth_info_model.DthInfoModel;
import com.app.rkvmoneyrecharge.models.post_code_model.PostCodeModel;
import com.app.rkvmoneyrecharge.models.roffer_plan_model.RofferPlanModel;
import com.app.rkvmoneyrecharge.models.state_board_model.StateBoardModel;
import com.app.rkvmoneyrecharge.models.state_model.StateModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterfacesNew {



    @Headers("Content-Type: application/json")
    @POST("api/login")
    Call<CommonResponseModel> Login(@Body Map<String, String> params);

    @Headers("Content-Type: application/json")
    @POST("api/SignUp")
    Call<CommonResponseModel> getCompanyInfo(@Body Map<String, String> params);

    @Headers("Content-Type: application/json")
    @POST("api/ForgPassword")
    Call<CommonResponseModel> ForgPassword(@Body Map<String, String> params);

    @Headers("Content-Type: application/json")
    @POST("api/SignUpSave")
    Call<CommonResponseModel> registerRequest(@Body Map<String, String> params);


    @Headers("Content-Type: application/json")
    @POST("api/AddReferenceUser")
    Call<CommonResponseModel> retailerRegisterRequest(@Body Map<String, String> params);


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET()
    Call<GetOperatorModel> getOperatorFromMobile (@Url String url);

    @Headers("Content-Type: application/json")
    @POST()
    Call<CommonResponseModel> commonPostRequest(@Url String url, @Body Map<String, String> params);

    @Headers("Content-Type: application/json")
    @GET()
    Call<CommonResponseModel> commonGetRequest(@Url String url);

    @Headers("Content-Type: application/json")
    @GET()
    Call<CheckPlanModel> checkPlan(@Url String url);

    @Headers("Content-Type: application/json")
    @GET()
    Call<RofferPlanModel> planDetails(@Url String url);

    @Headers("Content-Type: application/json")
    @GET()
    Call<DthInfoModel> dthCustomerInfo(@Url String url);


    @Headers("Content-Type: application/json")
    @GET()
    Call<StateModel> stateName(@Url String url);


    @Headers("Content-Type: application/json")
    @GET()
    Call<StateBoardModel> boardDetails(@Url String url);






    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET()
    Call<List<PostCodeModel>> getPincodeInfo (@Url String url);

}
