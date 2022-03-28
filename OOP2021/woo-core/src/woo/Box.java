package woo;

import java.io.Serializable;

public class Box extends Product implements Serializable/*implements IObservable*/{
    private String _service;

    public Box(String idp,int price, int valueCrit, String ids, String service){
        _service = service;
        super.setPrice(price);
        super.setCritValue(valueCrit);
        super.setIdp(idp);
        super.setSupplier(ids);
        super.setN(5);
        
    }
    
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
        String s = String.format("BOX|%s|%s|%d|%d|%d|%s",super.getIdp(),super.getSupplier(),super.getPrice(),super.getCritValue(),super.getStock(),_service);
        return s;
    }
    
}
