//package com.example.demo.conf;
//
//import com.example.demo.pojo.model.vo.QueryVo;
//import com.example.demo.services.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Component
//@Slf4j
//public class TaskTest {
//    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    @Autowired
//    Ods_patient_base_infoService ods_patient_base_infoService;
//    @Autowired
//    Ods_ip_mrbase_infoService ods_ip_mrbase_infoService;
//    @Autowired
//    Ods_otherService ods_otherService;
//    @Autowired
//    OdsOpServices odsOpServices;
//    @Autowired
//    Ods_hpServices ods_hpServices;
//    @Scheduled(cron = "0 0/30 3,18 * * ?")
//        public  void po(){
//        //患者基本信息
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//           while (flag) {
//               String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//               String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//               log.info("开始传输从"+beginTime+"到"+endTime+"Ods_patient_base_infod历史数据");
//               List<QueryVo> info = ods_patient_base_infoService.getInfo(beginTime,endTime);
//               j+=15;
//               k+=15;
//               if (beginTime.compareTo("2017-01-01 00:00:00")<0){
//                   flag=false;
//               }
//           }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_patient_base_infoService.getInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        //System.out.println(System.currentTimeMillis());
//        //病案首页
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从"+beginTime+"到"+endTime+"Ods_ip_mrbase_info的历史数据");
//                //List<QueryVo> mrbaseInfo = ods_ip_mrbase_infoService.getMrbaseInfo();
//                List<QueryVo> mrbaseInfo = ods_ip_mrbase_infoService.getMrbaseInfo(beginTime,endTime);
//                j+=15;
//                k+=15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0){
//                    flag=false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_ip_mrbase_infoService.getMrbaseInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        //住院诊疗费用结算明细记录
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_ip_account_list的历史数据");
//                List<QueryVo> account_list = ods_ip_mrbase_infoService.getAccount_List(beginTime,endTime);
//                j += 15;
//                k += 15;
//
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_ip_mrbase_infoService.getAccount_list():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        //住院诊疗费用结算记录
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_ip_account_info的历史数据");
//                List<QueryVo> accountInfo = ods_ip_mrbase_infoService.getAccountInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_ip_mrbase_infoService.getAccountInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        //
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_ip_adm_info的历史数据");
//                List<QueryVo> admInfo = ods_ip_mrbase_infoService.getAdmInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_ip_mrbase_infoService.getAdmInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_adm_operation_info的历史数据");
//                List<QueryVo> operatoionInfo = ods_ip_mrbase_infoService.getOperatoionInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_ip_mrbase_infoService.getOperatoionInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_adm_pacsreport_info的历史数据");
//                List<QueryVo> pacsreportInfo = ods_ip_mrbase_infoService.getPacsreportInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_ip_mrbase_infoService.getPacsreportInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//    }
//    @Scheduled(cron = "0 0/30 3,18 * * ?")
//    public  void po1(){
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_resource_hospbed_info的历史数据");
//                List<QueryVo> hospbedInfo = ods_otherService.getHospbedInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_otherService.getHospbedInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_user_base_info的历史数据");
//                List<QueryVo> baseInfo = ods_otherService.getBaseInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_otherService.getBaseInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_chagedetail_list的历史数据");
//                List<QueryVo> chagedetailList = ods_otherService.getChagedetailList(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_otherService.getChagedetailList():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_dim_loc的历史数据");
//                List<QueryVo> dimIoc = ods_otherService.getDimIoc(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_otherService.getDimIoc():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_resource_locbed_info的历史数据");
//                List<QueryVo> locbedInfo = ods_otherService.getLocbedInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_otherService.getLocbedInfo():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_order_list的历史数据");
//                List<QueryVo> orderList = ods_otherService.getOrderList(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_otherService.getOrderList():上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//    }
//
//    @Scheduled(cron = "0 0/30 3,18 * * ?")
//    public  void po2(){
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_op_account_info的历史数据");
//                List<QueryVo> accountInfo = odsOpServices.getAccountInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("odsOpServices.getAccountInfo()：上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_op_account_list的历史数据");
//                List<QueryVo> accountList = odsOpServices.getAccountList(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("odsOpServices.getAccountList()：上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_op_adm_info的历史数据");
//                List<QueryVo> admInfo = odsOpServices.getAdmInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("odsOpServices.getAdmInfo()：上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//
//    }
//    @Scheduled(cron = "0 0/30 3,18 * * ?")
//    public  void po3(){
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_hp_account_info的历史数据");
//                //List<QueryVo> accountInfo = ods_hpServices.getAccountInfo(beginTime,endTime);
//                List<QueryVo> accountInfo = ods_hpServices.getAccountInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_hpServices.getAccountInfo()：上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_hp_account_list的历史数据");
//               // List<QueryVo> accountList = ods_hpServices.getAccountList(beginTime,endTime);
//                List<QueryVo> accountList = ods_hpServices.getAccountList(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_hpServices.getAccountList()：上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//
//        try {
//            long j = 15;
//            long k = 0;
//            boolean flag = true;
//            while (flag) {
//                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_hp_adm_info的历史数据");
//                //List<QueryVo> amdInfo = ods_hpServices.getAmdInfo();
//                List<QueryVo> amdInfo = ods_hpServices.getAmdInfo(beginTime,endTime);
//                j += 15;
//                k += 15;
//                if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        log.info("ods_hpServices.getAmdInfo()：上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//
//    }
//}