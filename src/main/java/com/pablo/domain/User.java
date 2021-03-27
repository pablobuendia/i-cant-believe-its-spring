package com.pablo.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
// NamedQuery: is a way to provide alias names to one or more queries, which are static in nature.
// NamedQuery allows queries to be segregated at one place and at one place and accessed later via
// alias names. They are parsed/unparsed once. NamedQueries is used to group multiple instances of
// @NamedQuery
@NamedQueries({@NamedQuery(name = "findByEmail", query = "from User u where u.email = :email"),
    @NamedQuery(name = "findByEmailAndPassword",
        query = "from User u where u.email = :email and u.password = :password"),})
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String email;
  private String password;
  @Column(name = "first_name")
  private String firstname;
  @Column(name = "last_name")
  private String lastname;
  private int age;
  private int gender;
  @Column(name = "contact_number")
  private String contactNumber;
  @Column(name = "alternate_contact_number")
  private String alternateContactNumber;
  private String address;
  @Column(name = "city_code")
  private String cityCode;
  @Column(name = "state_code")
  private String stateCode;
  @Column(name = "country_mode")
  private String countryCode;
  @Column(name = "create_time")
  private Timestamp createTime;
  @Column(name = "last_updated")
  private Timestamp lastUpdated;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public String getAlternateContactNumber() {
    return alternateContactNumber;
  }

  public void setAlternateContactNumber(String alternateContactNumber) {
    this.alternateContactNumber = alternateContactNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getStateCode() {
    return stateCode;
  }

  public void setStateCode(String stateCode) {
    this.stateCode = stateCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Timestamp lastUpdated) {
    this.lastUpdated = lastUpdated;
  }


}
