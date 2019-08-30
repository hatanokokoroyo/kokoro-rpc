package com.tracker;

import com.annotation.RpcService;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RpcServiceScanner {
    // 默认扫描的包路径
    private static final String SCAN_PACKAGE = "com.service.impl";

    /**
     * 扫描指定包路径下的添加了@RpcService注解的类
     *
     * @param packagePath 需要扫描的包路径
     * @return List<Class>
     */
    public static List<Class> getRpcClass(String packagePath) {
        URL packageUrl = ClassLoader.getSystemClassLoader().getResource(packagePath.replace(".", "/"));
        if (packageUrl == null) {
            return new ArrayList<Class>();
        }
        File file = new File(packageUrl.getPath());
        if (!file.isDirectory()) {
            return new ArrayList<Class>();
        }
        return scanRpcClass(file, packagePath);
    }

    /**
     * 扫描指定目录下的添加了@RpcService注解的类
     *
     * @param directory   文件夹
     * @param packageName 当前文件夹对应的包路径
     * @return List<Class>
     */
    private static List<Class> scanRpcClass(File directory, String packageName) {
        List<Class> rpcClassList = new ArrayList<Class>();
        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<Class>();
        }
        for (File file : files) {
            if (file.isFile()) {
                Class target = parseRpcClass(packageName, file);
                if (target != null) {
                    rpcClassList.add(target);
                }
            } else {
                //递归, 扫描下一层目录
                rpcClassList.addAll(scanRpcClass(file, packageName + "." + file.getName()));
            }
        }
        return rpcClassList;
    }

    /**
     * 检查指定的文件是否是添加了@RpcService注解的类, 如果是, 解析为Class并返回
     *
     * @param packageName 当前文件对应的包路径
     * @param file        文件
     * @return Class or null
     */
    private static Class parseRpcClass(String packageName, File file) {
        if (!file.getName().endsWith(".class")) {
            return null;
        }
        Class target = null;
        try {
            //判断类是否有添加@RpcService注解
            String classRelativePath = packageName + "." + file.getName().replace(".class", "");
            target = Class.forName(classRelativePath);
            Annotation annotation = target.getAnnotation(RpcService.class);
            if (annotation == null) {
                return null;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return target;
    }
}
