package cn.mars.securekeyborad;

/**
 * 定义加密接口
 * Created by ma.xuanwei on 2018/1/9.
 */

public interface IEncryption {
    /**
     * 定义加密接口
     * @param str
     * @return
     */
    String encode(String str);

    /**
     * 定义解密接口
     * @param str
     * @return
     */
    String decode(String str);
}
