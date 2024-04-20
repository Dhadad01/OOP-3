package ascii_art;
/**
 * Custom checked exception class representing an exception related to font handling.
 * Extends InputExceptions class.
 */
public class CheckedFontException extends InputExceptions{
    /**
     * Constructs a CheckedFontException object with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public CheckedFontException(String message) {
        super(message);
    }
}
