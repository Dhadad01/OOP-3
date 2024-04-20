package image;
import ascii_art.InputExceptions;
/**
 * Custom checked exception class representing an exception related to resolution checking.
 * Extends InputExceptions class.
 */
public class CheckResException extends InputExceptions {
    /**
     * Constructs a CheckResException object with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public CheckResException(String message) {
        super(message);
    }
}
