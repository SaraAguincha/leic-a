package woo.exceptions;


public class InvalidStatusSupplierException extends Exception {
    private static final long serialVersionUID = 128482714442L;
    /**invalid sup id */
    private String _ids;

    /**
     * 
     * @param ids
     */
    public InvalidStatusSupplierException(String ids) {
        _ids = ids;
    }
    /**
     * 
     * @return of invalid sup id
      */
    public String getIds() {
        return _ids;
    }

}
