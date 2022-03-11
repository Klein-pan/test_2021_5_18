package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.example.demo.pojo.model.textmodel.Textmodel;
import com.example.demo.pojo.model.textmodel.Textmodel2;
import com.example.demo.services.Ods_ip_mrbase_infoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.RedisTemplate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
class DemoApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Ods_ip_mrbase_infoService ods_ip_mrbase_infoService;
    @Test
    void contextLoads() throws Exception {
        Textmodel textmodel = new Textmodel();
        textmodel.setMrbase_01(null);
        textmodel.setMrbase_02(123456);
        textmodel.setMdbase127_s("Mdbase127_s");
        textmodel.setMdbase127_1("Mdbase127_1");
        textmodel.setMdbase127_2(789456);
        textmodel.setMdbase127_3("Mdbase127_3");
        Map<String, Object> stringObjectMap = objectToMap(textmodel);
        List<Object> listMap = new ArrayList<>();
        ArrayList<Map> maps = new ArrayList<>();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("mdbase127_1",stringObjectMap.get("mdbase127_1"));

        stringObjectMap.remove("mdbase127_1");

        stringObjectHashMap.put("mdbase127_2",stringObjectMap.get("mdbase127_2"));

        stringObjectMap.remove("mdbase127_2");

        stringObjectHashMap.put("mdbase127_3",stringObjectMap.get("mdbase127_3"));
        listMap.add(stringObjectHashMap);
        stringObjectMap.remove("mdbase127_3");

        stringObjectMap.replace("mdbase127_s",listMap);
       // stringObjectHashMap.remove("mdbase127_3");

        String s = JSONObject.toJSONString(stringObjectMap, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(s);
        System.out.println(stringObjectMap);
        maps.add(stringObjectMap);
        HashMap<String, Object> stringObjectHashMap1 = new HashMap<>();
        stringObjectHashMap1.put("data",maps);
        ValueFilter filter = new ValueFilter() {
            @Override
            public Object process(Object obj, String s, Object v) {
                if (v == null)
                    return "";
                return v;
            }
        };
        String s1 = JSONObject.toJSONString(stringObjectHashMap1,filter, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println("s1:"+s1);
    }
    //java对象转map
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }
    @Test
    void methnd1() throws Exception {
        List<Textmodel> tLIST  = new ArrayList<>();
        Textmodel textmodel = new Textmodel();
        textmodel.setMrbase_01("123456");

        Textmodel textmodel2 = new Textmodel();
        textmodel2.setMrbase_01("234567");

        List<Textmodel2> listt2 = new ArrayList<>();
        Textmodel2 t1 = new Textmodel2();
        Textmodel2 t2 = new Textmodel2();
        t2.setMdbase127_1("123456");
        t1.setMdbase127_1("234567");

        for (Textmodel textmodel1 : tLIST) {
            String mdbase127_1 = textmodel1.getMdbase127_1();



        }
    }
}
