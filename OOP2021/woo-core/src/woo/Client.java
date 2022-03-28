package woo;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Client implements Serializable/*implements IObserver*/{

    private String _idc;
    private String _name;
    private String _address;
    private List<Sale> _transactions = new ArrayList<Sale>();
    private int _points = 0;
    private int _totalSpent = 0;
    private int  _allPurchases = 0;

    
    public Client(String idc, String name, String address) {
        _idc = idc;
        _name = name;
        _address = address;
    }

    public String getIdc(){
        return _idc;
    }

    public String getStatus(){
        if (_points < 2000) {
            return "NORMAL";
        }
        else if (_points > 25000) {
            return "ELITE";
        }
        else {
            return "SELECTION";
        }
    }

    public void setPoints(int points){
        _points = points; 
    }
    public int getPoints(){
        return _points;
    }

    public List<Sale> getTransactions(){
        return _transactions;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String s = String.format("%s|%s|%s|%s|%d|%d",_idc,_name,_address,getStatus(),_totalSpent,_allPurchases);
        return s;
    }

    public void addTransaction(Sale s) {
        _transactions.add(s);
    }
/*
    public List<Transaction> paidSales() {
        //fix me
    }

    public void update(){
        //fix me
    }
    */
}