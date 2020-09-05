package com.example.helpapp.Data;
public class Company {

    private int id;
    private String email;
    private String company_name;
    private String ac_person;
    private String phone;
    private String street;
    private int house_nr;
    private String city;
    private int caps;
    private int gloves;
    private int goggles;
    private int masks;
    private int shoe_covers;
    private int suits;
    private int priority;

    public Company() {
    }

    public Company(int id, String email, String company_name, String ac_person,
                   String phone, String street, int house_nr, String city, int caps,
                   int gloves, int goggles, int masks, int shoe_covers, int suits, int priority) {
        this.id = id;
        this.email = email;
        this.company_name = company_name;
        this.ac_person = ac_person;
        this.phone = phone;
        this.street = street;
        this.house_nr = house_nr;
        this.city = city;
        this.caps = caps;
        this.gloves = gloves;
        this.goggles = goggles;
        this.masks = masks;
        this.shoe_covers = shoe_covers;
        this.suits = suits;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAc_person() {
        return ac_person;
    }

    public void setAc_person(String ac_person) {
        this.ac_person = ac_person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse_nr() {
        return house_nr;
    }

    public void setHouse_nr(int house_nr) {
        this.house_nr = house_nr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCaps() {
        return caps;
    }

    public void setCaps(int caps) {
        this.caps = caps;
    }

    public int getGloves() {
        return gloves;
    }

    public void setGloves(int gloves) {
        this.gloves = gloves;
    }

    public int getGoggles() {
        return goggles;
    }

    public void setGoggles(int goggles) {
        this.goggles = goggles;
    }

    public int getMasks() {
        return masks;
    }

    public void setMasks(int masks) {
        this.masks = masks;
    }

    public int getShoe_covers() {
        return shoe_covers;
    }

    public void setShoe_covers(int shoe_covers) {
        this.shoe_covers = shoe_covers;
    }

    public int getSuits() {
        return suits;
    }

    public void setSuits(int suits) {
        this.suits = suits;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
