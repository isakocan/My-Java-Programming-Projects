package isakocan;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Market {
	
	public static final Random random = new Random();
	
	public static void simulatePurcashes(List<Customer> customers, List<Product> products) throws InsufficientStockError{
		for (Customer customer : customers) {
			int numberOfProducts = random.nextInt(3)+1;
			
			for (int i = 0; i < numberOfProducts; i++) {
				int quantity = random.nextInt(26)+25;
				int randomProduct = random.nextInt(products.size());
				
				try {
					products.get(randomProduct).reduceStock(quantity);
					customer.purchaseProduct(products.get(randomProduct), quantity);
					System.out.println(customer.getName() + " purcashed " + quantity + " of " + products.get(randomProduct).getName());
				} catch (InsufficientStockError e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public static void printCustomerSpending(List<Customer> customers) {
		for (Customer customer : customers) {
			double totalSpending = 0.0;
			for (Map.Entry<Product,Integer> product : customer.getPurchasedProducts().entrySet()) {
				totalSpending += (product.getKey().getPrice()) * (product.getValue());
			}
			System.out.println("Customer: "+customer.getName()+", Total Spending: $"+totalSpending);
		}
	}
	
	public static void printTotalIncome(List<Customer> customers) {
		double totalIncome = 0.0;
		for (Customer customer : customers) {
			for (Map.Entry<Product,Integer> product : customer.getPurchasedProducts().entrySet()) {
				totalIncome += (product.getKey().getPrice()) * (product.getValue());
			}
		}
		System.out.println("Total Market Income: $"+totalIncome);
	}
	
}
