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
import dao.OrderDao;
import utility.DatabaseConnection;
import model_class.Account;
import model_class.AccountType;
import model_class.Customer;
import model_class.Order;

public class OrderDaoImplHibernate implements OrderDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(OrderDaoImplHibernate.class);

	@Override
	public int createOrder(Order order) {
		GenericDao<Order> genericDao = new GenericDao<>(Order.class);
		Order createdOrder = genericDao.createObject(order);
		return createdOrder.getId();
	}

	@Override
	public void updateOrder(Order order) {
		GenericDao<Order> genericDao = new GenericDao<>(Order.class);
		genericDao.updateObject(order);
	}

	@Override
	public void deleteOrder(int id) {
		GenericDao<Order> genericDao = new GenericDao<>(Order.class);
		genericDao.deleteObject(id);
	}

	@Override
	public Order readOrderById(int id) {
		Order order = new Order();
		String query = "SELECT * FROM `order` WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			order.setId(resultSet.getInt("id"));
			order.setTotalCost(resultSet.getBigDecimal("total_cost"));
			order.setDate(resultSet.getTimestamp("date").toLocalDateTime());

			CustomerDao customerDaoImpl = new CustomerDaoImplHibernate();
			Customer customer = customerDaoImpl.readCustomerById(resultSet
					.getInt("customer_id"));
			order.setCustomer(customer);

			// OrderLineDao orderLineDaoImpl = new OrderLineDaoImpl();
			// List<OrderLine> orderLineList =
			// orderLineDaoImpl.readOrderLinesOfOrderId(resultSet.getInt("id"));
			// order.setOrderLines(orderLineList);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public List<Order> readOrdersOfCustomerId(int customer_id) {
		List<Order> orderList = new ArrayList<>();
		String query = "SELECT * FROM `order` WHERE Customer_id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, customer_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order order = new Order();
				order.setId(resultSet.getInt("id"));
				order.setTotalCost(resultSet.getBigDecimal("total_cost"));
				order.setDate(resultSet.getTimestamp("date").toLocalDateTime());
				Customer customer = new Customer();
				customer.setId(customer_id);
				order.setCustomer(customer);
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@Override
	public List<Integer> readCustomerIdsWithOrder() {
		List<Integer> list = new ArrayList<>();
		String query = "SELECT DISTINCT customer_id FROM `order`";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getInt("customer_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Order> readAllOrders() {
		List<Order> orderList = new ArrayList<>();
		String query = "SELECT * FROM `order`";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order order = new Order();
				order.setId(resultSet.getInt("id"));
				order.setTotalCost(resultSet.getBigDecimal("total_cost"));
				Customer customer = new Customer();
				customer.setId(resultSet.getInt("customer_id"));
				order.setCustomer(customer);
				order.setDate(resultSet.getTimestamp("date").toLocalDateTime());
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
}
