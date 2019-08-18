package com.loction.xokhttp;


import android.util.Log;

import javax.xml.transform.sax.SAXTransformerFactory;

/**
 * @author tianxiaolong
 *         time：2019/8/15 21:33
 *         description：
 */

public class Logs {

	private static final String TAG = "X_OKHTTP_LOG";



	public static final int LOG_INFO = 1 << 1;

	public static final int LOG_DEBUG = 1 << 2;

	public static final int LOG_ERROR = 1 << 3;

	public static final int LOG_ALL = LOG_DEBUG | LOG_INFO | LOG_ERROR;

	public static void I(String msg){
		if(DEBUG(LOG_INFO)) Log.i(TAG,msg);
	}


	private static boolean DEBUG(int leve){
		return (Config.DEBUG_LEVE & leve) == leve;
	}
	public static void D(String msg){
		if(DEBUG(LOG_DEBUG)) Log.d(TAG,msg);
	}

	public static void E(String msg){
		if(DEBUG(LOG_ERROR)) Log.e(TAG,msg);
	}

}
