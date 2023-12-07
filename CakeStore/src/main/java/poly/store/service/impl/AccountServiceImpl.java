package poly.store.service.impl;

import java.util.List;
import java.util.Optional;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import poly.store.dao.AccountDAO;
import poly.store.entity.Account;
import poly.store.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	AccountDAO dao;

	@Autowired
	BCryptPasswordEncoder password;
	
	public List<Account> findAll() {
		return dao.findAll();
	}

	public Account findById(String username) {
		return dao.findById(username).get();
	}

	public List<Account> getAdministrators() {
		return dao.getAdministrators();
	}

	public void delete(String username) {
		dao.deleteById(username);
	}

	@Override
	public Account save(Account account) {
		Optional<Account> optionalAccount = dao.findById(account.getUsername());
		if (optionalAccount.isPresent()) {
			Account existingAccount = optionalAccount.get();
			existingAccount.setPassword(account.getPassword());
			return dao.save(existingAccount);
		}
		return null;
	}

	@Override
	public Account create(Account account) throws NotFoundException {
		Optional<Account> result = dao.findById(account.getUsername());
		if(result.isPresent()){
			throw new NotFoundException("Account already exist!!!");
		}else{
			Account accounts = new Account();
			accounts.setUsername(account.getUsername());
			accounts.setPassword(password.encode(account.getPassword()));
			accounts.setEmail(account.getEmail());
			accounts.setFullname(account.getFullname());
			return dao.save(accounts);
		}
	}

	@Override
	public Account update(Account account) {
		Optional<Account> optionalAccount = dao.findById(account.getUsername());
		if (optionalAccount.isPresent()) {
			Account existingAccount = optionalAccount.get();
			existingAccount.setEmail(account.getEmail());
			existingAccount.setFullname(account.getFullname());
			existingAccount.setPhone(account.getPhone());
			existingAccount.setAddress(account.getAddress());
			return dao.save(existingAccount);
		}
		return null;
	}



}
