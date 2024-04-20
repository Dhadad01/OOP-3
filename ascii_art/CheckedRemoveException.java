package ascii_art;
/**
 * Custom checked exception class representing an exception related to removing elements.
 * Extends InputExceptions class.
 */
public class CheckedRemoveException extends InputExceptions{
    /**
     * Constructs a CheckedRemoveException object with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public CheckedRemoveException(String message) {
        super(message);
    }
}
