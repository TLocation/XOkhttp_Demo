package com.loction.xokhttp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telecom.Call;
import android.util.Log;

import com.loction.xokhttp.builder.BaseRequestBuilder;
import com.loction.xokhttp.builder.GetRequestBuilder;
import com.loction.xokhttp.builder.PostRequestBuilder;
import com.loction.xokhttp.builder.UpdateRequestBuilder;

import okhttp3.OkHttpClient;

/**
 * @author tianxiaolong
 *         time：2018/12/27 18:55
 *         description：
 *
 */

public class LifecycleManager implements LifecycleObserver {
	private Object tag;
	private OkHttpClient client;

	private boolean reStartRequest;

	private int cancelType;

	private okhttp3.Call reStartCall;

	private BaseRequestBuilder requestBuilder;


	public LifecycleManager(OkHttpClient client, FragmentActivity context) {
		if (isRunUiThread()) {
			tag = context;
			context.getLifecycle().addObserver(this);
		} else {
			tag = context.getApplicationContext();
		}
		this.client = client;
	}

	public LifecycleManager(OkHttpClient client, Fragment context) {
		if (isRunUiThread()) {
			tag = context;
			context.getLifecycle().addObserver(this);
		} else {
			tag = context.getContext().getApplicationContext();
		}
		this.client = client;
	}

	public LifecycleManager(OkHttpClient client, Context context) {
		if (isRunUiThread()) {
			tag = context;
		} else{
			tag = context.getApplicationContext();
		}
		this.client = client;
	}

	private void initConfig(){
		// do nothing
	}


	public LifecycleManager reStartRequest(){
		reStartRequest = true;
		return this;
	}

	public LifecycleManager  setCancelTiming(@CancelType.CancelState int state){
		cancelType = state;
		return this;
	}


	private boolean isRunUiThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}


	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	private void onResume(){
		Logs.I("LifecycleManager in onResume");
		if(reStartCall != null && requestBuilder != null){
			Logs.I("re start request http");
			  requestBuilder.requestCall(reStartCall);
		}
	}
	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	private void onPause(){
		Logs.I("LifecycleManager in onPause");
		if(cancelType == CancelType.CANCEL_PAUSE){
			if(reStartRequest){
				reStartCall = XOkhttpClient.getXOkHttp().cancelTagReStart(tag);
			}else {
				XOkhttpClient.getXOkHttp().cancelTag(tag);
			}
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private void onDestroy() {
		Logs.I("LifecycleManager in onDestroy");
		if(cancelType == CancelType.CANCEL_DESTORY){
			XOkhttpClient.getXOkHttp().cancelTag(tag);
		}
	}



	public GetRequestBuilder get() {
		GetRequestBuilder getRequestBuilder = new GetRequestBuilder(client, tag);
		requestBuilder = getRequestBuilder;
		return getRequestBuilder;
	}

	public PostRequestBuilder post() {
		PostRequestBuilder postRequestBuilder = new PostRequestBuilder(client, tag);
		requestBuilder = postRequestBuilder;
		return postRequestBuilder;
	}

	public UpdateRequestBuilder upload() {
		UpdateRequestBuilder updateRequestBuilder = new UpdateRequestBuilder(client, tag);
		requestBuilder = updateRequestBuilder;
		return updateRequestBuilder;
	}
}
