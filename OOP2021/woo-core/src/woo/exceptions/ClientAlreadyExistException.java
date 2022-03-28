package woo.exceptions;

/** Exception thrown when the id is already associated with another client  */

public class ClientAlreadyExistException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 136741823230L;
    /**Already used client id */
    private String _idc;

    /**
     * 
     * @param idc
     */

    public ClientAlreadyExistException(String idc) {
        _idc = idc;
    }
    /**
     * 
     * @return already used client id
     */

    public String getIdc() {
        return _idc;
    }
}