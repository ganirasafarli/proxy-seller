package proxy.seller.assigment.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Item not found.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
