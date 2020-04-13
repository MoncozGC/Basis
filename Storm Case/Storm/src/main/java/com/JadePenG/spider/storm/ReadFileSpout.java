package com.JadePenG.spider.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

/**
 * 读取文件的Spout
 */
public class ReadFileSpout extends BaseRichSpout {

    private BufferedReader reader;
    private SpoutOutputCollector spoutOutputCollector;

    /*
     *@Desc 类似于构造方法 在类创建的时候会执行一次 获取数据
     *@param map storm集群和用户自定义的配置 一般不用
     *@param topologyContext 上下文对象 一般也不用
     *@param spoutOutputCollector 可以将数据读取到collector 由collector将数据发送给storm框架
     *@return void
     **/
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        try {
            this.spoutOutputCollector = spoutOutputCollector;
            URL url = this.getClass().getClassLoader().getResource("input/data_flow.dat");
            reader = new BufferedReader(new FileReader(new File(url.getFile())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *@Desc 读取内容  Tuple 是storm传递数据的基本单位 底层有while循环，可以不停去读取数据
     *@param
     *@return void
     **/
    @Override
    public void nextTuple() {
        try {
            String line = reader.readLine();
            if (line != null && line.length() > 0) {
                spoutOutputCollector.emit(Arrays.asList(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     *@Desc 定义输出的字段
     *@param outputFieldsDeclarer
     *@return void
     **/
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("dataFlow"));
    }
}