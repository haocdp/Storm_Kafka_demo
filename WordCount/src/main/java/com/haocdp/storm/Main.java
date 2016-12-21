package com.haocdp.storm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haoc_dp on 2016/12/20.
 */
public class Main {
    private static void writeData(String data) {
        try {
            String path = Main.class.getName();
            File file = new File("/Users/haoc_dp/" + path);
            if(!file.exists()) {
                file.createNewFile();
            }

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(data + " --" + sdf.format(new Date()));
            stringBuffer.append("\n");
            fileOutputStream.write(stringBuffer.toString().getBytes("utf-8"));
            fileOutputStream.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)throws Exception {
        writeData("测试数据");
    }
}
