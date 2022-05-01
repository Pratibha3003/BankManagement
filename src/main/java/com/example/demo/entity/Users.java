package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(name="seq", initialValue=10000000)
public class Users {
	
	
	@Length(min=3, message="Name Can not less than three charactor")
	private String name;
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@Column(unique=true)
	@Id
	private long acno;
	
	@Length(min=10,max=13,message="cell no must be of 10 charector")
	@Column(unique=true)
	private String cellno;

	@Email
	@Column(unique=true)
	private String email;
	
	@Length(min=3, message="Address can not less than three charector")
	private String address;
	
	@Length(min=10,max=10,message="cell no must be of 10 charector")
	@Column(unique=true)
	private String pan;
	
	@Length(min=12,max=12, message="Adhr must be be of 18 charector.")
	@Column(unique=true)
	private String adhaar;
	
	@Column(columnDefinition=" double default 10000.00")
	private double balance=10000;
	
	@Column(unique=true)
	private String username;
	private String password;
	
	
	@OneToMany(cascade=CascadeType.ALL)
	
	@JoinColumn(name="acno")
	private List<Transaction> transactions = new ArrayList<>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAcno() {
		return acno;
	}
	public void setAcno(long acno) {
		this.acno = acno;
	}
	public String getCellno() {
		return cellno;
	}
	public void setCellno(String cellno) {
		this.cellno = cellno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getAdhaar() {
		return adhaar;
	}
	public void setAdhaar(String adhaar) {
		this.adhaar = adhaar;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
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
	@Override
	public String toString() {
		return "Users [name=" + name + ", acno=" + acno + ", cellno=" + cellno + ", email=" + email
				+ ", address=" + address + ", pan=" + pan + ", adhaar=" + adhaar + ", balance=" + balance
				+ ", username=" + username + ", password=" + password + "]";
	}
	
	
	
	

}
