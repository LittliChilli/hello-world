package com.shop.service.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieHandlingService {

    /**
     * Sets a "cancel cookie" (with maxAge = 0) on the response to disable
     * persistent logins.
     *
     * @param request
     * @param response
     */
    public void cancelCookie(String cookieName, HttpServletRequest request, HttpServletResponse response);

    /**
     * Sets the cookie on the response
     *
     * @param tokens     the tokens which will be encoded to make the cookie value.
     * @param maxAge     the value passed to {@link Cookie#setMaxAge(int)}
     * @param cookieName
     * @param request    the request
     * @param response   the response to add the cookie to.
     */
    public void setCookie(String[] tokens, int maxAge, String cookieName, HttpServletRequest request,
                          HttpServletResponse response);

    /**
     * @param cookieName
     * @param request
     * @return
     */
    public String[] getAndDecodeCookie(String cookieName, HttpServletRequest request);

    /**
     * @param cookieName
     * @param request
     * @return
     */
    public String getCookie(String cookieName, HttpServletRequest request);

    /**
     * Inverse operation of decodeCookie.
     *
     * @param cookieTokens the tokens to be encoded.
     * @return base64 encoding of the tokens concatenated with the ":"
     * delimiter.
     */
    public String encodeCookie(String[] cookieTokens);

}
