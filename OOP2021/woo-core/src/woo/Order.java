package woo;

import java.util.List;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.Serializable;


public class Order extends Transaction implements Serializable {

    private TreeMap<String , Aux_T> _products;
    private String _ids;
    private int _idt;
    private int _price;
    private int _date;

    
    public Order(String ids ,TreeMap<String,Aux_T> produtos, int price, int date, int idt){
        _idt = idt;
        _ids = ids;
        _price = price;
        _date = date;
        _products = produtos;
        super.setPaid(true);
        /*Atualizar o stock dos produtos na empresa agora que sabemos que nao erros na lista de produtos dados*/
        for (Map.Entry<String,Aux_T> entry : _products.entrySet()){
            Aux_T t = entry.getValue();
            t.getProduct().setStock(t.getProduct().getStock() + entry.getValue().getQtd());
        }
    }

    public int getIdt(){
        return _idt;
    }

    @Override
    public void payUp(int date){}

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String s = String.format("%d|%s|%d|%d",_idt,_ids,_price,_date);
        for (Map.Entry<String,Aux_T> entry : _products.entrySet()){
            s += "\n" + entry.getKey() +"|"+ entry.getValue().getQtd();
        }
        return s;
    }
}