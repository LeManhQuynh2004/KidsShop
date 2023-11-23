package fpoly.group6_pro1122.kidsshop.Model;

import java.io.Serializable;

public class Shipment implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String date;
    private String city;
    private String district;
    private String address;
    private int status;
    private int address_type;
    private int user_id;

    public Shipment() {
    }

    public Shipment(int id,String name,String phone, String date, String city, String district, String address, int status, int address_type, int user_id) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.city = city;
        this.district = district;
        this.address = address;
        this.status = status;
        this.address_type = address_type;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAddress_type() {
        return address_type;
    }

    public void setAddress_type(int address_type) {
        this.address_type = address_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
