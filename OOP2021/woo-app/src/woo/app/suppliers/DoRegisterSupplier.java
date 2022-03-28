package woo.app.suppliers;


import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.exceptions.*;
import woo.app.exceptions.DuplicateSupplierKeyException;


/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<Storefront> {

  private Input<String> _ids;
  private Input<String> _name;
  private Input<String> _address;

  
  public DoRegisterSupplier(Storefront receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);
    
    _ids = _form.addStringInput(Message.requestSupplierKey());
    _name = _form.addStringInput(Message.requestSupplierName());
    _address = _form.addStringInput(Message.requestSupplierAddress());
    

  }

  @Override
  public void execute() throws DialogException {
    
    try{
      _form.parse();
      _receiver.frontRegisterSupplier(_ids.value(), _name.value(), _address.value());
    }
    catch ( SupplierAlreadyExistException e){
      throw new DuplicateSupplierKeyException(_ids.value());
      
  
    
    }
  
  }
}
