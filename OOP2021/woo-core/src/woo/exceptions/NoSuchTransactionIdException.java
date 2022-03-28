package woo.exceptions;

/**thrown when transaction Id doesnt exist */
public class NoSuchTransactionIdException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 300235364237129L;
    /**invalid transaction id */
    private int _idt;

    /**
     * 
     * @param idt
     */
    public NoSuchTransactionIdException(int idt) {
        _idt = idt;
    }
    
    /**
     * 
     * @return invaid transaction id
     */
    public int getIdt() {
        return _idt;
    }
}
