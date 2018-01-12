package cn.mars.securekeyboard.util;

import java.util.Random;

/**
 * Created by ma.xuanwei on 2018/1/11.
 */

public class RandomUtil {
    private static char[] mContent;

    /**
     * 得到长度为length的随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        if(length<=0){
            return null;
        }
        //随机字符是数字,大小写英文,32个符号
        if(mContent==null){
            mContent = new char[94];
            for(int i = 33;i<127;i++){
                mContent[i-33] = (char) i;
            }
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i< length;i++){
            sb.append(mContent[random.nextInt(94)]);
        }
        return sb.toString();
    }
}
