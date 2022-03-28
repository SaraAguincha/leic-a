package woo;

import java.util.List;
import java.util.TreeMap;

public abstract class Product{
    private String _supplier;
    private int _price;
    private int _critvalue;
    private String _idp;
    private TreeMap<String, Client> _listaC = new TreeMap<String,Client>();
    private int _stock = 0;
    private int _N;

    /*-----------   sets    ----------*/ 

    public void setSupplier(String s){
        _supplier = s;
    }

    public void setN(int n){
        _N = n;
    }

    public void setPrice(int price){
        _price = price;
    }

    public void setCritValue(int critvalue){
        _critvalue = critvalue;
    }

    public void setIdp(String idp){
        _idp= idp;
    }

    public void setStock(int stock){
        _stock = stock;
    }
    
    /*----------    gets    ------------*/
    public int getPrice(){
        return _price;
    }

    public int getN(){
        return _N;
    }

    public int getStock(){
        return _stock;
    }

    public String getSupplier(){
        return _supplier;
    }

    public int getCritValue(){
        return _critvalue;
    }
    
    public String getIdp(){
        return _idp;
    }

    public TreeMap getListC(){
        return _listaC;
    }
    /*-------------------------------- */
    @Override
    public String toString(){
        return "";
    }
}
