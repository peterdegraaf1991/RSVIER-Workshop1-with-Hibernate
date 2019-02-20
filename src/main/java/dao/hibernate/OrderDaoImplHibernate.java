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
		GenericDao<Order> genericDao = new GenericDao<>(Order.class);
		Order readOrder = genericDao.readObjectById(id);
		return readOrder;
		}

	@Override
	public List<Order> readOrdersOfCustomerId(int customer_id) {
		GenericDao<Order> genericDao = new GenericDao<>(Order.class);
		List<Order> orderList = genericDao.readObjectListByColumn("customer_id", customer_id);
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
		GenericDao<Order> genericDao = new GenericDao<>(Order.class);
		List<Order> orderList = genericDao.readAllObjects(Order.class);
		return orderList;
	}
}
