package com.mcl.rxjavaretrofitdemo.subscribers;

/**
 * Created by Chuiliu Meng on 2016/12/15.
 * 贡献数据接口
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
