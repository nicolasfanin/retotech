package com.nicolasfanin.retotech.domain.model;

public class ClientModel {

    private String name;
    private String surname;
    private int age;
    private String birthDate;

    public ClientModel(String name, String surname, int age, String birthDate) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
