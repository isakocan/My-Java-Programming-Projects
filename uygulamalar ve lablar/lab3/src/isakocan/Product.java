package isakocan;

import java.util.ArrayList;
import java.util.List;

public class Product implements IProduct{

	private static int idCounter=1;
	private final int id;
	private final String name;
	private final double price;
	private int stock;
	
	
	
	public Product(String name, double price) {
		this.name = name;
		this.price = price;
		this.stock = 100;
		this.id = idCounter;
		idCounter++;
	}
	
	public void reduceStock(int quantity) throws InsufficientStockError {
		if(quantity<=stock) 
			stock -= quantity;
		
		else 
			throw new InsufficientStockError("Insufficient stock for product: " + name + ". Only " + stock + " units are available. Remaining stock has been provided.");
		
	}

	public static void printInventoryWithIds(List<Product> products) {
		for (Product product : products) {
			System.out.println("Product ID: " + product.getId() + ", Product: " + product.getName() + ", Remaining Stock: " + product.getStock());
		}
	}
	
	public static List<Product> createProductList(){
		
		List<Product> productList = (List<Product>) new ArrayList<Product>();
		productList.add(new Product("Grain",125));
		productList.add(new Product("Steel",300));
		productList.add(new Product("Plastic",100));
		productList.add(new Product("Gold",1000));
		productList.add(new Product("Cotton",350));
		productList.add(new Product("Olive",250));
		return productList;
		
	}
	
	public int getId() {
		return id;
	}



	public int getStock() {
		return stock;
	}



	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getPrice() {
		return price;
	}

}
