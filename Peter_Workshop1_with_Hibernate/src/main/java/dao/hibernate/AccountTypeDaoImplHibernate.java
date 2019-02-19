package dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model_class.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.AccountTypeDao;
import utility.DatabaseConnection;

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

// public void printAccountType() {
// try {
// Statement statement = Connect.getConnection().createStatement();
// ResultSet resultSet =
// statement.executeQuery("SELECT id, description FROM accounttype");
// ResultSetMetaData metaData = resultSet.getMetaData();
//
// for (int i = 1; i <= metaData.getColumnCount(); i++){
// System.out.printf("%-12s\t", metaData.getColumnName(i));
// }
// System.out.println();
//
// while (resultSet.next()) {
// for (int i = 1; i <= metaData.getColumnCount(); i++)
// System.out.printf("%-12s\t", resultSet.getObject(i));
// System.out.println();
// }
// statement.close();
// }
// catch (SQLException e) {
// e.printStackTrace();
// }
// }
// public void printAccountType(int id) {
// String query = "SELECT id, description FROM accounttype WHERE id = ?";
// try {
// PreparedStatement preparedStatement =
// Connect.getConnection().prepareStatement(query);
// preparedStatement.setInt(1, id);
// ResultSet resultSet = preparedStatement.executeQuery();
//
// ResultSetMetaData metaData = resultSet.getMetaData();
// for (int i = 1; i <= metaData.getColumnCount(); i++){
// System.out.printf("%-20s\t", metaData.getColumnName(i));
// }
// System.out.println();
//
// while (resultSet.next()) {
// for (int i = 1; i <= metaData.getColumnCount(); i++)
// System.out.printf("%-20s\t", resultSet.getObject(i));
// System.out.println();
// }
// preparedStatement.close();
// }
// catch (SQLException e) {
// e.printStackTrace();
// }
// }
//
