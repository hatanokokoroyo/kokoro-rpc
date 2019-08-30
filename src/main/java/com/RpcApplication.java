package com;

import com.annotation.RpcScanReader;
import com.annotation.RpcServiceScanner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class RpcApplication {
    public void run(Class application) {
        System.out.println("项目启动中");
        // 检查类是否有@RpcScan注解, 从中读取要扫描的包路径
        System.out.println("获取rpc包路径扫描配置");
        String[] scanPackages = RpcScanReader.readScanPackages(application);
        //TODO 没有@RpcScan则检查application.properties的配置, 如果都没有直接抛出异常
        // 扫描指定包路径下的rpc类
        if (scanPackages != null) {
            System.out.println("获取rpc包路径扫描配置成功: " + Arrays.toString(scanPackages));
            System.out.println("开始扫描rpc服务");
            List<Class> rpcClassList = RpcServiceScanner.getRpcClass(scanPackages);
            if (rpcClassList.isEmpty()) {
                System.out.println("无rpc服务");
            } else {
                System.out.println("扫描完成");
                for (Class aClass : rpcClassList) {
                    System.out.println("注册Rpc服务: " + aClass.getName());
                }
            }
            //TODO 连接zookeeper, 注册服务
        } else {
            System.out.println("未发现rpc包路径扫描配置");
        }
        //TODO 启动rpc监听进程
    }
}
