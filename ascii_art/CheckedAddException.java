package ascii_art;
/**
 * Custom checked exception class representing an exception that occurs when adding elements.
 * Extends InputExceptions class.
 */
public class CheckedAddException extends InputExceptions{
    /**
     * Constructs a CheckedAddException object with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    CheckedAddException(String message){
        super(message);

    }
}
