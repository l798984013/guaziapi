package com.mianze.apiexport.constant;

import com.mianze.apiexport.util.houben.Base64Utils;

public class Constant {
    //秒贷密钥
   public static final  String miaodaiKey="Egrq2vw82t5FNPYM";
   //秒贷渠道号
    //测试
//   public static final  String miaodaichannel="dp_channel_001";
    //正式
   public static final  String miaodaichannel="dp_channel_guaz";
   //秒贷
    //测试
//    public  static  final String miaodaiUrl="http://39.106.210.85/product/receive";
    //正式
    public  static  final String miaodaiUrl="https://e8x.cn/product/receive";
    public static final String ppdId = "70b986c9a2314ffc9cbf60211208181d";
    /**************************************************/
    /**
     * 各自的key值，具体请询问对接人员
     */
    public static final String SECRET_ID = "AKIDCKOv6dil1550ty2E1eHgF0A2UMX0r4C0o2I";
    public static final String SECRET_KEY = "erd1z2Vd7db293k892Scl62qzVN3jVbrzG9nrc1O";

    /**
     * host地址（test表示测试环境，release代表正式环境）
     */
    //测试
//    public static final String TEST_HOST = "https://service-42vfpr0p-1252830251.ap-shanghai.apigateway.myqcloud.com/test";
            //正式
    public static final String TEST_HOST = "https://service-42vfpr0p-1252830251.ap-shanghai.apigateway.myqcloud.com/release";

    /**
     * 接口具体地址（示例为进件测试接口）
     */
    //测试
//    public static final String TEST_INTERFACE_NAME = "/test/apiImport";
            //正式
    public static final String TEST_INTERFACE_NAME = "/start/import";
    /***************************************************/
    /********************展王*******************************/
    //测试进件
//    public static final String zhanwangUrl1 = "http://www.klqian.com/apiforzywonly/zywapiforone.html";
//    public static final String zhanwangUrl2 = "http://www.klqian.com/apiforzywonly/zywapifortwo.html";
    //正式
    public static final String zhanwangUrl1 = "http://www.klqian.com/apiforzywonly/apiforfirst.html";
    public static final String zhanwangUrl2 = "http://www.klqian.com/apiforzywonly/apiforsecond.html";
    /************************大金贷***************************/
    //查重测试
//    public static final String DaJinDaiPhone="http://116.62.204.155/creditUnion/largeCredit/applyDuplicateCheck";
    //正式
//    public static final String DaJinDaiPhone="https://www.jiayiunion.com/creditUnion/largeCredit/applyDuplicateCheck";
    //大金贷贷款申请接口测试
//    public static final String DaJinDaiUrl = "http://116.62.204.155/creditUnion/largeCredit/apply";
    //正式
    public static final String DaJinDaiUrl = "https://www.jiayiunion.com/creditUnion/largeCredit/apply";
    /***************************************************/

    //速易网络测试
//    public static final String SuYiUrl = "http://218.17.158.203:8022/api/mutiloan/reg.aspx?ChannelId=110556&AdsId=2104200";
    //正式
    public static final String SuYiUrl = "https://d.yoojum.com/api/mutiloan/reg.aspx?ChannelId=110556&AdsId=2104200";

}
