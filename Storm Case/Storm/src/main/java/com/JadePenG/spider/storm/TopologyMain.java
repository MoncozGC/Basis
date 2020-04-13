package com.JadePenG.spider.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class TopologyMain {
    public static void main(String[] args) {
        //创建拓扑任务
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("readFileSpout", new ReadFileSpout());
        builder.setBolt("splitBolt", new SplitBolt()).shuffleGrouping("readFileSpout");
        builder.setBolt("wordCountBolt", new WordCountBolt()).shuffleGrouping("splitBolt");

        //将任务上传到storm上
        //上传的方式有两种 1本地上传 2集群上传
        LocalCluster localCluster = new LocalCluster();
        //1 上传任务的名称，不能重复
        Config conf = new Config();
        localCluster.submitTopology("wordCount", conf, builder.createTopology());

    }
}