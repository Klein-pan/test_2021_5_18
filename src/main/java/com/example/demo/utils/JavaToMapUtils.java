package com.example.demo.utils;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.example.demo.pojo.Bundle;
import com.example.demo.pojo.MessageHeader;
import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.model.excption.UDException;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class JavaToMapUtils {
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

    public static RespensBundle base64AndSm4AndSm3(List<Map> maps, HashMap<String, List> stringListHashMap, Integer count, String format,String table)  {
        stringListHashMap.put("data", maps);
        //添加过滤器
        ValueFilter filter = new ValueFilter() {
            @Override
            public Object process(Object obj, String s, Object v) {
                if (v == null)
                    return "";
                return v;
            }
        };
        String s2 = JSONObject.toJSONString(stringListHashMap, filter, SerializerFeature.DisableCircularReferenceDetect);
       // System.out.println("s2" + s2);
        String base64 = null;
        try {
            base64 = encodeBese(s2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UDException(-1,"json转base64发生错误","json转base64发生错误");
        }
        String sm4 = SMUtils.toSM4Str(base64, "hrj421g7w5a56590");
       //System.out.println("sm4:" + sm4);
        String sm3 = SMUtils.toSM3Str(sm4);
        //System.out.println(sm3);
        Bundle bundle = new Bundle();
        RespensBundle respensBundle = new RespensBundle();
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setSignature(sm3);
        messageHeader.setActionCode(table);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        messageHeader.setTimestamp(sdf.format(date));
        messageHeader.setTimestamp(format);
        messageHeader.setElementCount(count);//和sql查询条数同步。
        messageHeader.setOrgCode("121000004298902452");
        messageHeader.setSourceSystemCode("1");
        bundle.setMessageHeader(messageHeader);
        bundle.setId(UUID.randomUUID().toString());
        bundle.setMessageBody(sm4);
        respensBundle.setBundle(bundle);
        return respensBundle;
    }
    public static String encodeBese (String JSON) throws UnsupportedEncodingException {
        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        byte[] jsonBytes = JSON.getBytes("UTF-8");
        //编码
        String jsonBase64 = encoder.encodeToString(jsonBytes);
       // System.out.println("转码:" + jsonBase64);
        //解码
        byte[] decode = decoder.decode(jsonBase64);
       // System.out.println("解码：" + new String(decode, "UTF-8"));
        return jsonBase64;
    }
}
