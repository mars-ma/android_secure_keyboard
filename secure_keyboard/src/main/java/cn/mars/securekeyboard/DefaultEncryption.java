package cn.mars.securekeyboard;

import cn.mars.securekeyboard.util.DesUtil;
import cn.mars.securekeyboard.util.LogUtil;
import cn.mars.securekeyboard.util.RandomUtil;

/**
 * Created by ma.xuanwei on 2018/1/9.
 */

public class DefaultEncryption implements IEncryption {
    private String mKey;
    private DesUtil desUtil;

    DefaultEncryption(){
        mKey = RandomUtil.getRandomString(8);
        LogUtil.D("随机字符串:"+mKey);

        desUtil = new DesUtil();
    }

    @Override
    public String encode(String str) {
        LogUtil.D("原文:"+str);
        String str2 = desUtil.encode(mKey,str);
        LogUtil.D("密文:"+str2);
        return str2;
    }

    @Override
    public String decode(String str) {
        LogUtil.D("密文:"+str);
        String str2 = desUtil.decode(mKey,str);
        LogUtil.D("原文:"+str2);
        return str2;
    }
}
