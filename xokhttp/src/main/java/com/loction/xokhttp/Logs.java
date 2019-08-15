package com.loction.xokhttp;


import android.util.Log;

/**
 * @author tianxiaolong
 *         time：2019/8/15 21:33
 *         description：
 */

public class Logs {
	private static final String TAG = "XOkhttp";


	/**
	 * TODO open log set  variety of state  DEBUG INFO ERROR
	 *
	 */
	public static boolean DEBUG = true;

	public static void I(String msg){
		if(DEBUG) Log.i(TAG,msg);
	}

	public static void D(String msg){
		if(DEBUG) Log.d(TAG,msg);
	}
}
