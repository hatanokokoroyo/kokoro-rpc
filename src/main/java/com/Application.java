package com;

import com.annotation.RpcScan;

//TODO 实现包扫描注解, 通过注解的方式来指定扫描rpc注解
@RpcScan(scanPackages = {"com.service.impl"})
public class Application {
    public static void main(String[] args) {
        //TODO 新增启动器类, 注入Application.class, 执行rpc注解扫描.
        RpcApplication rpcApplication = new RpcApplication();
        rpcApplication.run(Application.class);
    }
}
