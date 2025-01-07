package isakocan;

import java.util.List;
import java.util.Random;

public class Shipping implements IShipping{
	private static final Random random = new Random();
	
	@Override
	public boolean shipProduct(String productName, String customerName){
		return random.nextDouble() <= 0.75;
	}
	
	public void simulateShipping(List<Customer> customers) throws ProductShippingError{
		
		try {
		for (Customer customer : customers) {
			for (Product product : customer.getPurchasedProducts().keySet()) {
				if(shipProduct(product.getName(), customer.getName()))
					System.out.println("Product "+product.getName()+" successfully shipped to "+customer.getName());
				else
					throw new ProductShippingError(product.getName()+" could not be shipped to "+customer.getName());
			}
			
		}
		}
		catch(ProductShippingError e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
}
