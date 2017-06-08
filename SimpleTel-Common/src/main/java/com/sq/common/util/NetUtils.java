package com.sq.common.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by qishang on 2017/6/8.
 */
public class NetUtils {
    /**
     * 查看本地端口是否被占用
     * @param port
     * @return true:占用 false:未占用
     */
    public static boolean LocalPortIsUsing(int port) {
        boolean flag = true;
        try {
            flag = isPortUsing("127.0.0.1", port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 查看端口是否被占用
     * @param port
     * @return true:占用 false:未占用
     */
    public static boolean isPortUsing(String host, int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress theAddress = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(theAddress, port);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
