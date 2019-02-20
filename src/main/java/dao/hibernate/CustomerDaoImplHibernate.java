package dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.CustomerDao;
import utility.DatabaseConnection;
import model_class.Account;
import model_class.AccountType;
import model_class.Customer;

public class CustomerDaoImplHibernate implements CustomerDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(CustomerDaoImplHibernate.class);

	@Override
	public void createCustomer(Customer customer) {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		genericDao.createObject(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		genericDao.updateObject(customer);
	}

	@Override
	public void deleteCustomer(int id) {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		genericDao.deleteObject(id);
	}
	
	@Override
	public Customer readCustomerById(int id) {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		Customer readCustomer = genericDao.readObjectById(id);
		return readCustomer;
		}

	@Override
	public List<Customer> readCustomersByLastname(String lastname) {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		List<Customer> readCustomerList = genericDao.readObjectListByColumn("lastname",lastname);
		return readCustomerList;
	}

	// Wil je normaal niet gebruiken, volgens mij wordt deze ook nooit aangesproken.
	@Override
	public void deleteCustomer(String lastname) {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		genericDao.deleteObjectByColumn("lastname", lastname);
	}

	public int CustomerNameExists(Customer customer) {
		String query = "SELECT id FROM customer WHERE firstname=? AND middlename = ? AND surname = ?";
		int rowCount = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setString(1, customer.getFirstname());
			preparedStatement.setString(2, customer.getMiddlename());
			preparedStatement.setString(3, customer.getSurname());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
				rowCount++;
			System.out.println("Rows Found = " + rowCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	@Override
	public List<Customer> readAllCustomers() {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		List<Customer> customerList = genericDao.readAllObjects(Customer.class);
		return customerList;
	}
}
