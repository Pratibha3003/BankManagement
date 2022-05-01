package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer>  {

	List<Transaction> findByUserAcno(long acno);
	

	

	
}
