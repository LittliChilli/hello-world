package com.shop.front.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 验证码cookie
 */
public class CookieLoginFilter implements Filter {

	protected FilterConfig filterConfig = null;

	private String redirectURL = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		redirectURL = filterConfig.getInitParameter("redirectURL");
		initCaptchaService(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		chain.doFilter(request, response);
	}

	/**
	 * 从ApplicatonContext获取CaptchaService实例.
	 */
	protected void initCaptchaService(final FilterConfig fConfig) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
		// cookieHandlingService = (CookieHandlingService)
		// context.getBean("cookieHandlingService");
		// merchantSiteService = (MerchantSiteService)
		// context.getBean("merchantSiteService");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	// private static final Logger logger =
	// LoggerFactory.getLogger(CookieLoginFilter.class);
	//
	// @Autowired
	// private CookieHandlingService cookieHandlingService;
	//
	// private final String cookieName = "qudao";
	//
	// protected FilterConfig filterConfig = null;
	//
	// private String redirectURL = null;
	//
	// public final int BCOOKIE_MAX_AGE = 14 * 24 * 60 * 60;
	//
	// @Override
	// public void doFilter(ServletRequest req, ServletResponse res,
	// FilterChain chain) throws IOException, ServletException {
	// HttpServletRequest request = (HttpServletRequest) req;
	// HttpServletResponse response = (HttpServletResponse) res;
	// HttpSession session = request.getSession();
	// //针对IE的cookie机制
	// response.addHeader("P3P","CP=CAO PSA OUR");
	//
	// CSRFTokenManager.getTokenForSession(request.getSession());
	//
	// try {
	// saveChannel2Cookie(request, response);
	// } catch (Exception e) {
	// logger.error("save channel 2 cookie error",e);
	// }
	//
	// // cps_website 来源
	// cpsWebisteSource(request, response);
	//
	// // 三方来源判断统计
	// statsticSource(request, response);
	//
	// // 电脑版和手机版切换cookie
	// changeMobileWebsiteClient(request,response);
	//
	// //广告投放
	// saveAdCookie(request,response);
	//
	// if (session != null) {
	// String sessionSeries =
	// (String)session.getAttribute(Sessions.SESSION_LOGINSERIES.value());
	// if(sessionSeries == null){
	// String randomNumber = RandomStringUtils.randomNumeric(10); //登录验证随机码
	// session.setAttribute(Sessions.SESSION_LOGINSERIES.value(),
	// DigestUtil.hash(randomNumber));
	// }
	//
	// User user = (User)session.getAttribute(Sessions.SESSION_USER.value());
	//
	// Merchant merchant =
	// (Merchant)session.getAttribute(Sessions.SESSION_MERCHANT.value());
	//
	// if (user == null) {
	// user = cookieHandlingService.autoLogin(request, response);
	// if (user != null) {
	// logger.info("Cookie login for user {} from ip {}",
	// user.getId(), UserTools.getRemortIP(request));
	// request.getSession().setAttribute(
	// Sessions.SESSION_USER.value(), user);
	// request.getSession().setAttribute(
	// CommonConstants.SESSION_COOKIE_FLAG,
	// CommonConstants.SESSION_COOKIE_FLAG_COOKIE);
	// }
	// }
	// if(user != null){
	// if(merchant == null){
	// merchant = merchantSiteService.getMerchantInfo(user.getId(), true);
	// }
	// if(merchant != null){
	// request.getSession().setAttribute(CommonConstants.SESSION_MERCHANT,
	// merchant);
	// }
	// }
	// }
	// chain.doFilter(request, response);
	// }
	//
	// /**
	// * cps_website 保存来源
	// *
	// * @param request
	// * @param response
	// */
	// private void cpsWebisteSource(HttpServletRequest request,
	// HttpServletResponse response) {
	// String tks = request.getParameter("tks");
	// String tks_token[] = null;
	// if (StringUtils.isNotBlank(tks)) {
	// tks_token = new String[] { tks };
	// }
	// if (null != tks_token) {
	// cookieHandlingService.setCookie(tks_token, 24 * 3600,
	// "tks_source", request, response);
	// }
	// }
	//
	// /**
	// * 三方来源统计
	// *
	// * @param request
	// * @param response
	// */
	// private void statsticSource(HttpServletRequest request,
	// HttpServletResponse response) {
	// String referer = request.getHeader("Referer");
	// String frm = request.getParameter("frm"); // 渠道
	// String login = request.getParameter("login"); // 指定登录方式
	// String token[] = null;
	// /*
	// * if ("alipay".equals(request.getParameter("s"))) {
	// * request.setAttribute("referer", "alipay"); token = new String[] {
	// * "alipay" }; }
	// */
	// if (StringUtils.isNotBlank(frm)) {
	// if ("doudou".equalsIgnoreCase(frm)) {
	// String annalID = request.getParameter("annalID");
	// if (StringUtils.isNotBlank(annalID)) {
	//// token = new String[] { frm + "_" + annalID };
	// request.getSession().setAttribute(CommonConstants.SESSION_DOUDOU_SOURCE,
	// frm + "_" + annalID);
	// }
	// }else
	// token = new String[] { frm };
	// }
	//
	// if (StringUtils.isNotBlank(login)) {
	// request.setAttribute("login", true);
	// request.setAttribute("referer", login);
	// }
	// if (StringUtils.isNotBlank(referer)) {
	//
	// if (StringUtils.isBlank(frm)) {
	// token = new String[] { referer };
	// }
	// if (referer.contains("guang.com")) {
	// request.setAttribute("referer", "guang.com");
	// }else if (referer.contains("qq.com") || referer.contains("qzone")) {
	// if (StringUtils.isBlank(login)) {
	// request.setAttribute("referer", "qq");
	// }
	// } else if (referer.contains("taobao.com")
	// || referer.contains("tmall.com")) {
	// if (StringUtils.isBlank(login)) {
	// request.setAttribute("referer", "taobao");
	// }
	// } else if (referer.contains("alipay.com")) {
	// if (StringUtils.isBlank(login)) {
	// request.setAttribute("referer", "alipay");
	// }
	// } else if (referer.contains("weibo.com")) {
	// if (StringUtils.isBlank(login)) {
	// request.setAttribute("referer", "weibo");
	// }
	// }else {
	// request.setAttribute("referer", "qq");
	// }
	// }else{
	// request.setAttribute("referer", "guang.com");
	// }
	//
	// String[] first_from_type = cookieHandlingService.getAndDecodeCookie(
	// "firstFromType", request);
	// // 根据来源 设置cookie，后面做来源统计
	// // if ((null == from_type || null == from_type[0]) && null != token)
	// if (null != token && !token[0].equals("guang.com")) {
	// cookieHandlingService.setCookie(token, 24 * 3600,
	// "fromType", request, response);
	//
	// }
	// if (null != token
	// && (null == first_from_type || null == first_from_type[0])) {
	// cookieHandlingService.setCookie(token, 30 * 24 * 3600,
	// "firstFromType", request, response);
	// }
	// }
	//
	// private void changeMobileWebsiteClient(HttpServletRequest request,
	// HttpServletResponse response){
	// String ver = request.getParameter("ver");
	// if(StringUtil.isNotBlank(ver)){
	// cookieHandlingService.setCookie(new String[]{ ver }, 24 * 3600,
	// "mobileWebsiteClient", request, response);
	// }
	// }
	//
	// @Override
	// public void destroy() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void init(FilterConfig fConfig) throws ServletException {
	// // TODO Auto-generated method stub
	// this.filterConfig = fConfig;
	// redirectURL = filterConfig.getInitParameter("redirectURL");
	// initCaptchaService(fConfig);
	// }
	//
	// /**
	// * 从ApplicatonContext获取CaptchaService实例.
	// */
	// protected void initCaptchaService(final FilterConfig fConfig) {
	// ApplicationContext context = WebApplicationContextUtils
	// .getWebApplicationContext(fConfig.getServletContext());
	// cookieHandlingService = (CookieHandlingService) context
	// .getBean("cookieHandlingService");
	// merchantSiteService = (MerchantSiteService)
	// context.getBean("merchantSiteService");
	// }
	//
	// /**
	// * 保存渠道值到cookie (cpn & isg)2个参数，分别记录渠道和是否是内部渠道
	// *
	// * @param request
	// * @param response
	// */
	// public void saveChannel2Cookie(HttpServletRequest request,
	// HttpServletResponse response){
	//
	// String channel = request.getParameter("cpn");
	// String inner_ch = request.getParameter("isg");
	// String [] token = new String[2];
	// if(StringUtil.isNotBlank(channel)){
	// token[0] = channel;
	// if(StringUtil.isNotBlank(inner_ch)){
	// token[1] = inner_ch;
	// }else{
	// token[1] = "";
	// }
	// cookieHandlingService.setCookie(token, BCOOKIE_MAX_AGE, cookieName,
	// request, response);
	// }else{
	// String [] _channel = cookieHandlingService.getAndDecodeCookie(cookieName,
	// request);
	// if(null == _channel){
	// token[0] = "guang";
	// token[1] = "guang";
	// cookieHandlingService.setCookie(token,
	// BCOOKIE_MAX_AGE,cookieName,request,response);
	// }
	// }
	// }
	//
	// public void saveAdCookie(HttpServletRequest request,
	// HttpServletResponse response){
	// String[] ad_flag = cookieHandlingService.getAndDecodeCookie(
	// "ad_flag", request);
	// if(ad_flag == null){
	// request.setAttribute("ad_flag", true);
	// cookieHandlingService.setCookie(new String[]{"true"}, 6 * 60 * 60,
	// "ad_flag", request, response);
	// }else{
	// request.setAttribute("ad_flag", false);
	// }
	//
	// }

}
