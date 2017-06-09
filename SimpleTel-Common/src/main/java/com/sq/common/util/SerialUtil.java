package com.sq.common.util;

import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by qishang on 2017/6/9.
 */
public class SerialUtil {
   public static byte[] Serial(Object obj) throws IOException {
        // 序列化
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output h2o = new Hessian2Output(os);
        h2o.startMessage();
        h2o.writeObject(obj);
        h2o.completeMessage();
        h2o.close();
        byte[] buffer = os.toByteArray();
        os.close();
        return buffer;
    }

   public static Object DeSerial(byte[] arr, Class<?> clazz) throws IOException {
        // 反序列化
        ByteArrayInputStream in = new ByteArrayInputStream(arr);
        HessianInput input = new HessianInput(in);
        Object obj = input.readObject(clazz);
        input.close();
        in.close();
        return obj;
    }
}
