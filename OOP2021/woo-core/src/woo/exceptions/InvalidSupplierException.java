package woo.exceptions;


public class InvalidSupplierException extends Exception{
    private static final long serialVersionUID = 261424341235L;
    /**invalid sup id */
    private String _ids;
    private String _idp;

    /**
     * 
     * @param ids
     */
    public InvalidSupplierException(String ids,String idp) {
        _ids = ids;
        _idp = idp;
    }
    /**
     * 
     * @return of invalid sup id
      */
    public String getIds() {
        return _ids;
    }
    public String getIdp(){
        return _idp;
    }
}
