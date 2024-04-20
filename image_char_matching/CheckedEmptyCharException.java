package image_char_matching;
import ascii_art.InputExceptions;
/**
 * Represents an exception thrown when attempting to match an empty character during image character matching.
 */
public class CheckedEmptyCharException extends InputExceptions{
    /**
     * Constructs a CheckedEmptyCharException with the specified error message.
     * @param message the error message
     */
    public CheckedEmptyCharException(String message) {
        super(message);
    }
}
