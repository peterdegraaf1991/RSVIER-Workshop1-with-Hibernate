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
	
	//Fiddling around with SQL JOIN Queries
	@Override
	public Customer readCustomerById(int id) {
		Customer customer = new Customer();
		String query = "SELECT * FROM customer LEFT JOIN account ON customer.id = account.customer_id LEFT JOIN account_type ON account.account_type_id = account_type.id WHERE customer.id = ? ";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			customer.setId(resultSet.getInt("id"));
			customer.setFirstname(resultSet.getString("firstname"));
			customer.setMiddlename(resultSet.getString("middlename"));
			customer.setSurname(resultSet.getString("surname"));
			customer.setAccountDescription(resultSet.getString("description"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	//Fiddling around with SQL JOIN Queries
	@Override
	public ArrayList<Customer> readCustomersByLastname(String lastname) {
		ArrayList<Customer> listOfCustomers = new ArrayList<>();
		String query = "SELECT * FROM customer LEFT JOIN account ON customer.id = account.customer_id LEFT JOIN account_type ON account.account_type_id = account_type.id WHERE customer.surname = ? ";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setString(1, lastname);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getInt("id"));
				customer.setFirstname(resultSet.getString("firstname"));
				customer.setMiddlename(resultSet.getString("middlename"));
				customer.setSurname(resultSet.getString("surname"));
				customer.setAccountDescription(resultSet.getString("description"));
				listOfCustomers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfCustomers;
	}

	@Override
	public void deleteCustomer(String lastname) {
		String query = "DELETE FROM customer WHERE surname = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, lastname);
			preparedStatement.executeUpdate();
			LOG.info("Person successfully removed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	//Fiddling around with SQL JOIN Queries
	@Override
	public List<Customer> readAllCustomers() {
		GenericDao<Customer> genericDao = new GenericDao<>(Customer.class);
		List<Customer> customerList = genericDao.readAllObjects(Customer.class);
		return customerList;
	}
}
