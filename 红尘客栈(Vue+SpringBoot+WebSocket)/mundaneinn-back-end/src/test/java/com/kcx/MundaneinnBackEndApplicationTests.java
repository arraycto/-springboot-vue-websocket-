package com.kcx;
import com.kcx.utils.CodeUtils;
import com.kcx.utils.SendSms;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class MundaneinnBackEndApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        //获得连接
        Connection connection =   dataSource.getConnection();
        System.out.println(connection);
        //关闭连接
        connection.close();
    }
    @Test
    void sendSms(){
        SendSms.sendSms("17365560614",CodeUtils.getCode());
    }
    @Test
    void getCode() throws JSONException {
       System.out.println( CodeUtils.getCode());
    }
    @Test
    void encryption(){//加密
        String mdStr=DigestUtils.md5Hex("123456");//e10adc3949ba59abbe56e057f20f883e
        System.out.println(mdStr);
    }
}
