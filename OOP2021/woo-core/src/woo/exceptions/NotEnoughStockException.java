package woo.exceptions;

/**thrown when qtd > stock */

public class NotEnoughStockException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 22384864737129L;
    /**invalid client id */
    private int _qtd;
    private int _stock;

    /**
     * 
     * @param qtd
     * @param stock
     */
    
    public NotEnoughStockException(int qtd, int stock) {
        _qtd = qtd;
        _stock = stock;
    }
    
    /**
     * 
     * @return of invalid qtd
     */
    public int getQtd() {
        return _qtd;
    }
    /**
     * 
     * @return stock
     */
    public int getStock() {
        return _stock;
    }

}