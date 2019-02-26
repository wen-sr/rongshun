package com.rongshun.pojo.wechat;

import java.util.Date;

public class WeChatUserInfo {
    private Integer id;

    private String openid;

    private Integer subscribe;

    private Date subscribetime;

    private String nickname;

    private Integer sex;

    private String country;

    private String province;

    private String city;

    private String language;

    private String headimgurl;

    private String remark;

    private String groupid;

    private String loginId;

    private String auth;

    private String bk1;

    private String bk2;

    private String bk3;

    public WeChatUserInfo(Integer id, String openid, Integer subscribe, Date subscribetime, String nickname, Integer sex, String country, String province, String city, String language, String headimgurl, String remark, String groupid, String loginId, String auth, String bk1, String bk2, String bk3) {
        this.id = id;
        this.openid = openid;
        this.subscribe = subscribe;
        this.subscribetime = subscribetime;
        this.nickname = nickname;
        this.sex = sex;
        this.country = country;
        this.province = province;
        this.city = city;
        this.language = language;
        this.headimgurl = headimgurl;
        this.remark = remark;
        this.groupid = groupid;
        this.loginId = loginId;
        this.auth = auth;
        this.bk1 = bk1;
        this.bk2 = bk2;
        this.bk3 = bk3;
    }

    public WeChatUserInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Date getSubscribetime() {
        return subscribetime;
    }

    public void setSubscribetime(Date subscribetime) {
        this.subscribetime = subscribetime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId == null ? null : loginId.trim();
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth == null ? null : auth.trim();
    }

    public String getBk1() {
        return bk1;
    }

    public void setBk1(String bk1) {
        this.bk1 = bk1 == null ? null : bk1.trim();
    }

    public String getBk2() {
        return bk2;
    }

    public void setBk2(String bk2) {
        this.bk2 = bk2 == null ? null : bk2.trim();
    }

    public String getBk3() {
        return bk3;
    }

    public void setBk3(String bk3) {
        this.bk3 = bk3 == null ? null : bk3.trim();
    }
}