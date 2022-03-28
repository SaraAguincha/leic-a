package woo.exceptions;

/** Exception thrown when the id is already associated with another client  */

public class ServiceLvlDoesntExistException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 726194382L;
    /**Already used client id */
    private String _service;

    /**
     * 
     * @param ids
     */

    public ServiceLvlDoesntExistException(String service) {
        _service = service;
    }
    /**
     * 
     * @return service lvl
     */

    public String getServiceLvl() {
        return _service;
    }
}
