package woo.exceptions;

/** Exception thrown when the id is already associated with another client  */

public class SupplierDoesntExistException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 8394221L;
    /**Already used client id */
    private String _ids;

    /**
     * 
     * @param ids
     */

    public SupplierDoesntExistException(String ids) {
        _ids = ids;
    }
    /**
     * 
     * @return already used client id
     */

    public String getIdc() {
        return _ids;
    }
}