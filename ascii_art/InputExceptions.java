package ascii_art;
/**
 * Abstract base class for custom exceptions related to input handling.
 * Extends the Exception class.
 */
public abstract class InputExceptions extends Exception {
    /**
     * Constructs an InputExceptions object with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public InputExceptions(String message) {
        super(message); // Call the constructor of the superclass (Exception) with the provided message
    }
}
