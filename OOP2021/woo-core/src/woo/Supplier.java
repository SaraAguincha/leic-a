package woo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Supplier implements Serializable {

    private String _ids;
    private String _name;
    private String _address;
    private boolean _status = true;
    private List<Order> _transactions = new ArrayList<Order>();
    
    public Supplier(String ids, String name, String address){
        _ids = ids;
        _name = name;
        _address = address;
    }

    public String getIds(){
        return _ids;
    }

    public boolean getStatus(){
        return _status;
    }

    public void toggle(){
        if (_status == true){
            _status = false;
        }
        else{
            _status = true;
        }
    }

    public void addTransaction(Order s) {
        _transactions.add(s);
    }

    public List<Order> getTransactions(){
        return _transactions;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String s = String.format("%s|%s|%s|",_ids,_name,_address); // o status e adicionado na store
        return s;
    }

    /*
    public void addTransactions(Transaction t) {
        _transactions.put(getIdt(t),t);
    }
    */
    
}