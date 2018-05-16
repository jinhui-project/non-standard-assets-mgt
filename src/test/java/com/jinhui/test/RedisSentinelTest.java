package com.jinhui.test;


import com.jinhui.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by jinhui on 2017/10/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:springmvc-servlet.xml" })
public class RedisSentinelTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void Test1(){
        String groupId = "groupId";
        String key = "key";
        RedisUtils.setValue(key, groupId, "test");
        System.out.println(RedisUtils.getValue(key, groupId));
    }

}
