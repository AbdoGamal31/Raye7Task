package com.raye7task.network;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ErrorCode.OFFLINE_ERROR
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorCode {
    int OFFLINE_ERROR = -100;
}
