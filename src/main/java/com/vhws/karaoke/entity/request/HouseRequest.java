package com.vhws.karaoke.entity.request;

public class HouseRequest {
    private String houseName;
    private String cnpj;
    private String phone;
    private String address;

    public HouseRequest() {
    }

    public HouseRequest(String houseName, String cnpj, String phone, String address) {
        this.houseName = houseName;
        this.cnpj = cnpj;
        this.phone = phone;
        this.address = address;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
