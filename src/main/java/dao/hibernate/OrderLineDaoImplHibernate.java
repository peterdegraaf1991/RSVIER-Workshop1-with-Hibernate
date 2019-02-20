package dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.OrderLineDao;
import dao.ProductDao;
import utility.DatabaseConnection;
import model_class.Account;
import model_class.AccountType;
import model_class.OrderLine;
import model_class.Product;

public class OrderLineDaoImplHibernate implements OrderLineDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(OrderLineDaoImplHibernate.class);

	@Override
	public void createOrderLine(OrderLine orderLine) {
		GenericDao<OrderLine> genericDao = new GenericDao<>(OrderLine.class);
		genericDao.createObject(orderLine);
	}

	@Override
	public OrderLine readOrderLineById(int id) {
		OrderLine orderLine = new OrderLine();
		String query = "SELECT * FROM order_line WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			orderLine.setId(resultSet.getInt("id"));
			orderLine.setAmount(resultSet.getInt("amount"));
			orderLine.setOrderId(resultSet.getInt("order_id"));
			ProductDao productDaoImpl = new ProductDaoImplHibernate();
			// uses resultSet from readOrderLine or readProduct?
			Product product = productDaoImpl.readProductById(resultSet
					.getInt("product_id"));
			orderLine.setProduct(product);
			LOG.info("ProductLine with id '" + id + "' read");
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return orderLine;
	}

	@Override
	public List<OrderLine> readOrderLinesOfOrderId(int id) {
		List<OrderLine> orderLineList = new ArrayList<>();
		String query = "SELECT * FROM order_line WHERE order_id = ?";
		int i = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LOG.info("BeforeError1 (1/3)");
				OrderLine orderLine = new OrderLine();
				orderLine.setId(resultSet.getInt("id"));
				orderLine.setAmount(resultSet.getInt("amount"));
				orderLine.setOrderId(resultSet.getInt("order_id"));
				Product product = new Product();
				product.setId(resultSet.getInt("product_id"));
				orderLine.setProduct(product);
				orderLineList.add(orderLine);
				LOG.info("BeforeError1 (2/3)");
				i = i + 1;
				LOG.info("i =" + i);
			}
			LOG.info("AfterError1 (3/3)");
			LOG.info("OrderLineList with " + i + " orderlines read");
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return orderLineList;
	}

	public List<OrderLine> readAllOrderLines() {
		List<OrderLine> orderLineList = new ArrayList<>();
		String query = "SELECT * FROM order_line";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				LOG.info("BeforeError2 1/3");
				OrderLine orderLine = new OrderLine();
				orderLine.setId(resultSet.getInt("id"));
				orderLine.setAmount(resultSet.getInt("amount"));
				orderLine.setOrderId(resultSet.getInt("order_id"));
				Product product = new Product();
				product.setId(resultSet.getInt("product_id"));
				orderLine.setProduct(product);
				orderLineList.add(orderLine);
				LOG.info("BeforeError2 2/3");
			}
			LOG.info("AfterError2 3/3");
			LOG.info("'" + orderLineList.size() + "' OrderLines read");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderLineList;
	}

	@Override
	public void updateOrderLine(OrderLine orderLine) {
		GenericDao<OrderLine> genericDao = new GenericDao<>(OrderLine.class);
		genericDao.updateObject(orderLine);
	}

	@Override
	public void deleteOrderLine(int id) {
		GenericDao<OrderLine> genericDao = new GenericDao<>(OrderLine.class);
		genericDao.deleteObject(id);
	}

}
