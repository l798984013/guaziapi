import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mianze.apiexport.util.MD5;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class HaiDaiTest {
    private final String secret="sd6FSsbyvxw1f";
    @Test
    public void HaiDaiKj(){

        String data="{\n" +
                "    \"appKey\": \"hdkj\",\n" +
                "    \"sign\": \"BBD9E396ED5BFEB73F7EAAD4129B96E6\",\n" +
                "    \"data\": {\n" +
                "        \"customerName\": \"齐天大圣\",\n" +
                "        \"customerMobile\": \"1810201123\",\n" +
                "        \"houseType\": 1,\n" +
                "        \"carType\": 2,\n" +
                "        \"money\": 1000000,\n" +
                "        \"month\": 12,\n" +
                "        \"zoneId\": 10086,\n" +
                "        \"age\": 30,\n" +
                "        \"sex\": 2,\n" +
                "        \"salaryBankPublic\": 5100000,\n" +
                "        \"salaryBankPrivate\": 1,\n" +
                "        \"isFund\": 1,\n" +
                "        \"isSecurity\": 1,\n" +
                "        \"creditCard\": 2,\n" +
                "        \"isBuyInsurance\": 1,\n" +
                "        \"weixinLoanAmount\": 4100000,\n" +
                "        \"alipayLoanAmount\": 0\n" +
                "    }\n" +
                "}";
        TreeSet<String> treeSet=new TreeSet<>();
        treeSet.addAll(Arrays.asList("customerName","customerMobile","houseType","carType","money","month","zoneId","age","sex","salaryBankPublic","salaryBankPrivate","isFund","isSecurity",
                "creditCard","isBuyInsurance","weixinLoanAmount","alipayLoanAmount"));
        JSONObject jsonObject= JSON.parseObject(data);
        JSONObject jsonObjectData=jsonObject.getJSONObject("data");
        StringBuffer stringBuffer =new StringBuffer(secret);
        for(Iterator<String> iter = treeSet.iterator(); iter.hasNext();) {
            String itemKey=iter.next();
            stringBuffer.append(itemKey+jsonObjectData.getString(itemKey));
        }
        stringBuffer.append(secret);
        System.out.println(stringBuffer.toString());
        String md58str=MD5.md5(stringBuffer.toString());
        System.out.println(md58str.toUpperCase());
    }
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
}
