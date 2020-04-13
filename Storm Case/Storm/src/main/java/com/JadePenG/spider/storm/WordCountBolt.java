package com.JadePenG.spider.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单词统计的Bolt
 */
public class WordCountBolt extends BaseRichBolt {

    private Map<String, String> map = new HashMap<>();

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    }

    /*
     *@Desc 处理业务，统计单词出现的次数 打印次数
     *@param tuple
     *@return void
     **/
    @Override
    public void execute(Tuple tuple) {
        String phone = tuple.getStringByField("phone");
        String dataFlow = tuple.getStringByField("dataFlow");
        String[] news = dataFlow.split(",");
        String str = map.get(phone);
        Integer upFlow=null;
        Integer downFlow=null;
        Integer upCountFlow=null;
        Integer downCountFlow=null;
        if (str != null) {//统计过当前的词
            String[] olds = str.split(",");
            upFlow = (Integer.parseInt(olds[0]) + Integer.parseInt(news[0]));
            downFlow = (Integer.parseInt(olds[1]) + Integer.parseInt(news[1]));
            upCountFlow = (Integer.parseInt(olds[2]) + Integer.parseInt(news[2]));
            downCountFlow = Integer.parseInt(olds[3]) + Integer.parseInt(news[3]);
            dataFlow = upFlow + "," + downFlow + "," + upCountFlow + "," + downCountFlow;
            System.out.println("流量变化,手机号:"+phone+",旧值:"+str+",新值:"+dataFlow);
            map.put(phone, dataFlow);
        } else {//第一次添加到map
            upFlow = Integer.parseInt(news[0]);
            downFlow = Integer.parseInt(news[1]);
            upCountFlow =  Integer.parseInt(news[2]);
            downCountFlow =  Integer.parseInt(news[3]);
            map.put(phone, dataFlow);
        }
        Map<String,Object> message=new HashMap<>();
        message.put("phone",phone);
        message.put("upFlow",upFlow);
        message.put("downFlow",downFlow);
        message.put("upCountFlow",upCountFlow);
        message.put("downCountFlow",downCountFlow);
        System.out.println(message);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}