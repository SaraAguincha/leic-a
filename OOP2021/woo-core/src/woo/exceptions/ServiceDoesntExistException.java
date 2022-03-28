package woo.exceptions;

/** Exception thrown when the id is already associated with another client  */

public class ServiceDoesntExistException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 24881024L;
    /**Already used client id */
    private String _service;

    /**
     * 
     * @param ids
     */

    public ServiceDoesntExistException(String service) {
        _service = service;
    }
    /**
     * 
     * @return already used client id
     */

    public String getService() {
        return _service;
    }
}