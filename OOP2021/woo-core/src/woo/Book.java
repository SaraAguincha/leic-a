package woo;

import java.io.Serializable;

public class Book extends Product implements Serializable/*implements IObservable*/{
    private String _Tittle;
    private String _Author;
    private String _ISBN;

    public Book(String idp, String Tittle, String Author, String ISBN, String ids,int price, int valueCrit){
        _Tittle = Tittle;
        super.setPrice(price);
        super.setCritValue(valueCrit);
        super.setIdp(idp);
        super.setSupplier(ids);
        super.setN(3);
        _Author= Author;
        _ISBN = ISBN;
    }

    @Override
    public void setStock(int stock){
        super.setStock(stock);
    }
    /*
    public void addObserver(IObserver o){
        if (o instanceof Client){
            Client c = (Client) o;
            _listaC.put(o.getId(),o);
        }
    }

    public void removeObserver(String id){
        _listaC.remove(id);
    }

    public void warn(){
        for (Client c : _listaC) {
            c.update();
        }
    }
    */

    @Override
    public String toString(){
        String s = String.format("BOOK|%s|%s|%d|%d|%d|%s|%s|%s",super.getIdp(),super.getSupplier(),super.getPrice(),super.getCritValue(), super.getStock(),_Tittle,_Author,_ISBN);
        return s;
    }
    
}
