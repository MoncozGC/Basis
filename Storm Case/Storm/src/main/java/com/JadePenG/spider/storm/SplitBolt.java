package com.JadePenG.spider.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Arrays;
import java.util.Map;

/**
 * 切割单词的Bolt
 */
public class SplitBolt extends BaseRichBolt {

    private OutputCollector outputCollector;

    /*
     *@Desc 类似于构造方法 读取上一层的数据
     *@param map
     *@param topologyContext
     *@param outputCollector
     *@return void
     **/
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    /*
     *@Desc 处理业务的方法，切割单词，将单词发送给下一层
     *@param tuple
     *@return void
     **/
    @Override
    public void execute(Tuple tuple) {
        String dataFlow = tuple.getStringByField("dataFlow");
        String[] words = dataFlow.split("\t");
        String phone = words[1];
        String str = words[6] + "," + words[7] + "," + words[8] + "," + words[9];
        outputCollector.emit(Arrays.asList(phone, str));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("phone", "dataFlow"));
    }
}