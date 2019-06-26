package com.bitkrx.config.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import java.io.FileInputStream;
import java.util.Properties;

public class RabbitmqClient {

    private static String HOST = "61.97.253.52";
    private static String HOST_STA = "61.97.253.140";
    private static String QUEUE_NAME = "KRW";
    private static String USER_NAME = "PlanBit";
    private static String PASSWORD = "PlanBit";
    private static String VIRTUAL_HOST = "PlanBit";
    private static int PORT = 10071;

    /*private static String HOST_STA = "180.70.92.35";
    private static String QUEUE_NAME_STA = "Order";
    private static String USER_NAME_STA = "PlanBit";
    private static String PASSWORD_STA = "PlanBit";
    private static String VIRTUAL_HOST_STA = "PlanBit";
    private static int PORT_STA = 10071;*/

    private static Connection connection = null;
    private static Channel channel = null;


    private static RabbitmqClient client = null;

    public static final String RELATIVE_PATH_PREFIX = RabbitmqClient.class.getResource("").getPath()
            + System.getProperty("file.separator") + ".." + System.getProperty("file.separator")
            + ".." + System.getProperty("file.separator") + ".." + System.getProperty("file.separator")+".." + System.getProperty("file.separator")
            + "cmeconfig" + System.getProperty("file.separator");

    public static final String GLOBALS_PROPERTIES_FILE
            = RELATIVE_PATH_PREFIX +  System.getProperty("file.separator") + "CmeProps" + System.getProperty("file.separator") + "dataStatus.properties";

    public static RabbitmqClient getinstance() throws Exception {

        Properties props = new Properties();

//        System.out.println("GLOBALS_PROPERTIES_FILE : " + GLOBALS_PROPERTIES_FILE);

        FileInputStream fis  = new FileInputStream(GLOBALS_PROPERTIES_FILE);
        props.load(new java.io.BufferedInputStream(fis));
        String status = props.getProperty("status").trim();
        String host = HOST;

        if(!"0".equals(status)) {
            host =   HOST_STA;
        }
        synchronized(RabbitmqClient.class){
            if(client == null) {
                client = new RabbitmqClient();
            }
            if(channel== null) {
                createChannel(host);
            }
            return client;
        }
    }

    public static void createChannel(String host) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        factory.setPort(PORT);
        factory.setVirtualHost(VIRTUAL_HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

    }

    public void close() throws Exception {
        channel.close();
        connection.close();
    }

    //rabbitMQ 메세지 발송 메소드
    public void sendMsg(String msg) throws Exception {


        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println(" KRW [x] Sent '" + msg + "'");

    }
}
