package woo.exceptions;
/**thrown when product id is already used */
public class ProductAlreadyExistException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 878618246301L;
    /**Invalid idp */
    private String _idp;
    /**
     * 
     * @param idp
     */
    public ProductAlreadyExistException(String idp) {
        _idp = idp;
    }
    /**
     * 
     * @return invalid idp
     */
    public String getIdp() {
        return _idp;
    }
}