package com.loction.xokhttp;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.loction.xokhttp.builder.GetRequestBuilder;
import com.loction.xokhttp.builder.PostRequestBuilder;
import com.loction.xokhttp.builder.UpdateRequestBuilder;

import okhttp3.OkHttpClient;

/**
 * @author tianxiaolong
 *         time：2018/12/27 18:55
 *         description：
 */

public class LifecycleManager implements LifecycleObserver {
	private Object tag;
	private OkHttpClient client;

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

	private boolean isRunUiThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private void onDestroy() {
		Log.d("TAG", "Ondesss"+"\ncode===>"+hashCode());
		XOkhttpClient.getXOkHttp().cancelTag(tag);
	}

	public GetRequestBuilder get() {
		return new GetRequestBuilder(client, tag);
	}

	public PostRequestBuilder post() {
		return new PostRequestBuilder(client, tag);
	}

	public UpdateRequestBuilder upload() {
		return new UpdateRequestBuilder(client, tag);
	}
}
