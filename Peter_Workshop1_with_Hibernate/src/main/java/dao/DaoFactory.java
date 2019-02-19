package dao;

import dao.hibernate.*;

public class DaoFactory {

	private static AccountDao accountDao = null;
	private static AccountTypeDao accountTypeDao = null;
	private static CustomerDao customerDao = null;
	private static OrderDao orderDao = null;
	private static OrderLineDao orderLineDao = null;
	private static ProductDao productDao = null;
	
	
	public DaoFactory(int databaseOption) {
		switch (databaseOption) {

		case 1:
			accountDao = new AccountDaoImplHibernate();
			accountTypeDao = new AccountTypeDaoImplHibernate();
			customerDao = new CustomerDaoImplHibernate();
			orderDao = new OrderDaoImplHibernate();
			orderLineDao = new OrderLineDaoImplHibernate();
			productDao = new ProductDaoImplHibernate();
			break;
			
		default:
			//temp.
			System.out.println("Invalid input");
			break;
		}
	}

	public static AccountDao getAccountDao() {
		return accountDao;
	}


	public static AccountTypeDao getAccountTypeDao() {
		return accountTypeDao;
	}


	public static CustomerDao getCustomerDao() {
		return customerDao;
	}


	public static OrderDao getOrderDao() {
		return orderDao;
	}


	public static OrderLineDao getOrderLineDao() {
		return orderLineDao;
	}


	public static ProductDao getProductDao() {
		return productDao;
	}
	
}
