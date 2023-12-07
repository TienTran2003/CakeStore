package poly.store.service;

import java.util.List;

import javassist.NotFoundException;
import poly.store.entity.Account;

public interface AccountService {
	public List<Account> findAll() ;
	public Account findById(String username) ;
	public List<Account> getAdministrators() ;

	Account create(Account account) throws NotFoundException;

	Account update(Account account);

	void delete(String username);

	Account save(Account account);
}
