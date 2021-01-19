package com.kcx.socket;

import com.alibaba.fastjson.JSON;
import com.kcx.pojo.Chat;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session WebSocketSession;
    //当前发消息的人员编号
    private String userId = "";
    /*
     * 连接建立成功调用的方法
     * @param userId 用户的Id
     * @param WebSocketSession 为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param config 可选的参数。

     */

    @OnOpen
    public void onOpen(@PathParam(value = "userId") String userId, Session WebSocketSession, EndpointConfig config) {
        this.userId = userId;//接收到发送消息的Id
        this.WebSocketSession = WebSocketSession;//得到当前服务端与客户端的会话连接
        webSocketSet.put(userId, this);//加入线程安全的Set中，键是用户Id，值是当前会话
        addOnlineCount(); //在线人数加1
        System.out.println("用户"+userId+"加入！当前在线人数为" + getOnlineCount());
    }

    //连接关闭调用的方法
    @OnClose
    public void onClose() {
        if (!userId.equals("")) {
            webSocketSet.remove(userId);  //从Set中删除该用户的会话信息
            subOnlineCount();//在线人数减1
            System.out.println("用户"+userId+"下线，连接关闭！当前在线人数为" + getOnlineCount());
        }
    }


    /*
     * 收到客户端消息后给所有人和指定的人发消息
     * @param message 客户端发送过来的消息
     * @param session 可选的参数

     */

/*	@OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        sendAll(message);
        //给指定的人发消息
        sendToUser(message);
    }*/


    /*
     * 给指定的人发送消息，注解表示了这是收到消息触发的事件
     * @param message

     */

    @OnMessage
    public void sendToUser(String message) {
        //得到消息
        String sendMessage = message.split("[|]")[0];
        //得到将要发送目标的Id
        String sendUserId = message.split("[|]")[1];
        //得到当前时间
        String now = getNowTime();
        try {
            if (webSocketSet.get(sendUserId) != null) {
                Chat chat=new Chat(1,userId,sendUserId,sendMessage,new Date());
                //webSocketSet.get(sendUserId).sendMessage(now + "用户" + userId + "发来消息："  + sendMessage);
                webSocketSet.get(sendUserId).sendMessage(JSON.toJSONString(chat));
            } else {
                System.out.println("当前用户不在线");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 给所有人发消息
     * @param message

     */

    private void sendAll(String message) {
        String now = getNowTime();
        String sendMessage = message.split("[|]")[0];
        //遍历HashMap
        for (String key : webSocketSet.keySet()) {
            try {
                //判断接收用户是否是当前发消息的用户
                if (!userId.equals(key)) {
                    webSocketSet.get(key).sendMessage(now + "用户" + userId + "发来消息：" + " <br/> " + sendMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*
     * 获取当前时间
     * @return
     */

    private String getNowTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }
    /*
     * 发生错误时调用
     * @param session
     * @param error
     */

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    /*
     * 服务器向客户端发送消息
     * @param message
     * @throws IOException
     */

    public void sendMessage(String message) throws IOException {
        this.WebSocketSession.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

}