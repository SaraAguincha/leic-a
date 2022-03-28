package woo.app.products;

import woo.exceptions.*;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownSupplierKeyException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;

/**
 * Register box.
 */
public class DoRegisterProductBox extends Command<Storefront> {

  private Input<String> _idp;
  private Input<Integer> _price;
  private Input<Integer> _valueCrit;
  private Input<String> _ids;
  private Input<String> _service;


  public DoRegisterProductBox(Storefront receiver) {
    super(Label.REGISTER_BOX, receiver);
    _idp = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _valueCrit = _form.addIntegerInput(Message.requestStockCriticalValue());
    _ids = _form.addStringInput(Message.requestSupplierKey());
    _service = _form.addStringInput(Message.requestServiceType());

  }

  @Override
  public final void execute() throws DialogException {
    
    try{
      _form.parse();
      _receiver.frontRegisterBox(_idp.value(), _price.value(), _valueCrit.value(), _ids.value(), _service.value());
      
      }
      catch ( ProductAlreadyExistException e){
        throw new DuplicateProductKeyException(_idp.value());
  
      }
      catch (SupplierDoesntExistException e){
        throw new UnknownSupplierKeyException(_ids.value());
      }
      catch (ServiceDoesntExistException e){
        throw new UnknownServiceTypeException(_service.value());
      }
  }
}
