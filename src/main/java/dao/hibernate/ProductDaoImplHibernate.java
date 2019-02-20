package dao.hibernate;

import model_class.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.ProductDao;

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
		GenericDao<Product> genericDao = new GenericDao<>(Product.class);
		Product readProduct = genericDao.readObjectById(id);
		return readProduct;
		}

	public Product readProductByName(String name) {
		GenericDao<Product> genericDao = new GenericDao<>(Product.class);
		Product readProduct = genericDao.readSingleObjectByColumn("name", name);
		return readProduct;
	}

	public List<Product> readAllProducts() {
		GenericDao<Product> genericDao = new GenericDao<>(Product.class);
		List<Product> productList = genericDao.readAllObjects(Product.class);
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
