package com.rongshun.pojo.wechat;

import java.util.Date;

public class Orders {
    private Integer id;

    private String customer;

    private Double payable;

    private Double paid;

    private String status;

    private Date dd;

    private String addwho;

    private Date adddate;

    public Orders(Integer id, String customer, Double payable, Double paid, String status, Date dd, String addwho, Date adddate) {
        this.id = id;
        this.customer = customer;
        this.payable = payable;
        this.paid = paid;
        this.status = status;
        this.dd = dd;
        this.addwho = addwho;
        this.adddate = adddate;
    }

    public Orders() {
        super();
    }

    public Orders(String customer, String status) {
        this.customer = customer;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer == null ? null : customer.trim();
    }

    public Double getPayable() {
        return payable;
    }

    public void setPayable(Double payable) {
        this.payable = payable;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getDd() {
        return dd;
    }

    public void setDd(Date dd) {
        this.dd = dd;
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho == null ? null : addwho.trim();
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }
}