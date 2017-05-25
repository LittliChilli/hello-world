//package com.shop.service.common.impl;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//
//import com.shop.service.common.CookieHandlingService;
//
//@Service
//public class CookieHandlingServiceImpl implements CookieHandlingService {
//	private static final Logger logger = LoggerFactory.getLogger(CookieHandlingServiceImpl.class);
//
//	public static final String DEFAULT_COOKIE_NAME = "oau";
//	public static final String DEFAULT_AdminT_COOKIE_NAME = "aoau";
//
//	private SecureRandom random = null;
//
//	public static final int DEFAULT_SERIES_LENGTH = 16;
//	public static final int DEFAULT_TOKEN_LENGTH = 16;
//
//	private int seriesLength = DEFAULT_SERIES_LENGTH;
//	private int tokenLength = DEFAULT_TOKEN_LENGTH;
//	private static final String DELIMITER = ":";
//
//	private boolean useSecureCookie = false;
//
//	/**
//	 * 保存在浏览器中cookie的最大时间14天
//	 */
//	public final static int BCOOKIE_MAX_AGE = 14 * 24 * 60 * 60;
//
//	/**
//	 * 保存在浏览器中cookie的最大时间14天
//	 */
//	public final static int AdminT_MAX_AGE = 14 * 24 * 60 * 60;
//
//	/**
//	 * 保存浏览器中cookie的最大时间30天
//	 *
//	 */
//	public final static int TokenCookie_MAX_AGE = 30 * 24 * 60 * 60;
//
//	public CookieHandlingServiceImpl() {
//		super();
//		try {
//			random = SecureRandom.getInstance("SHA1PRNG");
//		} catch (NoSuchAlgorithmException e) {
//			logger.error(e.getMessage(), e);
//		}
//	}
//
//	protected String generateSeriesData() {
//		byte[] newSeries = new byte[seriesLength];
//		random.nextBytes(newSeries);
//		return new String(Base64.getEncoder().encode(newSeries));
//	}
//
//	protected String generateTokenData() {
//		byte[] newToken = new byte[tokenLength];
//		random.nextBytes(newToken);
//		return new String(Base64.getEncoder().encode(newToken));
//	}
//
//	/**
//	 * Decodes the cookie and splits it into a set of token strings using the
//	 * ":" delimiter.
//	 *
//	 * @param cookieValue
//	 *            the value obtained from the submitted cookie
//	 * @return the array of tokens.
//	 */
//	public String[] decodeCookie(String cookieValue) {
//		for (int j = 0; j < cookieValue.length() % 4; j++) {
//			cookieValue = cookieValue + "=";
//		}
//
//		String cookieAsPlainText = new String(Base64.getDecoder().decode(cookieValue.getBytes()));
//
//		String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, DELIMITER);
//
//		if ((tokens[0].equalsIgnoreCase("http") || tokens[0].equalsIgnoreCase("https")) && tokens[1].startsWith("//")) {
//			// Assume we've accidentally split a URL (OpenID identifier)
//			String[] newTokens = new String[tokens.length - 1];
//			newTokens[0] = tokens[0] + ":" + tokens[1];
//			System.arraycopy(tokens, 2, newTokens, 1, newTokens.length - 1);
//			tokens = newTokens;
//		}
//
//		return tokens;
//	}
//
//	/**
//	 * Inverse operation of decodeCookie.
//	 *
//	 * @param cookieTokens
//	 *            the tokens to be encoded.
//	 * @return base64 encoding of the tokens concatenated with the ":"
//	 *         delimiter.
//	 */
//	@Override
//	public String encodeCookie(String[] cookieTokens) {
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < cookieTokens.length; i++) {
//			sb.append(cookieTokens[i]);
//
//			if (i < cookieTokens.length - 1) {
//				sb.append(DELIMITER);
//			}
//		}
//
//		String value = sb.toString();
//
//		sb = new StringBuilder(new String(Base64.getEncoder().encode(value.getBytes())));
//
//		while (sb.charAt(sb.length() - 1) == '=') {
//			sb.deleteCharAt(sb.length() - 1);
//		}
//
//		return sb.toString();
//	}
//
//	/**
//	 * Sets a "cancel cookie" (with maxAge = 0) on the response to disable
//	 * persistent logins.
//	 *
//	 * @param request
//	 * @param response
//	 */
//	@Override
//	public void cancelCookie(String cookieName, HttpServletRequest request, HttpServletResponse response) {
//		logger.debug("Cancelling cookie");
//		Cookie cookie = new Cookie(cookieName, null);
//		cookie.setMaxAge(0);
//		cookie.setPath(getCookiePath(request));
//
//		response.addCookie(cookie);
//	}
//
//	/**
//	 * Sets the cookie on the response
//	 *
//	 * @param tokens
//	 *            the tokens which will be encoded to make the cookie value.
//	 * @param maxAge
//	 *            the value passed to {@link Cookie#setMaxAge(int)}
//	 * @param cookieName
//	 * @param request
//	 *            the request
//	 * @param response
//	 *            the response to add the cookie to.
//	 */
//	@Override
//	public void setCookie(String[] tokens, int maxAge, String cookieName, HttpServletRequest request,
//			HttpServletResponse response) {
//		response.setHeader("P3P", "CP=CAO PSA OUR");
//		String cookieValue = encodeCookie(tokens);
//		Cookie cookie = new Cookie(cookieName, cookieValue);
//		cookie.setMaxAge(maxAge);
//		cookie.setPath(getCookiePath(request));
//		cookie.setSecure(useSecureCookie);
//		response.addCookie(cookie);
//		getCookie(cookieName, request);
//	}
//
//	@Override
//	public String[] getAndDecodeCookie(String cookieName, HttpServletRequest request) {
//
//		String cookieValue = getCookie(cookieName, request);
//		if (cookieValue != null) {
//			return decodeCookie(cookieValue);
//		}
//		return null;
//	}
//
//	@Override
//	public String getCookie(String cookieName, HttpServletRequest request) {
//		Cookie[] cookies = request.getCookies();
//
//		if ((cookies == null) || (cookies.length == 0)) {
//			return null;
//		}
//
//		for (int i = 0; i < cookies.length; i++) {
//			if (cookieName.equals(cookies[i].getName())) {
//				return cookies[i].getValue();
//			}
//		}
//		return null;
//	}
//
//	private String getCookiePath(HttpServletRequest request) {
//		String contextPath = request.getContextPath();
//		return contextPath.length() > 0 ? contextPath : "/";
//	}
//
//	public void setCookieName(String cookieName) {
//		Assert.hasLength(cookieName, "Cookie name cannot be empty or null");
//	}
//
//}