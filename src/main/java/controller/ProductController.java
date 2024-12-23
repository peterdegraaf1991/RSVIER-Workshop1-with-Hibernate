package controller;

import java.util.List;

import model_class.OrderLine;
import model_class.Product;
import dao.DaoFactory;
import view.ProductView;

public class ProductController extends Controller {
	ProductView productView = new ProductView();

	@Override
	public void runController() {
		int keuze = 1;
		Controller.newView = true;
		do {
			if (Controller.newView == true) {
				productView.clearTerminal();
				productView.printMenuHeader();
				productView.printMenuOptions();
				Controller.newView = false;
			}

			keuze = productView.requestMenuOption();
			switch (keuze) {
			case 1:
				printProductListWithoutOption();
				requestNewMenu();
				break;
			case 2:
				if (workerOrAdminPermission() == true)
					addProduct();
				else
					productView.noPermission();
				requestNewMenu();
				break;
			case 3:
				if (workerOrAdminPermission() == true)
					updateProduct(selectProductFromList());
				else
					productView.noPermission();
				requestNewMenu();
				break;
			case 4:
				if (workerOrAdminPermission() == true)
					deleteProduct();
				else
					productView.noPermission();
				requestNewMenu();
				break;
			case 9:
				keuze = 0;
				Controller.newView = true;
				break;
			case 0:
				productView.logoutTimer();
				System.exit(0);
				break;
			default:
				productView.invalidInput();
				break;
			}
		} while (keuze != 0);
	}

	private void updateProduct(Product product) {
		if (product == null)
			return;
		product.setName(productView.requestName());
		product.setPrice(productView.requestPrice());
		product.setStock(productView.requestStock());
		DaoFactory.getProductDao().updateProduct(product);
		// Print succes message
	}

	private void deleteProduct() {
		Product product = selectProductFromList();
		if (product == null)
			return;
		DaoFactory.getProductDao().deleteProduct(product.getId());
		}

	public Product selectProductFromList() {
		List<Product> list = printProductlist();
		if (list == null) {
			return null;
		}
		int index = productView.requestProductNumber(list.size());
		return list.get(index);
	}

	private void addProduct() {
		Product product = new Product();
		product.setName(productView.requestName());
		product.setPrice(productView.requestPrice());
		product.setStock(productView.requestStock());
		DaoFactory.getProductDao().createProduct(product);
		// Print succes message
	}

	private List<Product> printProductlist() {
		List<Product> productList = DaoFactory.getProductDao()
				.readAllProducts();
		if (productList.size() <= 0) {
			productView.noProductFound();
			return null;
		}
		productView.printProductList(productList);
		return productList;
	}

	private List<Product> printProductListWithoutOption() {
		List<Product> productList = DaoFactory.getProductDao()
				.readAllProducts();
		if (productList.size() <= 0) {
			productView.noProductFound();
			return null;
		}
		productView.printProductListWithoutOption(productList);
		return productList;
	}
	public void updateStock(List<OrderLine> list) {
		for (int i = 0; i < list.size(); i++) {
			int productId = list.get(i).getProduct().getId();
			Product product = DaoFactory.getProductDao().readProductById(
					productId);
			product.setStock(product.getStock() - list.get(i).getAmount());
			DaoFactory.getProductDao().updateProduct(product);
		}
	}

}
