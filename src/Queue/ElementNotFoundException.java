package Queue;

public class ElementNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ElementNotFound</code> without detail
     * message.
     */
    public ElementNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ElementNotFound</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ElementNotFoundException(String msg) {
        super(msg);
    }
}
