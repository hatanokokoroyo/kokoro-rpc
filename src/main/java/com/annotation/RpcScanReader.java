package com.annotation;

public class RpcScanReader {
    /**
     * 读取Class中@RpcScan的scanPackages
     *
     * @param application 启动类
     * @return 包路径.
     */
    public static String[] readScanPackages(Class application) {
        RpcScan rpcScan = (RpcScan) application.getAnnotation(RpcScan.class);
        if (rpcScan == null) {
            return null;
        }
        return rpcScan.scanPackages();
    }
}
