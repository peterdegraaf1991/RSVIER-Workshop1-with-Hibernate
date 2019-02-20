package dao.hibernate;

import java.util.List;

import model_class.Account;
import model_class.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.AccountDao;


public class AccountDaoImplHibernate extends GenericDao<Account> implements AccountDao{
	private static final Logger LOG = LoggerFactory
			.getLogger(AccountDaoImplHibernate.class);

	@Override	
	public int createAccount(Account account) {
	GenericDao<Account> genericDao = new GenericDao<>(Account.class);
	Account createdAccount = genericDao.createObject(account);
	return createdAccount.getId();
	}
	
	@Override
	public void updateAccount(Account account) {
		GenericDao<Account> genericDao = new GenericDao<>(Account.class);
		genericDao.updateObject(account);
	}

	@Override
	public void deleteAccount(int id) {
		GenericDao<Account> genericDao = new GenericDao<>(Account.class);
		genericDao.deleteObject(id);
	}

	@Override
	public Account readAccountById(int id) {
		GenericDao<Account> genericDao = new GenericDao<>(Account.class);
		Account readAccount = genericDao.readObjectById(id);
		return readAccount;
		}

	@Override
	public Account readAccountByEmail(String email) {
	GenericDao<Account> genericDao = new GenericDao<>(Account.class);
	Account readAccount = genericDao.readSingleObjectByColumn("email", email);
	return readAccount;
	}

	@Override
	public Account readAccountByCustomerId(int id) {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		Customer readCustomer = genericDao.readObjectById(id);
		return readCustomer.getAccount();
	}

	@Override
	public String readHash(int id) {
		Account readAccount = readAccountById(id);
		return readAccount.getHash();
	}

	@Override
	public List<Account> readAllAccounts() {
		GenericDao<Account> genericDao = new GenericDao<>(Account.class);
		List<Account> accountList = genericDao.readAllObjects(Account.class);
		return accountList;
	}
}