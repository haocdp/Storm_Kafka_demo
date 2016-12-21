package whut.cn.gps;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

public class GPSProducer extends Thread{
    private String topic;  
    private Random random = new Random();
    private DecimalFormat format = new DecimalFormat(".######");
    
    public GPSProducer(String topic){  
        super();  
        this.topic = topic;  
    }  
      
      
    @Override  
    public void run() {  
        Producer<Integer, String> producer = createProducer();  
        int i=0;  
        String randomGps = "";
        while(true){  
            randomGps = "( " + format.format(random.nextDouble() * 180) + 
                    " , "+ format.format(random.nextDouble() * 180) + 
                    " )";
            producer.send(new KeyedMessage<Integer, String>(topic, randomGps));
            System.out.println(i++ + " -- " + randomGps);
            try {  
                TimeUnit.SECONDS.sleep(1);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    private Producer<Integer, String> createProducer() {  
        Properties properties = new Properties();  
        properties.put("zookeeper.connect", "server2:2182,server3:2183,server4:2184");//声明zk  
        properties.put("serializer.class", StringEncoder.class.getName());  
        properties.put("metadata.broker.list", "localhost:9092");// 声明kafka broker  
        return new Producer<Integer, String>(new ProducerConfig(properties));  
     }  
      
      
    public static void main(String[] args) {  
        new GPSProducer("gps").start();// 使用kafka集群中创建好的主题 test   
          
    }  
}
