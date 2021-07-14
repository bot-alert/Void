package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.sun.istack.NotNull;

@Entity
@Table(name = "user",schema = "blogsite")
public class User_entity {
@NotNull
private String username;
@NotNull
private String password;
@NotNull
private String uudi;
@NotNull
private long phonenumber;
@NotNull
private String email;
@NotNull
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long userid;
@NotNull
private String dateCreated;

public User_entity(String username, String password, String uudi, long phonenumber, String email, long userid,
		String dateCreated) {
	super();
	this.username = username;
	this.password = password;
	this.uudi = uudi;
	this.phonenumber = phonenumber;
	this.email = email;
	this.userid = userid;
	this.dateCreated = dateCreated;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getUudi() {
	return uudi;
}

public void setUudi(String uudi) {
	this.uudi = uudi;
}

public long getPhonenumber() {
	return phonenumber;
}

public void setPhonenumber(long phonenumber) {
	this.phonenumber = phonenumber;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public long getUserid() {
	return userid;
}

public void setUserid(long userid) {
	this.userid = userid;
}

public String getDateCreated() {
	return dateCreated;
}

public void setDateCreated(String dateCreated) {
	this.dateCreated = dateCreated;
}



}