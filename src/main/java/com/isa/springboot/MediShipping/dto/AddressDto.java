package com.isa.springboot.MediShipping.dto;

public class AddressDto {
    private long id;
    private String country;
    private String city;
    private String streetName;
    private int number;

    public AddressDto(long id, String country, String city, String streetName, int number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
