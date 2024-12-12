package com.app.rkvmoneyrecharge.retrofit;

import com.app.rkvmoneyrecharge.base.BaseActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientRkv extends BaseActivity {
    public static Retrofit retrofit;
    public static final String BASE_URL = "https://rkvplan.in/";
    public static ApiInterfacesNew getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(false)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Response response =  chain.proceed(original);
//                            if (response.code() == 401){
//                                Utility.userLogout(context) ;
//                            }
                            return response;
                        }
                    })
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterfacesNew.class) ;
    }
}


