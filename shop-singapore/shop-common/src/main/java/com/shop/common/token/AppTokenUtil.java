package com.shop.common.token;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import com.shop.common.dateutil.DateUtil;

/**
 * token加密与解密
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
public class AppTokenUtil {

	public static final String TOKEN_SECRET_LOGIN = "singapore_login";

	/**
	 * 检查密钥长度,如不足24位,则加0x00补齐
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年1月16日
	 * 
	 * @param keyString
	 * @return
	 */
	private static byte[] getKeyBytes(String keyString) {
		byte[] b = new byte[24];
		byte[] key = keyString.getBytes();
		int count = keyString.getBytes().length;
		for (int i = 0; i < count; i++) {
			b[i] = key[i];
		}
		for (int i = count; i < 24; i++) {
			b[i] = 0x20;
		}
		return b;
	}

	/**
	 * 是否通过token认证
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年1月16日
	 * 
	 * @param authentication
	 * @param secret
	 * @return
	 */
	public static boolean isAuthToken(String authToken, String loginName) {

		boolean authFlag = false;

		HashMap<String, String> map = analysisToken(authToken, TOKEN_SECRET_LOGIN);

		// 用户名密码正确
		if (map != null && !map.isEmpty()) {

			if ("1".equals(map.get("FLA�")) && loginName.equals(map.get("UserId"))) {

				// 当前时间小于等于失效时间,则验证token通过
				if (DateUtil.getNow().getTime() <= Long.valueOf(map.get("EXPTIME"))) {
					authFlag = true;
				}
			}
		}

		return authFlag;
	}

	/**
	 * token解析
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年1月23日
	 * 
	 * @param authToken
	 * @return
	 */
	public static HashMap<String, String> analysisToken(String authToken) {

		return analysisToken(authToken, TOKEN_SECRET_LOGIN);
	}

	/**
	 * token解析
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年1月23日
	 * 
	 * @param authToken
	 * @return
	 */
	public static HashMap<String, String> analysisToken(String authToken, String secret) {

		if (StringUtils.isEmpty(authToken)) {
			return null;
		}

		// 拆分值并存入Map中
		HashMap<String, String> map = null;

		try {
			authToken = URLDecoder.decode(authToken, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 拆分认证码验证
		if (authToken.length() <= 4 || !("MMFQ".equals(authToken.substring(0, 4)))) {
			return null;
		}

		try {
			// 解析
			String authValue = analysisSpecialCharacter(authToken.substring(4));

			// 24字节密钥key,3倍DES密钥长度
			byte[] tripleKey = getKeyBytes(secret);

			// 生成密钥
			SecretKey secretKey = new SecretKeySpec(tripleKey, "DESede");
			String transformation = "DESede/CBC/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(transformation);

			// CBC方式的初始化向量
			byte[] iv = new byte[] { 93, 81, 122, 33, 47, 50, 17, 103 };
			IvParameterSpec ivparam = new IvParameterSpec(iv);

			// 解密
			byte[] auth = new sun.misc.BASE64Decoder().decodeBuffer(authValue);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparam);
			String textValue = new String(cipher.doFinal(auth));

			// 验证
			if (StringUtils.isNotEmpty(textValue)) {

				map = new HashMap<String, String>();

				String[] tokenValue = textValue.split(";");

				for (int i = 0; i < tokenValue.length; i++) {
					String[] value = tokenValue[i].split("=");
					map.put(value[0], value[1]);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return map;
	}

	/**
	 * 创建token
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年1月16日
	 * 
	 * @param host
	 * @param apId
	 * @param secret
	 * @return
	 */
	public static String createToken(String userId, Date expDate) {

		String orgToken = "FLAG=" + 1 + ";UserId=" + userId + ";EXPTIME=" + String.valueOf(expDate.getTime());

		return generateToken(orgToken, TOKEN_SECRET_LOGIN);
	}

	/**
	 * 生成带用户名/密码的token字符串
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年1月16日
	 * 
	 * @param host
	 * @param apId
	 * @param apSecret
	 * @param secret
	 * @return
	 */
	public static String generateToken(String orgToken, String apSecret) {

		if (StringUtils.isEmpty(orgToken)) {
			return null;
		}

		String authenticator = "MMFQ";

		try {

			// 24字节密钥key,3倍DES密钥长度
			byte[] tripleKey = getKeyBytes(apSecret);

			// 生成密钥
			SecretKey secretKey = new SecretKeySpec(tripleKey, "DESede");

			String transformation = "DESede/CBC/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(transformation);

			// CBC方式的初始化向量
			byte[] iv = new byte[] { 93, 81, (byte) 122, (byte) 233, 47, 50, 17, (byte) 103 };
			IvParameterSpec ivparam = new IvParameterSpec(iv);

			// 加密
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparam);
			byte[] encriptText = cipher.doFinal(orgToken.getBytes());

			// 加密
			authenticator += replaceSpecialCharacter(new sun.misc.BASE64Encoder().encode(encriptText));

			authenticator = URLEncoder.encode(authenticator, "utf-8");
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return authenticator;
	}

	/**
	 * 替换特殊字符
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年10月31日
	 *
	 * @param str
	 * @return
	 */
	public static String replaceSpecialCharacter(String str) {

		str = str.replace("/", "_a");
		str = str.replace("+", "_b");

		return str;
	}

	/**
	 * 解析特殊字符
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年10月31日
	 *
	 * @param str
	 * @return
	 */
	public static String analysisSpecialCharacter(String str) {
		str = str.replaceAll("_a", "/");
		str = str.replaceAll("_b", "+");

		return str;
	}
}
