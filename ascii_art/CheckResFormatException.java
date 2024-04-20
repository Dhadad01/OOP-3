package ascii_art;
/**
 * Custom checked exception class representing an exception related to result formatting.
 * Extends InputExceptions class.
 */
public class CheckResFormatException extends InputExceptions{
    /**
     * Constructs a CheckResFormatException object with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public CheckResFormatException(String message) {
        super(message);
    }
}
