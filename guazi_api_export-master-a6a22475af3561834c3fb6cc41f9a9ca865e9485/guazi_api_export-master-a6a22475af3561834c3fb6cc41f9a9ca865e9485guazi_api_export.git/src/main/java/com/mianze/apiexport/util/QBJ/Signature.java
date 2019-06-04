package com.mianze.apiexport.util.QBJ;

import com.mianze.apiexport.constant.Constant;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Signature with API Gateways.
 * 
 * @author ChenZilong
 */
public class Signature {
	private static final String CONTENT_CHARSET = "UTF-8";
	private static final String HMAC_ALGORITHM = "HmacSHA1";

    private static String getSignature(String secretId, String secretKey,  String timeStr)
            throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String signStr = "date: "+timeStr;
        Mac mac1 = Mac.getInstance(HMAC_ALGORITHM);
        byte[] hash;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(CONTENT_CHARSET), mac1.getAlgorithm());
        mac1.init(secretKeySpec);
        hash = mac1.doFinal(signStr.getBytes(CONTENT_CHARSET));
        String sig  = new String(Base64.encode(hash));
        return "hmac id=\""+secretId+"\", algorithm=\"hmac-sha1\", headers=\"date\", signature=\""+sig+"\"";
    }

	public static Map<String, String> buildSignatureHeaders(String secretId, String secretKey) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		if (secretId == null || secretKey == null) {
			return Collections.emptyMap();
		}
		Map<String, String> headers = new HashMap<>();
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timeStr = sdf.format(cd.getTime());
		headers.put("Date", timeStr);
		headers.put("Authorization", getSignature(secretId, secretKey, timeStr));
        headers.put("secretId", Constant.SECRET_ID);
		return headers;
	}


}