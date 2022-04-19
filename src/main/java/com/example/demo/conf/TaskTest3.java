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
//public class TaskTest3 {
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
//    @Autowired
//    MysqlServices mysqlServices;
//    @Scheduled(cron = "0 0/5 * * * ?  ")
//    public  void po() {
//        String format = "";
//        int sum = 0;
//        //患者基本信息
//        try {
////            long j = 15;
////            long k = 0;
////            boolean flag = true;
////            while (flag) {
////                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
////                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
////                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_patient_base_infod历史数据");
//            format = DATE_TIME_FORMATTER.format(LocalDateTime.now());
//            mysqlServices.addUpTimeStart(format,"Ods_patient_base_info");
//                List<QueryVo> info = ods_patient_base_infoService.getInfo();
//
//            for (QueryVo queryVo : info) {
//                sum += queryVo.getElementCount();
//            }
////                j += 15;
////                k += 15;
////                if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
////                    flag = false;
////                }
////            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        mysqlServices.addUpTimeEnd(format,"Ods_patient_base_info",DATE_TIME_FORMATTER.format(LocalDateTime.now()),sum);
//        log.info("ods_patient_base_infoService.getInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//        //System.out.println(System.currentTimeMillis());
//        //病案首页
//    }
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po1() {
////            try {
//////                long j = 15;
//////                long k = 0;
//////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_ip_mrbase_info的历史数据");
//////                    //List<QueryVo> mrbaseInfo = ods_ip_mrbase_infoService.getMrbaseInfo();
////                    List<QueryVo> mrbaseInfo = ods_ip_mrbase_infoService.getMrbaseInfo();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_ip_mrbase_infoService.getMrbaseInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////            //住院诊疗费用结算明细记录
////        }
////    @Scheduled(cron = "0 0/10 * * * ? ")
////    public  void po2() {
////        try {
//////            long j = 15;
//////            long k = 0;
//////            boolean flag = true;
//////            while (flag) {
//////                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_ip_account_list的历史数据");
////               List<QueryVo> account_list = ods_ip_mrbase_infoService.getAccount_list();
//////                j += 15;
//////                k += 15;
//////
//////                if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                    flag = false;
//////                }
//////            }
////        } catch (Exception e) {
////            log.info(e.getMessage());
////        }
////        log.info("ods_ip_mrbase_infoService.getAccount_list():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////    }
////        //住院诊疗费用结算记录
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po3() {
////            try {
//////                long j = 15;
//////                long k = 0;
//////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_ip_account_info的历史数据");
////                   List<QueryVo> accountInfo = ods_ip_mrbase_infoService.getAccountInfo();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_ip_mrbase_infoService.getAccountInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////
////        }
////        //住院就诊信息
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po4() {
////            try {
//////                long j = 15;
//////                long k = 0;
//////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_ip_adm_info的历史数据");
////                    List<QueryVo> admInfo = ods_ip_mrbase_infoService.getAdmInfo();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_ip_mrbase_infoService.getAdmInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////        }
////        //手术记录
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po5() {
////            try {
////                long j = 15;
////                long k = 0;
////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_adm_operation_info的历史数据");
////                   List<QueryVo> operatoionInfo = ods_ip_mrbase_infoService.getOperatoionInfo();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_ip_mrbase_infoService.getOperatoionInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////        }
////        //检查报告记录
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po6() {
////            try {
////                long j = 15;
////                long k = 0;
////                boolean flag = true;
////////                while (flag) {
////////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
////////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
////////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_adm_pacsreport_info的历史数据");
////                 List<QueryVo> pacsreportInfo = ods_ip_mrbase_infoService.getPacsreportInfo();
////////                    j += 15;
////////                    k += 15;
////////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
////////                        flag = false;
////////                    }
//////                }
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_ip_mrbase_infoService.getPacsreportInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////        }
////    @Scheduled(cron = "0 0/10 * * * ? ")
////    public  void po7() {
////        //全院床位使用情况统计表
////        try {
//////            long j = 15;
//////            long k = 0;
//////            boolean flag = true;
//////            while (flag) {
//////                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_resource_hospbed_info的历史数据");
////                List<QueryVo> hospbedInfo = ods_otherService.getHospbedInfo();
//////                j += 15;
//////                k += 15;
//////
//////                if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                    flag = false;
//////                }
//////            }
////
////        } catch (Exception e) {
////            log.info(e.getMessage());
////        }
////        log.info("ods_otherService.getHospbedInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////    }
////    @Scheduled(cron = "0 0/10 * * * ? ")
////    public  void po8() {
////        //职工信息表
////        try {
//////            long j = 15;
//////            long k = 0;
//////            boolean flag = true;
//////            while (flag) {
//////                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_user_base_info的历史数据");
////                List<QueryVo> baseInfo = ods_otherService.getBaseInfo();
//////                j += 15;
//////                k += 15;
//////                if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                    flag = false;
//////                }
//////            }
////
////        } catch (Exception e) {
////            log.info(e.getMessage());
////        }
////        log.info("ods_otherService.getBaseInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////    }
////        //收费项目明细记录表
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po9() {
////          try {
//////                long j = 15;
//////                long k = 0;
//////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_chagedetail_list的历史数据");
////                    List<QueryVo> chagedetailList = ods_otherService.getChagedetailList();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_otherService.getChagedetailList():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////        }
////    @Scheduled(cron = "0 0/10 * * * ? ")
////    public  void po10() {
////        //医疗机构原始科室字典表
////        try {
//////            long j = 15;
//////            long k = 0;
//////            boolean flag = true;
//////            while (flag) {
//////                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_dim_loc的历史数据");
////               List<QueryVo> dimIoc = ods_otherService.getDimIoc();
//////                j += 15;
//////                k += 15;
//////                if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                    flag = false;
//////                }
//////            }
////
////        } catch (Exception e) {
////            log.info(e.getMessage());
////        }
////        log.info("ods_otherService.getDimIoc():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////    }
////        //科室床位使用情况统计表
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po11() {
////            try {
//////                long j = 15;
//////                long k = 0;
//////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_resource_locbed_info的历史数据");
////                    List<QueryVo> locbedInfo = ods_otherService.getLocbedInfo();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_otherService.getLocbedInfo():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////        }
////
////        //医嘱明细记录表
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po12() {
////            try {
//////                long j = 15;
//////                long k = 0;
//////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_order_list的历史数据");
////                   List<QueryVo> orderList = ods_otherService.getOrderList();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_otherService.getOrderList():上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////        }
////        //门急诊诊疗费用结算记录
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po13() {
////            try {
//////                long j = 15;
//////                long k = 0;
//////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_op_account_info的历史数据");
////                   List<QueryVo> accountInfo = odsOpServices.getAccountInfo();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("odsOpServices.getAccountInfo()：上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////
////        }
////        //门急诊诊疗费用结算明细记录
////        @Scheduled(cron = "0 0/10 * * * ? ")
////        public  void po14() {
////            try {
//////                long j = 15;
//////                long k = 0;
//////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_op_account_list的历史数据");
////                    List<QueryVo> accountList = odsOpServices.getAccountList();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                        flag = false;
//////                    }
//////                }
////
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("odsOpServices.getAccountList()：上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////        }
////        //门急诊就诊信息
////            @Scheduled(cron = "0 0/10 * * * ? ")
////            public  void po15() {
////                try {
//////                    long j = 15;
//////                    long k = 0;
//////                    boolean flag = true;
//////                    while (flag) {
//////                        String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                        String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                        log.info("开始传输从" + beginTime + "到" + endTime + "Ods_op_adm_info的历史数据");
////                       List<QueryVo> admInfo = odsOpServices.getAdmInfo();
//////                        j += 15;
//////                        k += 15;
//////                        if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                            flag = false;
//////                        }
//////                    }
////                } catch (Exception e) {
////                    log.info(e.getMessage());
////                }
////                log.info("odsOpServices.getAdmInfo()：上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////            }
////
////            //体检费用结算明细记录
////            @Scheduled(cron = "0 0/10 * * * ? ")
////            public  void po16() {
////                try {
////                    long j = 15;
////                    long k = 0;
////                    boolean flag = true;
//////                    while (flag) {
//////                        String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                        String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                        log.info("开始传输从" + beginTime + "到" + endTime + "Ods_hp_account_list的历史数据");
//////                        // List<QueryVo> accountList = ods_hpServices.getAccountList();
////                        List<QueryVo> accountList = ods_hpServices.getAccountList();
//////                        j += 15;
//////                        k += 15;
//////                        if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                            flag = false;
//////                        }
//////                    }
////                } catch (Exception e) {
////                    log.info(e.getMessage());
////                }
////                log.info("ods_hpServices.getAccountList()：上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////            }
////            //体检就诊信息
////            @Scheduled(cron = "0 0/10 * * * ? ")
////            public  void po17() {
////             try {
////                long j = 15;
////                long k = 0;
////                boolean flag = true;
//////                while (flag) {
//////                    String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                    String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                    log.info("开始传输从" + beginTime + "到" + endTime + "Ods_hp_adm_info的历史数据");
//////                    //List<QueryVo> amdInfo = ods_hpServices.getAmdInfo();
////                    List<QueryVo> amdInfo = ods_hpServices.getAmdInfo();
//////                    j += 15;
//////                    k += 15;
//////                    if (beginTime.compareTo("2017-01-01 00:00:00")<0) {
//////                        flag = false;
//////                    }
//////                }
////            } catch (Exception e) {
////                log.info(e.getMessage());
////            }
////            log.info("ods_hpServices.getAmdInfo()：上传完成 "+DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////        }
////    //体检费用结算记录
////    @Scheduled(cron = "0 0/10 * * * ? ")
////    public void po18 () {
////        try {
////            long j = 15;
////            long k = 0;
////            boolean flag = true;
//////            while (flag) {
//////                String beginTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(j));
//////                String endTime = DATE_TIME_FORMATTER.format(LocalDateTime.now().minusDays(k));
//////                log.info("开始传输从" + beginTime + "到" + endTime + "Ods_hp_account_info的历史数据");
//////                //List<QueryVo> accountInfo = ods_hpServices.getAccountInfo();
////                List<QueryVo> accountInfo = ods_hpServices.getAccountInfo();
//////                j += 15;
//////                k += 15;
//////                if (beginTime.compareTo("2017-01-01 00:00:00") < 0) {
//////                    flag = false;
//////                }
//////            }
////
////        } catch (Exception e) {
////            log.info(e.getMessage());
////        }
////        log.info("ods_hpServices.getAccountInfo()：上传完成 " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
////    }
//    }
//
//
//
