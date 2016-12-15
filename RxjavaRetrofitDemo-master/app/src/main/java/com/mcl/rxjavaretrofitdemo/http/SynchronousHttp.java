package com.mcl.rxjavaretrofitdemo.http;

import android.util.Log;

import com.mcl.rxjavaretrofitdemo.entity.HttpResult;
import com.mcl.rxjavaretrofitdemo.entity.Subject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Chuiliu Meng on 2016/12/15.
 * 同步处理http请求
 * 判断请求码。根据请求码直接进行相关操作
 */

public class SynchronousHttp extends HttpMethods {
    private SynchronousHttp() {
        super();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final SynchronousHttp INSTANCE = new SynchronousHttp();
    }

    //获取单例
    public static SynchronousHttp getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getTopMovieSynchronous(Subscriber subscriber, final int start, final int count) {
        Observable observable = Observable.create(new Observable.OnSubscribe<HttpResult<List<Subject>>>() {
            @Override
            public void call(Subscriber<? super HttpResult<List<Subject>>> subscriber) {
                Call<HttpResult<List<Subject>>> responeCall = movieService.getTopMovieSynchronous(start, count);
                try {
                    Response<HttpResult<List<Subject>>> response = responeCall.execute();
                    Log.e("my_log", "===111111====>" + response.code());
                    if (response.code() == 200) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    } else {
                        String string = response.errorBody().string();
                        //ErrorBean accountRespone = JSON.parseObject(string, ErrorBean.class); 使用fastjson将返回的信息对象化
                        subscriber.onError(new ApiException("错误"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
        toSubscribe(observable, subscriber);
    }
}
