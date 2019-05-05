package com.sbk.order.alipayConfig;
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092000556745";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCHlRwKfCya0putQtPnkNPwzwW5NSpFw/NWo3ReoFN/pQBhV2tng1cxybJkT8az5sRDByFGNNtTMCecUanJCVpHSWqe3KbmmBGkH7fL3VvHwfITo3enp5usPXiWsEdWfcRJZ+VdEk07HCJlSXZgpYQk4DKl+4TzS2XUo3J6+7NLJLw3CKf7zfJ8VIXbArJM4WASjO8TeZN9BuIS+M325UN4N6e3TNUjZeyvcsLq97XiAnz23p5n6NVzQx/9XadzrEjWBGhsKGbtZl1RyYRxpOA8KReR+j0weu02k2m7lNRi7hkWi4P2I7LVzgcy+K3cp+/6fWnZ4rySjeCGC7O8GFHzAgMBAAECggEAKftk3GZI1YPjSooH3Ys95THlXsz0kvey8J/aV7ajREIOVRXhnwLPTA5dW6xMJcaN/QdHKVsSkra1fTTYMvTfbHVyO1CtqXdq9vwPSGP5mGgYkjYqxdIjZPj/zwE0hbaZwfUW03PoUCZNDDnsGs8FlPmLFVRFfaRaGralqyknAlwd0FrAhKsipK/T+Q9qQe7QS/ePj+5jCz8YZ6/OgjtfLZDTK2woZW7PNpbAdYgkyexcDaA8Z/fyVYUl8HbJ3kq3i0luJIi3iAdxHpl8zDbCtGydJyr+WS7DK9r+p1lvxSFIvSH7SYksB6uQkl6pfre1e1jiRmkHNgMn4umu6yU9OQKBgQDtoXyv/7uD1Kr8gOc3p7dK0wcsXulheZSXSaNQ2xfOBKCU6k8G6+fIVa6LyCBGjLhvy35xJIAPh2AnhhfO8HYFyRTPVIKt0cjpGz2ZyIQdptBRkBBGuMvXeoF9G4NY6OXPIMpAT/hIh0tXfPlCtBqG49v/fWnNpUhDUl44oEdFxQKBgQCSECv+c3D3blhzTVLBPEFuSfUB2cqUKgwTXuWdYLVO4z9DMIIt0d/zts3NZFty48neavCiBuPHroH0ODAOHQv6ViehF0IP8QBUdcqb7tuRGrKpeYRD5bZSUU47Kb/SnYFvKCsziHFCJ49WGyfteyqrPeJmrIMOMwq6R20E+DbsVwKBgDQcid9+0Xxjkuu0yP/ng8ag6J3OYpfBtdhHic0dfubrFNwTVilKapYAVDrLNGjES14Y3i2pt3uwUGHagjYbfYZfRnUBL3c23jxggO3Mj0yBXIXGdgtMU0d50hPBiNhimqMFqdTYtLYTFW6mNXNvU7HpHkG1GxLCAErjMU5xt6+RAoGAetuZdbT6f1VUlt+iTSF20+LncAAWcpwPfjejIcZXmxxweP6TcSEIpAHOiAXzGedaRHhlIZR0AeDaNGcSnLx398CCIvDiQ3Bu25wcqR63lw0C3KR2yXmt6v2ytPb//5M2bR43gGgzA/csjLvY0Nt534H1LnHK5qWuT1OG8tgpso8CgYBNTLYGntdj8X5VX5nR8Bi9e5bqmg+7L3lZtFkPXGPQxT78EK8WwZM4VHMZ3C3VnhmWBaTNwqu/MjA6cccNKOeU4AJdgqmvWpSGzwuBwpYEECCgMoF3oxtWWt347whkImt5UwxJOKMvWycLDKvNBFdoy5NNdkWFxQ4drccR0OswCw==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtka1T7p1aKraRIp9W+JkDdB84S21eorhTh7CRMl1ALpgDkFjkTKzDI0TqVULvV/FA/g8VyBE6gQaAgGI5rFW2cLAYDvh2JWVkWbpLG/qev8jx2bgfW1YSQ9AXWYI+ZJUcTUmr5PYzdkuCOCxFUW5U9tMHmxEGw77NsraBFzWnZePw00rWryD7Mvo5GmMtOwhKMfi7kebt9uCpFixwBhU45Nhau/e3wMEJxg24DaoFXiE5vbiZlcrvGeEOmqC/4iOVIeNIqZYFpFEuJXFBJ5C8VTChX9dGsmEGJTirJPdE65EWS4lI5sb3vreiJSOA6ZT96qpob0EViRwMRMsn7Bf7wIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8093/notify_url.html";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8093/return_url.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "UTF-8";

    // 支付宝网关
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

