package woo;

import java.io.Serializable;

public abstract class Transaction implements Serializable{

    private int _idt;
    private int _price;
    private int _dueDate;
    private int _paymentDate;
    private int _auxDate;
    private double _realPrice = 0;
    private boolean _paid = false;


    /*----------    gets    ------------*/


    public int getPrice(){
        return _price;
    }

    public int getPaymentDate(){
        return _paymentDate;
    }
    
    public double getRealPrice(){
        return _realPrice;
    }

    public boolean getPaid(){
        return _paid;
    }
    /*-----------   sets    ----------*/ 

    public void setAuxDate(int date){
        _auxDate = date;
    }

    public int getAuxDate(){
        return _auxDate;
    }

    public void setPaid(boolean status){
        _paid = status;
    }

    public void setRealPrice(double rprice){
        _realPrice = rprice;
    }

    public void payUp(int date){

    }
}