package ascii_art;
/**
 * Custom checked exception class representing a general input-related exception.
 * Extends InputExceptions class.
 */
public class CheckedAllException extends InputExceptions{
    /**
     * Constructs a CheckedAllException object with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    CheckedAllException(String message){
        super(message);
    }
}
