package com.kcx.config;

import java.io.FileWriter;
import java.io.IOException;
/*支付宝接口配置*/
public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2021000116674512";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCIvl3n0zD5mM2TuHdpI7tmEPr" +
            "SnxHTNrSNZvOyshMyNKXN9DGRUgB8w3YgXcoYhbgEW1Vf3DzU3I8RfofxuuJXvlahXYdOZopKPebJLDLi87I4fni5TMaGk+cU9Y/A5i+mBI" +
            "pWeNYJIq3W8YVJ0w0kaMLrbrsmRCwTzQ1VpXkYYM5Fz6B1LAPuevjQ6wcWVVvWvVntoF9C9pNZ4xi4FMVCucHQDsjw0kxJ13Nu782eY/1xKI9" +
            "nENBbplse9B1iVWJcJV72yrVojuxeStnt7j8C6PBbwRUSQ0ShrEMFYaHK0UQYdhSVO4TYnbNbRa36iNdKhRCqgRV4hk3R0L20lzDTAgMBAAECgg" +
            "EAMEE1dDSTyNDCYwmqoeCpJeiCCsd1i9p3NpsJDptGqOXpSNeOkmCkqWJ0khFm2uZ9PJRaeVyH3voCRsMnCAfSDfKuMX2G3QTUckSL55wATRSA" +
            "ZO1Efud3+0/AMSZC12ijketyQfecWFbzj7FlPtsKfTm250wJIQTXpRksRngBVrdyWXkgYUjuZSGP/H58RTDspzEEeYUn9HZfYkieZXS5ZwdhsA77" +
            "Q1kpmwbAHbZuaD56BETBB610e8Vy6UeSTEmqodmSLRpTCQW38tlFzI5IKgkIDgyZEoO+5DURJJI67YLDKSnW881Fb7bi3Ywf4zWMA1RPwhLVtI68o" +
            "zkHsBKV0QKBgQDxUiYj8eMVgxU7Z1jwusQ2FWnObfkBFEYv+7Rn1Bw8fQQD5AlOL3uOZ3UffFK4g0bew8tr2FQbCB5nFGL4UMZIFqQ8ESVfGggaS3p" +
            "f7G4nyYhmTzo8GtNaZ/alyuJhm/ND5yviiq4ArFCSnNbXODmW/TECfuuvOekFHhzKwS+GKwKBgQCRD71YqnSrcxGlIC0G8RP6N+HgufW+lAONuQ+4+" +
            "znjmUe0eZcrlXdoZNfTvOCJmltWafzJzYRUnHevVklrmpNJp0ywiyjk9taY8xZWw1hjYKNGb8oDyApWo4hTY52Nj3j2tPs/czuZyqTsW+1g46gXTX2kT" +
            "JBG2QEYBQK9y3mT+QKBgGZ/eeuyhb1RW+dLUh+NdGF0XuhrFWQ79EPVKR8gnI2km9v7M2ieCmDF2J1y9L+iIqvjhbdcvvxsSl2F7zOBO3bLBx7sOW9XYz" +
            "Gn9WZgybjkhKbP5/A3hKXVmpF4xwamcoDY427pcFag2egr7aSkIPHcwMBKoFhgxHSEAb6spz7ZAoGBAIvSgg4u7xo7ZeymIQCp6zmqF70HQgoanXpDwR" +
            "XqghgRrP4WnVHTVi931Fkxm7TG5fdbXeAeiskLsIl/QHHJouLBsbgRyq84Bg2TchrbXB0eDwNbyXrvnJd6NXM7d6f1fQE/3VWBexd4LpPs39geV+1uMgb" +
            "mDcNcdCN04PmUYXHxAoGBAJIcQcAwABtD6EyOhygawIhXEPiqyDn14/229S/06x1lLLNs5VMLoE1MOi8yYIFa9berX0gLi6rY35b7M5uvLtuCVx2TdGBn" +
            "dnp90ddV2Z+M5WjlPRJcsctyZlR7UtV2SqxVk3rSKHxjavMa8g1EHR2OenRBXGCuni/Ljz+fHzPT";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6npSNIrJnFU1U2pQ9ybEvLFIT3X2Ved" +
            "tfgZqNVWnBWSnZqUF49mSL/tUFrbLvWKOktJPg25Itk1wIjoW77qZYVzRSlRFdyJg8BBCXu673GIKjeLzlazqxq3jQCrH55wrRwCmAsF6juuT" +
            "broSzotwAlylmeOO291O8Blo/DDYWTK4o7q6zekDWUMfA8y65xVNjO0BS40A5uaCBWHoAH48ZoEh9u/vFr1ebNEiNjs4MHP53lWxLR7cCFYCP" +
            "AwH/09l3gG9kPPioV9jzfs0GGRhZ66KoPnnmgs8tI6ptG8ON04JGm4zaq/uqdRTnUi2L9Glv+xk50Ko2kJah2uNUXBM7QIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8080/";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
