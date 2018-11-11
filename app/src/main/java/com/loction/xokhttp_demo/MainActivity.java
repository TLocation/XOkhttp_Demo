package com.loction.xokhttp_demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.response.*;
import com.loction.xokhttp.response.UploadResponse;
import com.loction.xokhttp.utils.Responseer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

	private XOkhttpClient xOkhttpClient;
	private File imageFile;
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		 * 公共参数
		 */
//        HashMap<String, String> params = new HashMap<>();
//        params.put("username","tianxiaolong");
//        params.put("password","tianxiaolong");

		//                .addParams(params)
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Connection", "close");
		xOkhttpClient = new XOkhttpClient.Builder(ProgressManager.getInstance().with(new OkHttpClient.Builder())
				.build().newBuilder())
//                .addParams(params)
//				.addHeards(headers)
				.setCache(this,1024*10*10,300000)
				.setBaseClass(BaseResponse.class)
				.setLog()
				.builder();
		findViewById(R.id.main_get).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				xOkhttpClient.get()
						.url("http://wanandroid.com/wxarticle/chapters/json")
						.enqueue(new RawResponseHandler() {
							@Override
							public void onSuccful(Responseer<String> responseer) {
								String body = responseer.body();
//                          Toast.makeText(MainActivity.this, body, Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onFail(int errorCode, String errorMessage) {
//                          Toast.makeText(MainActivity.this, "是bai", Toast.LENGTH_SHORT).show();
							}
						});
			}
		});


		xOkhttpClient
				.post()
				.url("http://www.wanandroid.com/user/login")
				.addParam("username", "tianxiaolong")
				.addParam("password", "tianxiaolong")
				.enqueue(new GsonResponseHandler<LoginResponse>() {
					@Override
					public void onSuccful(LoginResponse response) {
						Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
						Log.d("haha", response.toString());
					}
				});


		findViewById(R.id.main_upload)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						openPicture();
					}
				});


	}

	private void uploadFile(File file) {

		xOkhttpClient.upload()
				.addFile("file", file)
				.url("http://hb5.api.okayapi.com/?s=App.CDN.UploadImg&app_key=A7C413FD1FDAC974F64996121A95F417")
//                .addParam("app_key","A7C413FD1FDAC974F64996121A95F417")
				.enqueue(new UploadResponse<com.loction.xokhttp_demo.UploadResponse>() {
					@Override
					public void onProgress(long uploadLength, long contentLength) {
						Log.d("okhttp", "upload--- uploadLength==" + uploadLength + "---contentlength===" + contentLength);
					}

					@Override
					public void uploadDone(long contentLength) {
						Log.d("okhttp", "上传完成===>" + contentLength);
					}

					@Override
					public void onUploadSuccful(com.loction.xokhttp_demo.UploadResponse response) {
						Log.d("testss", "上传成功" + response.getData().getUrl());
					}

					@Override
					public void onFail(int errorCode, String errorMessage) {
						Log.d("okhttp", "上传失败===>" + errorMessage);
					}
				});
	}

	private void openPicture() {
		/**
		 * 从相册选择
		 */
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// 以startActivityForResult的方式启动一个activity用来获取返回的结果
		startActivityForResult(intent, 100);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == RESULT_OK) {
			Uri uri = data.getData();// 获取图片的uri
			Intent intent_gallery_crop = new Intent("com.android.camera.action.CROP");
			intent_gallery_crop.setDataAndType(uri, "image/*");

			// 设置裁剪
			intent_gallery_crop.putExtra("crop", "true");
			intent_gallery_crop.putExtra("scale", true);
			// aspectX aspectY 是宽高的比例
			intent_gallery_crop.putExtra("aspectX", 1);
			intent_gallery_crop.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent_gallery_crop.putExtra("outputX", 400);
			intent_gallery_crop.putExtra("outputY", 400);

			intent_gallery_crop.putExtra("return-data", false);

			// 创建文件保存裁剪的图片
			createImageFile();
			imageUri = Uri.fromFile(imageFile);
			if (imageUri != null) {
				intent_gallery_crop.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				intent_gallery_crop.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			}

			startActivityForResult(intent_gallery_crop, 200);
		}
		if (requestCode == 200 && resultCode == RESULT_OK) {
			uploadFile(imageFile);
		}
	}

	/**
	 * 创建File保存图片
	 */
	private void createImageFile() {

		try {

			if (imageFile != null && imageFile.exists()) {
				imageFile.delete();
			}
			// 新建文件
			imageFile = new File(Environment.getExternalStorageDirectory(),
					System.currentTimeMillis() + "galleryDemo.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
