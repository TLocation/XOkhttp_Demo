package com.loction.xokhttp.callback;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.response.UploadResponse;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/11 15:17
 * description：
 */

public class ProgressRequestBody extends RequestBody {
	private final RequestBody mDelegate;
	private BufferedSink mBufferedSink;
	private UploadResponse listener;

	public ProgressRequestBody(RequestBody delegate, UploadResponse uploadResponse) {
		this.mDelegate = delegate;
		this.listener = uploadResponse;
	}

	@Override
	public MediaType contentType() {
		return mDelegate.contentType();
	}

	@Override
	public long contentLength() {
		try {
			return mDelegate.contentLength();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void writeTo(@NonNull BufferedSink sink) throws IOException {
		if (sink instanceof Buffer) {
			// Log Interceptor
			mDelegate.writeTo(sink);
			return;
		}
		if (mBufferedSink == null) {
			mBufferedSink = Okio.buffer(new CountingSink(sink));
		}
		mDelegate.writeTo(mBufferedSink);
		mBufferedSink.flush();
	}

	protected final class CountingSink extends ForwardingSink {
		private long bytesWritten = 0;
		private long contentLength = 0L;
		private long lastTime = 0L;

		public CountingSink(Sink delegate) {
			super(delegate);
		}

		@Override
		public void write(Buffer source, final long byteCount) throws IOException {
			super.write(source, byteCount);
			if (contentLength == 0) {
				//获得contentLength的值，后续不再调用
				contentLength = contentLength();
			}
			bytesWritten += byteCount;
			long cuTime = SystemClock.elapsedRealtime();
			if ((listener != null && cuTime - lastTime >= 150) || bytesWritten == contentLength) {
				final long current = bytesWritten;
				Log.d("tian", "完成");
				XOkhttpClient.handler
						.post(new Runnable() {
							@Override
							public void run() {
								if (current == contentLength) {
//									Log.d("tian", "完成");
									listener.uploadDone(contentLength);
								} else {
									listener.onProgress(current, contentLength);
//									Log.d("tian", "进度");
								}
							}
						});
			}
			lastTime = cuTime;
		}
	}

}
