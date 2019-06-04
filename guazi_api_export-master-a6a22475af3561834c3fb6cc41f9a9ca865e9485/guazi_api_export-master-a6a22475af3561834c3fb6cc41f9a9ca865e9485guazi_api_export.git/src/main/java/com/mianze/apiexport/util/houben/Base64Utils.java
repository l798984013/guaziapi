package com.mianze.apiexport.util.houben;

import java.io.UnsupportedEncodingException;


public class Base64Utils {

    /**
     * Base64������д
     */
    public static void main(String[] args) {
        String aa = "�̻�˽����";
        String aaa = byteArrayToBase64(aa.getBytes());
        System.out.println(aaa);
        try {
            String bb = new String(base64ToByteArray(aaa));
            System.out.println(bb);

            String tests = encodeBuffer("���񱾽��ڡ�", "GBK");
            System.out.println(tests);
            System.out.println(decodeBuffer(tests, "GBK"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * ���ַ�����base64�ַ���
     * @param a Ҫת�����ַ�����
     * @return  base64�ַ���
     */
    public static String byteArrayToBase64(byte[] a) {
             int aLen = a.length; //�ܳ���
             int numFullGroups = aLen / 3; //��3��byte�����4���ַ�Ϊһ�������
             int numBytesInPartialGroup = aLen - 3 * numFullGroups; //����
             int resultLen = 4 * ((aLen + 2) / 3); //�����������4�����������������(aLen+2)/3��֤���������������пռ��������=
             StringBuffer result = new StringBuffer(resultLen);
             int inCursor = 0;
             for (int i = 0; i < numFullGroups; i++) {
                int byte0 = a[inCursor++] & 0xff;
                int byte1 = a[inCursor++] & 0xff;
                int byte2 = a[inCursor++] & 0xff;
                result.append(intToBase64[byte0 >> 2]);
                result.append(intToBase64[(byte0 << 4) & 0x3f | (byte1 >> 4)]);
                result.append(intToBase64[(byte1 << 2) & 0x3f | (byte2 >> 6)]);
                result.append(intToBase64[byte2 & 0x3f]);
            }
            //��������
            if (numBytesInPartialGroup != 0) {
                int byte0 = a[inCursor++] & 0xff;
                result.append(intToBase64[byte0 >> 2]);
                //����Ϊ1
                if (numBytesInPartialGroup == 1) {
                    result.append(intToBase64[(byte0 << 4) & 0x3f]);
                    result.append("==");
                } else {
                    // ����Ϊ2
                    int byte1 = a[inCursor++] & 0xff;
                    result.append(intToBase64[(byte0 << 4) & 0x3f | (byte1 >> 4)]);
                    result.append(intToBase64[(byte1 << 2) & 0x3f]);
                    result.append('=');
                }
            }
            return result.toString();
        }

    static final char intToBase64[] = { 'A', 'B', 'C', 'D', 'E', 'F', /* ���� 0 ~ 5*/
        'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',  /* ����6 ~ 18*/
        'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',  /* ���� 19 ~ 31*/
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',  /* ���� 32 ~ 44*/
        't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',  /* ���� 45 ~ 57*/
        '6', '7', '8', '9', '.'/*ԭ�����ַ�+*/, '_'/*ԭ�����ַ�/ */ };  /* ����58 ~ 63*/

    /**
     * base64�ַ�����ԭΪ�ַ�����
     * @param s base64�ַ���
     * @return ��ԭ����ַ�������
     * @throws Exception
     */
    public static byte[] base64ToByteArray(String s) throws Exception {
             //�ַ��ܳ�������4�ı���
             int sLen = s.length();
             int numGroups = sLen / 4;
             if (4 * numGroups != sLen)
                 throw new IllegalArgumentException(
                         "�ִ����ȱ�����4�ı���");
             //��1��byte����©������byte����2��byte����©����1��byte
             int missingBytesInLastGroup = 0;
             int numFullGroups = numGroups;
             if (sLen != 0) {
                //��2��byte�����
                if (s.charAt(sLen - 1) == '=') {
                    missingBytesInLastGroup++;
                    //���������������������3��byte������һ����
                    numFullGroups--;
                }
                //��1��byte�����
                if (s.charAt(sLen - 2) == '=')
                    missingBytesInLastGroup++;
            }

            //���ֽڳ���

            byte[] result = new byte[3 * numGroups - missingBytesInLastGroup];
            try {
                int inCursor = 0, outCursor = 0;
                for (int i = 0; i < numFullGroups; i++) {
                    int ch0 = base64toInt(s.charAt(inCursor++), base64ToInt);
                    int ch1 = base64toInt(s.charAt(inCursor++), base64ToInt);
                    int ch2 = base64toInt(s.charAt(inCursor++), base64ToInt);
                    int ch3 = base64toInt(s.charAt(inCursor++), base64ToInt);
                    result[outCursor++] = (byte) ((ch0 << 2) | (ch1 >> 4));
                    result[outCursor++] = (byte) ((ch1 << 4) | (ch2 >> 2));
                    result[outCursor++] = (byte) ((ch2 << 6) | ch3);
                }
                if (missingBytesInLastGroup != 0) {
                    int ch0 = base64toInt(s.charAt(inCursor++), base64ToInt);
                    int ch1 = base64toInt(s.charAt(inCursor++), base64ToInt);
                    //������1������2��byte���϶�Ҫ����һ��byte��
                    result[outCursor++] = (byte) ((ch0 << 2) | (ch1 >> 4));
                    //�����2��������һ���Ź���3byte����ô��Ҫ����ڶ���byte��
                    if (missingBytesInLastGroup == 1) {
                        int ch2 = base64toInt(s.charAt(inCursor++), base64ToInt);
                        result[outCursor++] = (byte) ((ch1 << 4) | (ch2 >> 2));
                    }
                }
            } catch (Exception e) {
                throw e;
            }
            return result;
        }
        private static int base64toInt(char c, byte[] alphaToInt) throws Exception {
            int result = alphaToInt[c];
            if (result < 0)
                throw new Exception("illegal index!");//�Ƿ�����ֵ
            return result;
        }
        static final byte base64ToInt[] = { -1, -1, -1, -1, -1, -1, -1, -1,
                         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                         -1, -1/*ԭ����62*/, -1, -1, 62/*ԭ����-1*/, -1/*ԭ����63*/, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1,
                         -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                         13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1,
                         63/*ԭ����-1*/, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                         41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };

        /**
         * @param strԭʼ�����ַ���
         * @param charset���ж������ַ���ת�����ַ�����
         * @return �������ַ���
         */
        public static String encodeBuffer(String str ,String charset){
            if (str == null) {
                str = "";
            }
            try {
                return  Base64Utils.byteArrayToBase64(str.getBytes(charset));
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
        /**
         * �˷���Ϊ��ԭ�����ַ���ת����Ķ������ַ���
         * @param str �������ַ���
         * @param charset �ַ�����
         * @return  ���������ַ���
         */
        public static String decodeBuffer(String str ,String charset){
            if (str == null) {
                return "";
            }
             try {
                byte[] byteStr;
                try {
                    byteStr = Base64Utils.base64ToByteArray(str.trim());
                } catch (Exception e) {
                    return "";
                }
                return new String(byteStr,charset);
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
}
