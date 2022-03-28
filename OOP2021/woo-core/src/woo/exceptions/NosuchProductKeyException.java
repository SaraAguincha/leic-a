package woo.exceptions;


public class NosuchProductKeyException extends Exception {
    private static final long serialVersionUID = 9847586007L;
    /**invalid produtos id */
    private String _idp;

    /**
     * 
     * @param idp
     */
    public NosuchProductKeyException(String idp) {
        _idp = idp;
    }
    /**
     * 
     * @return of invalid product id
      */
    public String getIdp() {
        return _idp;
    }

}
