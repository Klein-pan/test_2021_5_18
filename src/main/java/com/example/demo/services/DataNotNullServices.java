package com.example.demo.services;

import com.example.demo.pojo.model.Ods_ip_mrbase_info;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_127;
import com.example.demo.pojo.model.Ods_op_account_info;
import com.example.demo.pojo.model.Ods_op_account_list;
import com.example.demo.pojo.model.Ods_op_adm_info;
import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.odsHp.Ods_hp_account_info;
import com.example.demo.pojo.model.odsHp.Ods_hp_account_list;
import com.example.demo.pojo.model.odsHp.Ods_hp_adm_info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class DataNotNullServices {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public List<Ods_op_account_list> opAccountListNotNull(List<Ods_op_account_list> accountLists) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
      log.info("Ods_op_account_list:开始处理空值数据"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        for (Ods_op_account_list accountList : accountLists) {
            for (int i = 1; i <=52 ; i++) {
                String aClass = accountList.getClass().getName();
                Class<?> aClass1 = Class.forName(aClass);
                Field declaredField=null;
                Object o = null;
                String opaccountlist = null;
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
        log.info("Ods_op_account_list:空值数据处理完成"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        return accountLists;
    }

    public List<Ods_op_adm_info> opAdmInfosNotNull(List<Ods_op_adm_info> ods_op_adm_infos) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        log.info("Ods_op_adm_info:开始处理空值数据"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        for (Ods_op_adm_info accountInfo : ods_op_adm_infos) {
            for (int i = 1; i <=33 ; i++) {
                String aClass = accountInfo.getClass().getName();
                Class<?> aClass1 = Class.forName(aClass);
                Field declaredField=null;
                Object o = null;
                String fieldName = null;
                if (i<=9) {
                    fieldName = "opadm_0"+i;
                    declaredField = aClass1.getDeclaredField(fieldName);
                    declaredField.setAccessible(true);
                    o = declaredField.get(accountInfo);
                }
                else if (i>9){
                    fieldName = "opadm_"+i;
                    declaredField = aClass1.getDeclaredField(fieldName);
                    declaredField.setAccessible(true);
                    o = declaredField.get(accountInfo);
                }
                if (o==null) {
                    if (fieldName.equals("opadm_06")||fieldName.equals("opadm_14")
                            ||fieldName.equals("opadm_20")||fieldName.equals("opadm_21")
                            ||fieldName.equals("opadm_30")||fieldName.equals("opadm_31") ){
                        declaredField.setAccessible(true);
                        declaredField.set(accountInfo,"1900-01-01 00:00:00");
                    }
                    else if (declaredField.getGenericType().toString().equals("class java.lang.String")){
                        declaredField.setAccessible(true);
                        declaredField.set(accountInfo,"-");
                    }else if (declaredField.getGenericType().toString().equals("class java.lang.Integer")){
                        declaredField.setAccessible(true);
                        declaredField.set(accountInfo,0);
                    }

                }
            }
        }
        log.info("Ods_op_adm_info:空值数据处理完成"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        return ods_op_adm_infos;
    }

    public List<Ods_op_account_info> opAccountInfosNotNull(List<Ods_op_account_info> account_infos) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        log.info("Ods_op_adm_info:开始处理空值数据"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        for (Ods_op_account_info account_info : account_infos) {
            for (int i = 1; i <=21 ; i++) {
                String aClass = account_info.getClass().getName();
                Class<?> aClass1 = Class.forName(aClass);
                Field declaredField=null;
                Object o = null;
                String fieldName = null;
                if (i<=9) {
                    fieldName = "opaccount_0"+i;
                    declaredField = aClass1.getDeclaredField(fieldName);
                    declaredField.setAccessible(true);
                    o = declaredField.get(account_info);
                }
                else if (i>9){
                    fieldName = "opaccount_"+i;
                    declaredField = aClass1.getDeclaredField(fieldName);
                    declaredField.setAccessible(true);
                    o = declaredField.get(account_info);
                }
                if (o==null) {
                    if (fieldName.equals("opaccount_07")||fieldName.equals("opaccount_18")
                            ||fieldName.equals("opaccount_19")){
                        declaredField.setAccessible(true);
                        declaredField.set(account_info,"1900-01-01 00:00:00");
                    }
                    else if (declaredField.getGenericType().toString().equals("class java.lang.String")){
                        declaredField.setAccessible(true);
                        declaredField.set(account_info,"-");
                    }else if (declaredField.getGenericType().toString().equals("class java.math.BigDecimal")){
                        declaredField.setAccessible(true);
                        declaredField.set(account_info,0);
                    }

                }
            }
        }
        log.info("Ods_op_adm_info:空值数据处理完成"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        return account_infos;
    }

    public List<Ods_hp_adm_info> hpAdmInfos(List<Ods_hp_adm_info> hpAmdInfos) {
        log.info("Ods_op_adm_info:开始处理空值数据"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        for (Ods_hp_adm_info hpAmdInfo : hpAmdInfos) {
            for (int i = 1; i <=28 ; i++) {
                String aClass = hpAmdInfo.getClass().getName();
                Class<?> aClass1 = null;
                try {
                    aClass1 = Class.forName(aClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new UDException(-1,"类加载失败","Ods_op_account_list未找到");
                }
                Field declaredField=null;
                Object o = null;
                String fieldName = null;
                if (i<=9) {
                    fieldName = "hpadm_0"+i;
                    try {
                        declaredField = aClass1.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new UDException(-1,"Field加载失败","Ods_op_account_list属性未找到");
                    }
                    declaredField.setAccessible(true);
                    try {
                        o = declaredField.get(hpAmdInfo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else if (i>9){
                    fieldName = "hpadm_"+i;
                    try {
                        declaredField = aClass1.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new UDException(-1,"Field加载失败","Ods_op_account_list属性未找到");
                    }
                    declaredField.setAccessible(true);
                    try {
                        o = declaredField.get(hpAmdInfo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (o==null) {
                    if (fieldName.equals("hpadm_25")||fieldName.equals("hpadm_26")
                            ||fieldName.equals("hpadm_06")||fieldName.equals("hpadm_05")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(hpAmdInfo,"1900-01-01 00:00:00");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (declaredField.getGenericType().toString().equals("class java.lang.String")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(hpAmdInfo,"-");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }else if (declaredField.getGenericType().toString().equals("class java.math.BigDecimal")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(hpAmdInfo,0);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        log.info("Ods_op_adm_info:空值数据处理完成"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        return hpAmdInfos;
    }

    public List<Ods_hp_account_info> hpaccountInfos(List<Ods_hp_account_info> accountInfos) {
        log.info("Ods_hp_account_info:开始处理空值数据"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        for (Ods_hp_account_info accountInfo : accountInfos) {
            for (int i = 1; i <=18 ; i++) {
                String aClass = accountInfo.getClass().getName();
                Class<?> aClass1 = null;
                try {
                    aClass1 = Class.forName(aClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new UDException(-1,"类加载失败","Ods_hp_account_info未找到");
                }
                Field declaredField=null;
                Object o = null;
                String fieldName = null;
                if (i<=9) {
                    fieldName = "hpaccount_0"+i;
                    try {
                        declaredField = aClass1.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new UDException(-1,"Field加载失败","Ods_hp_account_info属性未找到");
                    }
                    declaredField.setAccessible(true);
                    try {
                        o = declaredField.get(accountInfo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else if (i>9){
                    fieldName = "hpaccount_"+i;
                    try {
                        declaredField = aClass1.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new UDException(-1,"Field加载失败","Ods_hp_account_info属性未找到");
                    }
                    declaredField.setAccessible(true);
                    try {
                        o = declaredField.get(accountInfo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (o==null) {
                    if (fieldName.equals("hpaccount_06")||fieldName.equals("hpaccount_15")
                            ||fieldName.equals("hpaccount_15")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(accountInfo,"1900-01-01 00:00:00");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (declaredField.getGenericType().toString().equals("class java.lang.String")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(accountInfo,"-");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }else if (declaredField.getGenericType().toString().equals("class java.math.BigDecimal")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(accountInfo,0);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        log.info("Ods_hp_account_info:空值数据处理完成"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        return accountInfos;
    }

    public List<Ods_hp_account_list> hpaccountList(List<Ods_hp_account_list> accountLists) {
        log.info("Ods_hp_account_list:开始处理空值数据"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        for (Ods_hp_account_list accountList : accountLists) {
            for (int i = 1; i <=18 ; i++) {
                String aClass = accountList.getClass().getName();
                Class<?> aClass1 = null;
                try {
                    aClass1 = Class.forName(aClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new UDException(-1,"类加载失败","Ods_hp_account_list未找到");
                }
                Field declaredField=null;
                Object o = null;
                String fieldName = null;
                if (i<=9) {
                    fieldName = "hpaccountlist_0"+i;
                    try {
                        declaredField = aClass1.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new UDException(-1,"Field加载失败","Ods_hp_account_list属性未找到");
                    }
                    declaredField.setAccessible(true);
                    try {
                        o = declaredField.get(accountList);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else if (i>9){
                    fieldName = "hpaccountlist_"+i;
                    try {
                        declaredField = aClass1.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new UDException(-1,"Field加载失败","Ods_hp_account_list属性未找到");
                    }
                    declaredField.setAccessible(true);
                    try {
                        o = declaredField.get(accountList);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (o==null) {
                    if (fieldName.equals("hpaccountlist_06")||fieldName.equals("hpaccountlist_33")
                            ||fieldName.equals("hpaccountlist_34")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(accountList,"1900-01-01 00:00:00");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (declaredField.getGenericType().toString().equals("class java.lang.String")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(accountList,"-");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }else if (declaredField.getGenericType().toString().equals("class java.math.BigDecimal")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(accountList,0);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        log.info("Ods_hp_account_list:空值数据处理完成"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        return accountLists;
    }

    public List<Ods_ip_mrbase_info> ipMrbaseInfosNotNull(List<Ods_ip_mrbase_info> mrbaseInfos) {
        log.info("Ods_ip_mrbase_info:开始处理空值数据"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        for (Ods_ip_mrbase_info mrbaseInfo : mrbaseInfos) {
            for (int i = 1; i <=173 ; i++) {
                String aClass = mrbaseInfo.getClass().getName();
                Class<?> aClass1 = null;
                try {
                    aClass1 = Class.forName(aClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new UDException(-1,"类加载失败","Ods_ip_mrbase_info未找到");
                }
                Field declaredField=null;
                Object o = null;
                String fieldName = null;
                if (i<=9) {
                    fieldName = "mrbase_0"+i;
                    try {
                        declaredField = aClass1.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new UDException(-1,"Field加载失败","Ods_ip_mrbase_info属性未找到");
                    }
                    declaredField.setAccessible(true);
                    try {
                        o = declaredField.get(mrbaseInfo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else if (i>9){
                    fieldName = "mrbase_"+i;
                    try {
                        declaredField = aClass1.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new UDException(-1,"Field加载失败","Ods_ip_mrbase_info属性未找到");
                    }
                    declaredField.setAccessible(true);
                    try {
                        o = declaredField.get(mrbaseInfo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (o==null) {
                    if (fieldName.equals("mrbase_06")||fieldName.equals("mrbase_09")
                            ||fieldName.equals("mrbase_10") ||fieldName.equals("mrbase_170")
                            ||fieldName.equals("mrbase_171")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(mrbaseInfo,"1900-01-01 00:00:00");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }else if (fieldName.equals("mrbase_15")||fieldName.equals("mrbase_86")){
                        declaredField.setAccessible(true);
                    try {
                        declaredField.set(mrbaseInfo,"1900-01-01");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }}
                    else if (declaredField.getGenericType().toString().equals("class java.lang.String")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(mrbaseInfo,"-");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }else if (declaredField.getGenericType().toString().equals("class java.math.BigDecimal")){
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(mrbaseInfo,0);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        log.info("Ods_hp_account_list:空值数据处理完成"+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
    return mrbaseInfos;
    }

}

