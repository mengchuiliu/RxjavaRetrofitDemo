package com.mcl.rxjavaretrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mcl.rxjavaretrofitdemo.entity.HttpResult;
import com.mcl.rxjavaretrofitdemo.entity.Subject;
import com.mcl.rxjavaretrofitdemo.http.PackHttp;
import com.mcl.rxjavaretrofitdemo.http.SynchronousHttp;
import com.mcl.rxjavaretrofitdemo.subscribers.ProgressSubscriber;
import com.mcl.rxjavaretrofitdemo.subscribers.SubscriberOnNextListener;
import com.mcl.rxjavaretrofitdemol.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.click_me_BN)
    Button clickMeBN;
    @Bind(R.id.result_TV)
    TextView resultTV;

    private SubscriberOnNextListener getTopMovieOnNext;
    private SubscriberOnNextListener getTopMovieOnNext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getTopMovieOnNext = new SubscriberOnNextListener<HttpResult<List<Subject>>>() {
            @Override
            public void onNext(HttpResult<List<Subject>> subjects) {
                resultTV.setText("111111--->" + subjects.getSubjects().toString());
            }
        };

        getTopMovieOnNext1 = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                resultTV.setText("222222--->" + subjects.toString());
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick({R.id.click_me_BN, R.id.click_me_BN_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_me_BN:
                getMovie();
                break;
            case R.id.click_me_BN_1:
                getMovie1();
                break;
        }
    }

    //进行网络请求
    private void getMovie() {
        SynchronousHttp.getInstance().getTopMovieSynchronous(new ProgressSubscriber(getTopMovieOnNext, MainActivity.this), 0, 10);
    }

    //进行网络请求
    private void getMovie1() {
        PackHttp.getInstance().getTopMovie(new ProgressSubscriber(getTopMovieOnNext1, MainActivity.this), 0, 10);
    }
}
