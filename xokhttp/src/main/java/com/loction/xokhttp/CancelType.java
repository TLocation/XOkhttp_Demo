package com.loction.xokhttp;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author tianxiaolong
 *         time：2019/8/15 21:28
 *         description：
 */

public class CancelType {

	public static final int CANCEL_DESTORY = 0x001;

	public static final int CANCEL_PAUSE   = 0x002;

	@IntDef({CANCEL_DESTORY,CANCEL_PAUSE})
	@Retention(RetentionPolicy.SOURCE)
	public @interface CancelState{

	}
}
