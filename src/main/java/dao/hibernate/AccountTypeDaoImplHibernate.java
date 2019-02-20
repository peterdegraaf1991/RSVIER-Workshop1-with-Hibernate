package dao.hibernate;

import model_class.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dao.AccountTypeDao;

public class AccountTypeDaoImplHibernate implements AccountTypeDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(AccountTypeDaoImplHibernate.class);

	@Override
	public void createAccountType(AccountType accountType) {
			GenericDao<AccountType> genericDao = new GenericDao<>(AccountType.class);
			genericDao.createObject(accountType);
			}

	@Override
	public void updateAccountType(AccountType accountType) {
		GenericDao<AccountType> genericDao = new GenericDao<>(AccountType.class);
		genericDao.updateObject(accountType);
	}

	@Override
	public void deleteAccountType(int id) {
		GenericDao<AccountType> genericDao = new GenericDao<>(AccountType.class);
		genericDao.deleteObject(id);
	}

	@Override
	public AccountType readAccountType(int id) {
		GenericDao<AccountType> genericDao = new GenericDao<>(AccountType.class);
		AccountType readAccountType = genericDao.readObjectById(id);
		return readAccountType;

	}
}
