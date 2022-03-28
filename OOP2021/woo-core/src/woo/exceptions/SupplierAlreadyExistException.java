package woo.exceptions;

/**thrown when supplier Id is already in use */
public class SupplierAlreadyExistException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 30012864237129L;
    /**invalid supplier id */
    private String _ids;
    /**
     * 
     * @param ids
     */
    public SupplierAlreadyExistException(String ids) {
        _ids = ids;
    }
    /**
     * 
     * @return invaid supplier id
     */
    public String getIds() {
        return _ids;
    }
}