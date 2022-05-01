package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionType;
import com.example.demo.entity.Users;
import com.example.demo.error.InvalidException;
import com.example.demo.error.LowBalanceException;
import com.example.demo.error.NoTransactionException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.repository.TransactionRepo;
import com.example.demo.repository.UserRepo;

@Service
public class BankService implements UserDetailsService{
	
	@Autowired
	UserRepo repo;
	
	@Autowired
	TransactionRepo trepo;

	@Autowired
	PasswordEncoder passencoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user=repo.findByUsername(username);
		if(user==null)
			throw new UsernameNotFoundException("User Name Not Found.....!");		
		return new BankPrinciple(user);
	}

	
	public Users saveUser(Users user) {
		user.setPassword(passencoder.encode(user.getPassword()));
		
		return repo.save(user);
	}

	public String removeUser(long acno) throws UserNotFoundException 
	{
		if(!repo.existsById(acno)) 
			
			throw new UserNotFoundException("Username not found.....!");	
		
		else
		
			repo.deleteById(acno);
		
		return "Record Deleted SuccessFully.....!";
	}

	
	public  Users updateUser(long acno, Users user) throws UserNotFoundException {
		Users u;
		
		if(repo.existsByAcno(acno)) {
			
			u = repo.findByAcno(acno);
			
			if(Objects.nonNull(user.getAddress()) || !"".equals(user.getAddress()))
				u.setAddress(user.getAddress());
			
			if(Objects.nonNull(user.getCellno()))
				u.setCellno(user.getCellno());
			
			if(Objects.nonNull(user.getEmail()) || !"".equals(user.getEmail()))
				u.setEmail(user.getEmail());
			
			return repo.save(u);
		}
		else
			
			throw new UserNotFoundException("Username not Found!!");
	}

	public List<Users> getUsers() 
	{
		return repo.findAll();
	}

	public Users getUser(long acno) 
	{
		return repo.findByAcno(acno);
	}

	public List<Transaction> getTrans(int n) throws NoTransactionException {
		List<Transaction> tr = trepo.findAll();
		List<Transaction> res = new ArrayList<>();
		if(tr.size()==0)
			throw new NoTransactionException(" no trancation found "); 
		else {
		for(int i = 0; i < n && i<tr.size(); i++) {
			res.add(tr.get(i));
		}
		return trepo.findAll();
		}
			
	}

	public Users updateUserPass(long acno,Users user) {
		Users u = repo.findByAcno(acno);
		if(Objects.nonNull(user.getUsername()) || !"".equals(user.getUsername()))
			u.setUsername(user.getUsername());
		if(Objects.nonNull(user.getPassword()) || !"".equals(user.getPassword()))
			u.setPassword(passencoder.encode(user.getPassword()));
		return repo.save(u);
	}

	public List<Transaction> getTransactions(long acno, int n) throws NoTransactionException {
		
		List<Transaction> tr=trepo.findByUserAcno(acno);
		List<Transaction> res=new ArrayList<>();
		if(tr.size()==0)
			throw new NoTransactionException("Transaction Not Found");
		else
		{
			for(int i=0;i<n && i<tr.size();i++)
			{
				res.add(tr.get(i));
			}
			
		return trepo.findByUserAcno(acno);
		}
	}

	public String displayBal(long acno) {
		Users user = repo.findByAcno(acno);
		return "Your account balance is Rs."+user.getBalance();
	}

	public String transfer(double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	public String depwit(long acno, Transaction tr) throws LowBalanceException, UserNotFoundException, InvalidException {
		Users user = repo.findByAcno(acno);
		double amount, amt;
		
		if(tr.getTrtype().equals(TransactionType.DEPOSIT)) {
			amount = user.getBalance() + tr.getAmount();
			user.setBalance(amount);
			trepo.save(tr);
		}
		
		else 
			if(tr.getTrtype().equals(TransactionType.WITHDRAW)) {
			
				if(user.getBalance()<tr.getAmount()) {
				
					throw new LowBalanceException("Your account balance is low");
			}
			amount = user.getBalance() - tr.getAmount();
			user.setBalance(amount);
			trepo.save(tr);
		}
		else 
			if(tr.getTrtype().equals(TransactionType.TRANSFER)){
			if(!repo.existsByAcno(tr.getRacno())) {
				throw new UserNotFoundException("Username not found");
			}
			else {
				Users reciever = repo.findByAcno(tr.getRacno());
				if(user.getBalance()<tr.getAmount()) {
					throw new LowBalanceException("Your account balance is low");
				}
				amount = user.getBalance() - tr.getAmount();
				user.setBalance(amount);
				amt = reciever.getBalance() + tr.getAmount();
				reciever.setBalance(amt);
				repo.save(reciever);
				repo.save(user);
				trepo.save(tr);
			}
		}
		else {
			throw new InvalidException("Invalid transaction type");
		}
		return "Transaction Successful";
	}
	
	

}
