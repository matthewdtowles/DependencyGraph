package dependencygraph;

/**
 * InvalidClassException
 *
 * @author matthew.towles
 * @date Mar 1, 2019
 */
public class InvalidClassException extends Exception {

    /**
     * Creates a new instance of InvalidClassException without detail message.
     */
    public InvalidClassException() {
    }


    /**
     * Constructs an instance of InvalidClassException with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidClassException(String msg) {
        super(msg);
    }
}
