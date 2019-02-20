package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import utility.DatabaseConnection;
import model_class.*;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.hibernate.Session;

public class DatabaseController {
	private TextIO textIO = TextIoFactory.getTextIO();
	TextTerminal<?> terminal = textIO.getTextTerminal();
	
	public void initDatabase() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistence");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Customer customer = new Customer();
		customer.setFirstname("peter");
		customer.setMiddlename("de");
		customer.setSurname("Graaf");
		
		AccountType accountType1 = new AccountType();
		accountType1.setDescription("Customer");
		
		AccountType accountType2 = new AccountType();
		accountType2.setDescription("Worker");
		
		AccountType accountType3 = new AccountType();
		accountType3.setDescription("Admin");
		
		Account account = new Account();
		account.setEmail("AdminAccount@email.com");
		account.setHash("sha1:64000:18:IAYKe6SfpC4B7SoMryabWYhtAjMjNG9x:M1sKuTZ01MaoxTx8vOHayGc2");
		
		account.setCustomer(customer);
		account.setAccountType(accountType3);
		customer.setAccount(account);
		
		entityManager.getTransaction().begin();
		entityManager.persist(accountType1);
		entityManager.persist(accountType2);
		entityManager.persist(accountType3);
		entityManager.persist(account);
		entityManager.persist(customer);
		entityManager.getTransaction().commit();
		
		
/*
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				Statement statement = connection.createStatement()) {
			statement.addBatch("INSERT INTO customer(id,firstname,middlename,surname) VALUES (1,'Peter','de','Graaf')");
			statement.addBatch("INSERT INTO customer(id,firstname,middlename,surname) VALUES (999,'Mathijs','de','Graaf')");
			statement.addBatch("INSERT INTO account_type (id, description) VALUES (1, 'Customer')");
			statement.addBatch("INSERT INTO account_type (id, description) VALUES (2, 'Worker')");
			statement.addBatch("INSERT INTO account_type (id, description) VALUES (3, 'Admin')");
			statement.addBatch("INSERT INTO account(email,hash,customer_id,account_type_id) VALUES ('AdminAccount@email.com','sha1:64000:18:IAYKe6SfpC4B7SoMryabWYhtAjMjNG9x:M1sKuTZ01MaoxTx8vOHayGc2',1,3)");
			statement.addBatch("INSERT INTO account(email,hash,customer_id,account_type_id) VALUES ('CustomerAccount@email.com','sha1:64000:18:IoD4nHCn8mQu/Z28nR2Y1mhvK2daEsuU:bqOXaIjMeSlbTq0y8PM66Nou',999,1)");
			statement.addBatch("INSERT INTO product (name,price,stock) VALUES ('Kolonisten van Catan',49.50,100)");
			statement.addBatch("INSERT INTO product (name,price,stock) VALUES ('Gloomhaven',199.99,5)");
			statement.addBatch("INSERT INTO product (name,price,stock) VALUES ('Spirit Island',79.50,25)");
			statement.addBatch("INSERT INTO product (name,price,stock) VALUES ('Ticket to Ride: Europe',40.00,80)");
			statement.addBatch("INSERT INTO product (name,price,stock) VALUES ('Assante',19.50,40)");
			statement.addBatch("INSERT INTO `order` (total_cost, customer_id, date) VALUES (10,1,CURRENT_TIMESTAMP)");
			statement.addBatch("INSERT INTO order_line (order_id,product_id,amount) VALUES (1,1,20)");
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
*/
}

	public void clearDatabase() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistence");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query foreignKeyChecksOff = entityManager.unwrap(Session.class).createSQLQuery("SET FOREIGN_KEY_CHECKS=0");
		Query foreignKeyChecksOn = entityManager.unwrap(Session.class).createSQLQuery("SET FOREIGN_KEY_CHECKS=1");
		Query deleteCustomer = entityManager.unwrap(Session.class).createSQLQuery("TRUNCATE customer");
		Query deleteAccount = entityManager.unwrap(Session.class).createSQLQuery("TRUNCATE account");
		Query deleteAccountType = entityManager.unwrap(Session.class).createSQLQuery("TRUNCATE accountType");

		entityManager.getTransaction().begin();
		
		foreignKeyChecksOff.executeUpdate();
		deleteCustomer.executeUpdate();
		deleteAccount.executeUpdate();
		deleteAccountType.executeUpdate();
		foreignKeyChecksOn.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		
		/*	try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				Statement statement = connection.createStatement()) {
			statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
			statement.addBatch("TRUNCATE `account`");
			statement.addBatch("TRUNCATE `account_type`");
			statement.addBatch("TRUNCATE `order_line`");
			statement.addBatch("TRUNCATE `order`");
			statement.addBatch("TRUNCATE `customer`");
			statement.addBatch("TRUNCATE `address`");
			statement.addBatch("TRUNCATE `address_type`");
			statement.addBatch("TRUNCATE `product`");
			statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
*/	
}

}
