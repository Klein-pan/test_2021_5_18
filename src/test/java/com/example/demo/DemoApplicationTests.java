package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
//import com.example.demo.conf.TaskTest2;
import com.example.demo.mapper.sqlserver.OdsOpMaper;
import com.example.demo.mapper.sqlserver.Ods_patient_base_infoMapper;
import com.example.demo.pojo.client.Client;
import com.example.demo.pojo.model.Ods_op_account_list;
import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.textmodel.Textmodel;
import com.example.demo.pojo.model.textmodel.Textmodel2;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.RedisTemplate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@SpringBootTest
class DemoApplicationTests {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    RetryService retryService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Ods_ip_mrbase_infoService ods_ip_mrbase_infoService;
    @Autowired
    Ods_otherService ods_otherService;
    @Autowired
    Ods_hpServices ods_hpServices;
    @Autowired
    OdsOpMaper odsOpMaper;
//    @Autowired
//    TaskTest2 taskTest2;
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
        stringObjectHashMap.put("mdbase127_1", stringObjectMap.get("mdbase127_1"));

        stringObjectMap.remove("mdbase127_1");

        stringObjectHashMap.put("mdbase127_2", stringObjectMap.get("mdbase127_2"));

        stringObjectMap.remove("mdbase127_2");

        stringObjectHashMap.put("mdbase127_3", stringObjectMap.get("mdbase127_3"));
        listMap.add(stringObjectHashMap);
        stringObjectMap.remove("mdbase127_3");

        stringObjectMap.replace("mdbase127_s", listMap);
        // stringObjectHashMap.remove("mdbase127_3");

        String s = JSONObject.toJSONString(stringObjectMap, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(s);
        System.out.println(stringObjectMap);
        maps.add(stringObjectMap);
        HashMap<String, Object> stringObjectHashMap1 = new HashMap<>();
        stringObjectHashMap1.put("data", maps);
        ValueFilter filter = new ValueFilter() {
            @Override
            public Object process(Object obj, String s, Object v) {
                if (v == null)
                    return "";
                return v;
            }
        };
        String s1 = JSONObject.toJSONString(stringObjectHashMap1, filter, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println("s1:" + s1);
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
    void methnd1()  {
        List<Textmodel> tLIST = new ArrayList<>();
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
    @Test
    void methnd2() throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
//        String format = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(15));
//        System.out.println(format.compareTo("2017-01-01 00:00:00"));
//        System.out.println(format);

        List<Ods_op_account_list> accountLists = odsOpMaper.getods_op_account_list();
        for (Ods_op_account_list accountList : accountLists) {
            for (int i = 1; i <=52 ; i++) {
                String aClass = accountList.getClass().getName();
                Class<?> aClass1 = Class.forName(aClass);
                Field declaredField=null;
                Object o = null;
                String opaccountlist =null;
                if (i<=9) {
                    opaccountlist = "opaccountlist_0"+i;
                    declaredField = aClass1.getDeclaredField(opaccountlist);
                    declaredField.setAccessible(true);
                     o = declaredField.get(accountList);
                }
                else if (i>9){
                    opaccountlist = "opaccountlist_"+i;
                    declaredField = aClass1.getDeclaredField(opaccountlist);
                    declaredField.setAccessible(true);
                    o = declaredField.get(accountList);
                }
                if (o==null) {
                    if (opaccountlist.equals("opaccountlist_08")||opaccountlist.equals("opaccountlist_09")
                    ||opaccountlist.equals("opaccountlist_11")||opaccountlist.equals("opaccountlist_48")
                            ||opaccountlist.equals("opaccountlist_49") ){
                        declaredField.setAccessible(true);
                        declaredField.set(accountList,"1900-01-01 00:00:00");
                    }
                    else if (declaredField.getGenericType().toString().equals("class java.lang.String")){
                        declaredField.setAccessible(true);
                        declaredField.set(accountList,"-");
                    }else if (declaredField.getGenericType().toString().equals("class java.lang.Double")){
                        declaredField.setAccessible(true);
                        declaredField.set(accountList,0);
                    }

                }
            }
        }
    }
    @Test
    void test3(){
//        taskTest2.po();
//        taskTest2.po1();
//        taskTest2.po2();
//        taskTest2.po3();
//        taskTest2.po4();
//        taskTest2.po5();
//        taskTest2.po6();
//        taskTest2.po7();
//        taskTest2.po8();
//        taskTest2.po9();
//        taskTest2.po10();
//        taskTest2.po11();
//        taskTest2.po12();
//        taskTest2.po13();
//        taskTest2.po14();
//        taskTest2.po15();
//        taskTest2.po16();
       // taskTest2.po17();
//        taskTest2.po18();
//        taskTest2.po13();
//        taskTest2.po14();
//        taskTest2.po15();
//        taskTest2.po16();
    }
    @Autowired
    Client client;
    @Test
    void method4(){
        System.out.println(client.getClientId());
        System.out.println(client.getClientSecret());
        System.out.println(client.getKey());
    }
}
