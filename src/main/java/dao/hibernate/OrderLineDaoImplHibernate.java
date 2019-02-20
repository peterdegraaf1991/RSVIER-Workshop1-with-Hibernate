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
		GenericDao<OrderLine> genericDao = new GenericDao<>(OrderLine.class);
		OrderLine readOrderLine = genericDao.readObjectById(id);
		return readOrderLine;
		}

	@Override
	public List<OrderLine> readOrderLinesOfOrderId(int id) {
		GenericDao<OrderLine> genericDao = new GenericDao<>(OrderLine.class);
		List<OrderLine> orderLineList = genericDao.readObjectListByColumn("order_id", id);
		return orderLineList;
	}

	public List<OrderLine> readAllOrderLines() {
		GenericDao<OrderLine> genericDao = new GenericDao<>(OrderLine.class);
		List<OrderLine> orderLineList = genericDao.readAllObjects(OrderLine.class);
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
