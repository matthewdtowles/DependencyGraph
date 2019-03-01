package dependencygraph;

/**
 * CycleException
 *
 * @author matthew.towles
 * @date Mar 1, 2019
 */
public class CycleException extends Exception {

    /**
     * Creates a new instance of CycleException without detail message.
     */
    public CycleException() {
    }


    /**
     * Constructs an instance of CycleException with the specified detail message.
     * @param msg the detail message.
     */
    public CycleException(String msg) {
        super(msg);
    }
}
