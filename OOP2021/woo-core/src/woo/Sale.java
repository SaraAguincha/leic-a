package woo;

import java.io.Serializable;

public class Sale extends Transaction implements Serializable{

    private int _idt;
    private int _dueDate;
    private boolean _status;
    private Client _c;
    private Product _p;
    private int _qtd;
    private int _paymentDate;
    private int _price;
    private boolean _paid = false;

    public Sale(Client c, int dueDate, Product p, int qtd, int idt){
        _c = c;
        _dueDate = dueDate;
        _p = p;
        _qtd = qtd;
        _idt = idt;
        p.setStock(p.getStock() - qtd);

        }

    public void setPrice(int price) {
        _price = price;
    }

    public int getIdt(){
        return _idt;
    }


    /*------------------------  Functions to get price with discounts    ---------------------------*/
    public String getTime(int date){
        int n = _p.getN();
        if (_dueDate - date >= n){
            return "P1";
        }
        else if(0 <= _dueDate - date && _dueDate - date< n){
            return "P2";
        }
        else if(0 <= date - _dueDate && date - _dueDate <= n){
            return "P3";
        }
        else{
            return "P4";
        }
    }

    public double getRealPrice(int date){
        String time = getTime(date);
        String status = _c.getStatus();
        switch (time){
            case "P1":
                return _price*0.90;
            case "P2":
                if (status.equals("SELECTION") && (_dueDate - date >= 2)){
                    return _price*0.95;
                }
                if (status.equals("ELITE"))
                    return _price*0.90;
                return _price;
            case "P3":
                if (status.equals("NORMAL")){
                    return _price + _price*0.05*(date-_dueDate);
                }
                else if(status.equals("SELECTION") && date-_dueDate >1){
                    return _price + _price*0.02*(date-_dueDate);
                }
                else if(status.equals("ELITE")){
                    return _price*0.95;
                }
                return _price;
            case "P4":
                if (status.equals("NORMAL"))
                    return _price + _price*0.1*(date-_dueDate);
                else if(status.equals("SELECTION"))
                    return _price + _price*0.05*(date-_dueDate);
                return _price;
        }
        return -1;
    }

    @Override
    public void payUp(int date){
        _paymentDate = date;
        super.setPaid(true);
        super.setRealPrice(getRealPrice(date));

        String status = _c.getStatus();
        String time = getTime(date);
        
        if (time.equals("P1") || time.equals("P2")){
            _c.setPoints(_c.getPoints()+ (int) Math.round(super.getRealPrice()*10));
        }
        else if(status.equals("SELECTION") && date - _dueDate > 2)
            _c.setPoints((int) Math.round(_c.getPoints()*0.10));
        else if(status.equals("ELITE") && date - _dueDate >15 )
            _c.setPoints((int) Math.round(_c.getPoints()*0.25));
    }
    /*----------------------------------------------------------------------------------------*/

    @SuppressWarnings("nls")
    public String toString() {
        String s;
        if (super.getPaid() == true){
            s = String.format("%d|%s|%s|%d|%d|%d|%d|%d",_idt,_c.getIdc(),_p.getIdp(),_qtd,_price,(int) Math.round(super.getRealPrice()),_dueDate,_paymentDate);
        }
        else {
            s = String.format("%d|%s|%s|%d|%d|%d|%d",_idt,_c.getIdc(),_p.getIdp(),_qtd,_price,(int) Math.round(getRealPrice(super.getAuxDate())),_dueDate);
        }
        return s;
    }
}