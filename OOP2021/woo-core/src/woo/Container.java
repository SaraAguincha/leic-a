package woo;

import java.io.Serializable;

public class Container extends Product implements Serializable/*implements IObservable*/{
    private String _service;
    private String _serviceLevel;

    public Container(String idp, int price, int valueCrit, String ids, String service, String serviceLevel){
        _service = service;
        super.setPrice(price);
        super.setCritValue(valueCrit);
        super.setIdp(idp);
        super.setSupplier(ids);
        super.setN(8);
        _serviceLevel = serviceLevel;
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
        String s = String.format("CONTAINER|%s|%s|%d|%d|%d|%s|%s",super.getIdp(),super.getSupplier(),super.getPrice(),super.getCritValue(),super.getStock(),_service,_serviceLevel);
        return s;
    }
    
}
