package com.test.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author test
 */
@Component
@ConfigurationProperties(prefix = "test")
public class TestConfig
{
    /** 项目名称 */
    private String name;



    /** 上传路径 */
    private static String profile;


    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        TestConfig.profile = profile;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}