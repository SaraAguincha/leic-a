package woo.app.clients;

import woo.Storefront;
import woo.exceptions.*;
import pt.tecnico.po.ui.Command;                        
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import woo.app.exceptions.DuplicateClientKeyException;

/**
 * Register new client.
 */
public class DoRegisterClient extends Command<Storefront> {

  private Input<String> _idc;
  private Input<String> _name;
  private Input<String> _address;


  public DoRegisterClient(Storefront receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    _idc = _form.addStringInput(Message.requestClientKey());
    _name = _form.addStringInput(Message.requestClientName());
    _address = _form.addStringInput(Message.requestClientAddress());
  }

  @Override
  public void execute() throws DialogException {
    
    try{
    _form.parse();
    _receiver.frontRegisterClient(_idc.value(), _name.value(), _address.value());
    
    }
    catch ( ClientAlreadyExistException e){
      throw new DuplicateClientKeyException(_idc.value());

    }
    
  }

}
