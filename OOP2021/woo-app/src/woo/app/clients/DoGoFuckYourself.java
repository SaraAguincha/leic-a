package woo.app.clients;

import woo.Storefront;
import woo.exceptions.*;
import pt.tecnico.po.ui.Command;                        
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import woo.app.exceptions.DuplicateClientKeyException;


public class DoGoFuckYourself extends Command<Storefront> {
    
    public DoGoFuckYourself(Storefront receiver){
        super(Label.FUCK_YOU, receiver);
    }

    @Override
    public void execute() throws DialogException {
        System.out.println("Não quero saber");
    }
}
