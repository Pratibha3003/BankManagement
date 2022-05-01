package com.example.demo.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Transaction {
	@GeneratedValue
	@Id
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="acno")
	@JsonIgnore
	private Users user;
	
	
	@Column(columnDefinition="bigint default 00")
	private long racno;
	
	private TransactionType trtype;
	private double amount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public long getRacno() {
		return racno;
	}

	public void setRacno(long racno) {
		this.racno = racno;
	}

	public TransactionType getTrtype() {
		return trtype;
	}

	public void setTrtype(TransactionType trtype) {
		this.trtype = trtype;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", user=" + user + ", racno=" + racno + ", trtype=" + trtype + ", amount="
				+ amount + ", time=" + time + "]";
	}

	
	
	
	
	

}
