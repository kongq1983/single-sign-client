package com.kq.om.platform.dto;

/**
 * @author kq
 * @date 2021-05-08 10:39
 * @since 2020-0630
 */
public class CertAuthDto {

    private String oauthCode;
    private String oauthSession;
    private String token;

    public String getOauthCode() {
        return oauthCode;
    }

    public void setOauthCode(String oauthCode) {
        this.oauthCode = oauthCode;
    }

    public String getOauthSession() {
        return oauthSession;
    }

    public void setOauthSession(String oauthSession) {
        this.oauthSession = oauthSession;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CertAuthDto{" +
                "oauthCode='" + oauthCode + '\'' +
                ", oauthSession='" + oauthSession + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
