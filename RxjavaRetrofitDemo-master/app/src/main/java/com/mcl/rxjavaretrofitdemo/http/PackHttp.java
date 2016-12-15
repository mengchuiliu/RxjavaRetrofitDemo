package com.mcl.rxjavaretrofitdemo.http;

import android.util.Log;

import com.mcl.rxjavaretrofitdemo.entity.HttpResult;
import com.mcl.rxjavaretrofitdemo.entity.Subject;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Chuiliu Meng on 2016/12/15.
 * 封装数据返回统一数据源，然后根据数据源对象进行处理
 */

public class PackHttp extends HttpMethods {
    private PackHttp() {
        super();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final PackHttp INSTANCE1 = new PackHttp();
    }

    //获取单例
    public static PackHttp getInstance() {
        return SingletonHolder.INSTANCE1;
    }


    /**
     * 用于获取豆瓣电影Top250的数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getTopMovie(Subscriber subscriber, int start, int count) {
        Observable observable = movieService.getTopMovie(start, count)
                .map(new HttpResultFunc<List<Subject>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCount() == 0) {
                throw new ApiException(100);
            }
            return httpResult.getSubjects();
        }
    }
}
