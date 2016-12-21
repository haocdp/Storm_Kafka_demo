package com.haocdp.storm.gps.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haoc_dp on 2016/12/20.
 */
public class GPSHandlerBolt2 extends BaseBasicBolt {
    private static final Logger logger = LoggerFactory.getLogger(GPSHandlerBolt1.class);

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String msg = tuple.getStringByField("msg");
        logger.info("get one message is {}", msg);
        //basicOutputCollector.emit(new Values(msg));
        writeData(msg);
    }

    private void writeData(String data) {
        try {
            String path = GPSHandlerBolt2.class.getName();
            File file = new File("/home/slave1/" + path);
            if(!file.exists()) {
                file.createNewFile();
            }

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(data + "--" + sdf.format(new Date()));
            stringBuffer.append("\n");
            fileOutputStream.write(stringBuffer.toString().getBytes("utf-8"));
            fileOutputStream.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
