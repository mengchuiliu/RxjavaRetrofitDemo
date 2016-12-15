package com.mcl.rxjavaretrofitdemo.http;

import com.mcl.rxjavaretrofitdemo.entity.HttpResult;
import com.mcl.rxjavaretrofitdemo.entity.Subject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Chuiliu Meng on 2016/12/15.
 * 接口类
 */
public interface MovieService {

    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Call<HttpResult<List<Subject>>> getTopMovieSynchronous(@Query("start") int start, @Query("count") int count);

//    @POST
//    @PATCH
//    @DELETE
}
