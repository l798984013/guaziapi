package com.mianze.apiexport.mapper;

import com.alibaba.fastjson.JSONObject;
import com.mianze.apiexport.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Repository
public interface ApiMapper {

    Integer isToggleOpen();

    List<ChannelExport> getChannelExports();
    LoanInfo queryLoanInfo(@Param("orderNo") long orderNo);
    JSONObject findExportChannel(Integer id);
    JSONObject findGuaziCity(String location);
    void addExportResult(@Param("status") Integer status,@Param("order_no") String order_no,@Param("code") String code,
                         @Param("msg") String msg,@Param("data") String data,@Param("orderData") String orderData,@Param("phone") String phone,
                         @Param("exportChannel") String exportChannel,@Param("importChannel") String importChannel);
    String findPhone(@Param("id") String id);
    //大金贷
    JSONObject daJinDaiCity(@Param("location") String city);

    Integer zhanYeWangCity(@Param("location") String city);
    Integer suyiwangluoCity(@Param("location") String city);
    String findExistenceOrder(@Param("orderNo") String orderNo);
    void updateBackStatus(@Param("grabOrder") Integer grabOrder,@Param("grabOrderPrice") String grabOrderPrice,@Param("orderNo") String orderNo);

    default Integer findExistenceOrder1(@Param("orderNo") String orderNo) {
        return null;
    }

    void updateChargeback(@Param("chargeback") Integer chargeback,@Param("chargebackPrice") String chargebackPrice,@Param("orderNo") String orderNo);

    Integer queryLoanAmount();
}



