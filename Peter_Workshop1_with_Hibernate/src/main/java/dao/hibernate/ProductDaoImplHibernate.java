package dao.hibernate;

import model_class.Account;
import model_class.AccountType;
import model_class.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.ProductDao;
import utility.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImplHibernate implements ProductDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProductDaoImplHibernate.class);

	public void createProduct(Product product) {
		GenericDao<Product> genericDao = new GenericDao<>(Product.class);
		genericDao.createObject(product);
	}

	@Override
	public Product readProductById(int id) {
		Product product = new Product();
		String query = "SELECT * FROM product WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			product.setName(resultSet.getString("name"));
			product.setId(resultSet.getInt("id"));
			product.setStock(resultSet.getInt("stock"));
			product.setPrice(resultSet.getBigDecimal("price"));
			LOG.info("ProudctInfo from product with id '" + id + "' read");
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public Product readProductByName(String name) {
		Product product = new Product();
		String query = "SELECT * FROM product WHERE name = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			product.setName(resultSet.getString("name"));
			product.setId(resultSet.getInt("id"));
			product.setStock(resultSet.getInt("stock"));
			product.setPrice(resultSet.getBigDecimal("price"));
			LOG.info("ProudctInfo from product with name '" + name + "' read");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public List<Product> readAllProducts() {
		List<Product> productList = new ArrayList<>();
		String query = "SELECT * FROM product";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setName(resultSet.getString("name"));
				product.setId(resultSet.getInt("id"));
				product.setStock(resultSet.getInt("stock"));
				product.setPrice(resultSet.getBigDecimal("price"));
				productList.add(product);
			}
			LOG.info("ProudctInfo of '" + productList.size()
					+ "' products read");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public void updateProduct(Product product) {
		GenericDao<Product> genericDao = new GenericDao<>(Product.class);
		genericDao.updateObject(product);
	}

	public void deleteProduct(int id) {
		GenericDao<Product> genericDao = new GenericDao<>(Product.class);
		genericDao.deleteObject(id);
	}

}
// public void printProduct() {
// try {
// Statement statement = Connect.getConnection().createStatement();
// ResultSet resultSet = statement.executeQuery("select * from product");
//
//
// ResultSetMetaData metaData = resultSet.getMetaData();
// for (int i = 1; i <= metaData.getColumnCount(); i++){
// System.out.printf("%-15s\t", metaData.getColumnName(i));
// }
// System.out.println();
//
// while (resultSet.next()) {
// for (int i = 1; i <= metaData.getColumnCount(); i++)
// System.out.printf("%-15s\t", resultSet.getObject(i));
// System.out.println();
// }
// statement.close();
// }
// catch (SQLException e) {
// e.printStackTrace();
// }
// }
// public void printProduct(int id) {
// String query = "SELECT * from product WHERE id = ?";
// try {
// PreparedStatement preparedStatement =
// Connect.getConnection().prepareStatement(query);
// preparedStatement.setInt(1, id);
// ResultSet resultSet = preparedStatement.executeQuery();
//
// ResultSetMetaData metaData = resultSet.getMetaData();
// for (int i = 1; i <= metaData.getColumnCount(); i++){
// System.out.printf("%-15s\t", metaData.getColumnName(i));
// }
// System.out.println();
//
// while (resultSet.next()) {
// for (int i = 1; i <= metaData.getColumnCount(); i++)
// System.out.printf("%-15s\t", resultSet.getObject(i));
// System.out.println();
// }
// preparedStatement.close();
// }
// catch (SQLException e) {
// e.printStackTrace();
// }
//
// }
// public void printProduct(String name){
// String query = "SELECT * from product WHERE name = ?";
// try {
// PreparedStatement preparedStatement =
// Connect.getConnection().prepareStatement(query);
// preparedStatement.setString(1, name);
// ResultSet resultSet = preparedStatement.executeQuery();
//
// ResultSetMetaData metaData = resultSet.getMetaData();
// for (int i = 1; i <= metaData.getColumnCount(); i++){
// System.out.printf("%-15s\t", metaData.getColumnName(i));
// }
// System.out.println();
//
// while (resultSet.next()) {
// for (int i = 1; i <= metaData.getColumnCount(); i++)
// System.out.printf("%-15s\t", resultSet.getObject(i));
// System.out.println();
// }
// preparedStatement.close();
// }
// catch (SQLException e) {
// e.printStackTrace();
// }
// }
// }
