package isakocan;

public class InsufficientStockError extends Exception{

	private static final long serialVersionUID = 1L;

	public InsufficientStockError(String message) {
		super(message);
	}
}
