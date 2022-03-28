package woo;

public class Aux_T {
    
    private Product _p;
    private int _qtd;

    
    public Aux_T(Product p, int qtd) {
        _p = p;
        _qtd = qtd;
    }

    public Product getProduct(){
        return _p;
    }

    public int getQtd(){
        return _qtd;
    }

}