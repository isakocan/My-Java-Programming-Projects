package isakocan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
	private final String name;
	private final String location;
	private final HashMap<Product,Integer> purchasedProducts;
	
	public Customer(String name, String location) {
		this.name = name;
		this.location = location;
		this.purchasedProducts = new HashMap<Product, Integer>();
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public Map<Product,Integer> getPurchasedProducts() {
		return purchasedProducts;
	}
	
	public void purchaseProduct(Product product, int quantity) {
		purchasedProducts.put(product, quantity);
	}
	
	public static <Z> void showListCustomer(List<Z> customer) {
		for (Z z : customer) {
			System.out.println("Customer name: "+((Customer) z).getName()+"   Location: "+((Customer) z).getLocation());
		}
	}
	
	public static List<Customer> createCustomerList(){
		List<Customer> customerList = (List<Customer>)new ArrayList<Customer>();
		customerList.add(new Customer("Allan","Washington"));
		customerList.add(new Customer("Jhon","NYC"));
		customerList.add(new Customer("Lily","Kansas"));
		customerList.add(new Customer("Arthur","Detroit"));
		customerList.add(new Customer("Frank","NYC"));
		customerList.add(new Customer("Joe","Seattle"));
		return customerList;
	}
}
