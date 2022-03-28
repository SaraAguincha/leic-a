package woo.exceptions;
/**thrown when number is under 0 */
public class InvalidDateInputException extends Exception {
    /**Serial number */
    private static final long serialVersionUID = 82651864981L;
    /**Invalid date */
    private int _date;

    /**
     * 
     * @param date
     */
    public InvalidDateInputException(int date) {
        _date = date;
    }
    /**
     * 
     * @return invalid date
     */
    public int getDate() {
        return _date;
    }

}