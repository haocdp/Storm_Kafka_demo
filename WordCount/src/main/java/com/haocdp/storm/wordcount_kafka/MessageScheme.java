package com.haocdp.storm.wordcount_kafka;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <br/>create at 16-1-20
 *
 * @author liuxh
 * @since 1.0.0
 */
public class MessageScheme implements Scheme {
    private static final Logger logger = LoggerFactory.getLogger(MessageScheme.class);

    @Override
    public List<Object> deserialize(byte[] ser) {
        try {
            String msg = new String(ser, "UTF-8");
            logger.info("get one message is {}", msg);
            return new Values(msg);
        } catch (UnsupportedEncodingException ignored) {
            return null;
        }
    }

    /*@Override
    public List<Object> deserialize(ByteBuffer ser) {
        //TODO
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        String msg = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            // charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
            charBuffer = decoder.decode(ser.asReadOnlyBuffer());
            msg = charBuffer.toString();
            logger.info("get one message is {}", msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Values(msg);
    }*/

    @Override
    public Fields getOutputFields() {
        return new Fields("msg");
    }
}
