package woo.exceptions;

/**thrown when searched client does not exists */

public class NoSuchClientIdException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 20012864737129L;
    /**invalid client id */
    private String _idc;

    /**
     * 
     * @param idc
     */
    public NoSuchClientIdException(String idc) {
        _idc = idc;
    }
    /**
     * 
     * @return of invalid client id
      */
    public String getIdc() {
        return _idc;
    }

}