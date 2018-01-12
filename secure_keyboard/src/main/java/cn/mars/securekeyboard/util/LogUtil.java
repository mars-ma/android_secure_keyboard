package cn.mars.securekeyboard.util;

import android.os.Process;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ma.xuanwei on 2016/12/6.
 */

/**
 * 用于在DEBUG模式下输出Log
 */
public class LogUtil {
    private static boolean DEBUG =true;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String TAG = "mars";
    public static void setTAG(String tag){
        TAG = tag;
    }

    public static void setLogOn(boolean on){
        DEBUG = on;
    }

    public static void E(String msg){
        if (DEBUG){
            Log.e(TAG,msg);
        }
    }

    public static void E(String tag,String msg){
        if (DEBUG){
            Log.e(tag,msg);
        }
    }

    public static void D(String msg){
        if (DEBUG){
            Log.d(TAG,msg);
        }
    }

    public static void E(String msg,boolean showPID){
        if (DEBUG){
            Log.e(TAG,showPID? ("PID "+Process.myPid()+" TID "+Process.myTid()+" "+msg):msg);
        }
    }

    public static void E(String tag,String msg,boolean showPID){
        if (DEBUG){
            Log.e(tag,showPID? "PID "+Process.myPid()+" TID "+Process.myTid()+" "+msg:msg);
        }
    }

    public static void DT(String msg){
        if (DEBUG){
            Log.d(TAG,sdf.format(new Date())+":"+msg);
        }
    }

    public static void I(String msg){
        if (DEBUG){
            Log.i(TAG,msg);
        }
    }


    public static String printStackTrace(Throwable t) {
        if(t == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            t.printStackTrace(new PrintStream(baos));
        }finally{
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        E(baos.toString());
        return baos.toString();
    }
}
