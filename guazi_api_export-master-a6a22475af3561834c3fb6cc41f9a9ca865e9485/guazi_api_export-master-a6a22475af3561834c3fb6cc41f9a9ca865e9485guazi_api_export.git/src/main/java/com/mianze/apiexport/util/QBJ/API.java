package com.mianze.apiexport.util.QBJ;
import com.mianze.apiexport.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
@Slf4j
public class API {

    private Logger log= LoggerFactory.getLogger(API.class);
    public static String response(Object result) {
        String response= null;
        String apiUrl = Constant.TEST_HOST+Constant.TEST_INTERFACE_NAME;
        try {
            //构建鉴权
            Map<String, String> headers = Signature.buildSignatureHeaders(Constant.SECRET_ID, Constant.SECRET_KEY);
            //设置body中的参数（json提交），调用接口不同，所需参数不同
            /*JSONObject body = new JSONObject();
            body.put("XXX", "XXX");
            body.put("XXX", "XXX");*/
            //post请求，具体请求方式，看文档定
            response = HttpUtils.doPostByJson(apiUrl, headers, result);

        }catch (Exception e){
            e.printStackTrace();
//            log.error("请求发送发生错误，原因："+e.getMessage());
            return "error";
        }
//        log.info(String.valueOf(response));
        return response;
    }
}
