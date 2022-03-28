package woo.app.products;

import woo.exceptions.*;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownServiceLevelException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownSupplierKeyException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {

  private Input<String> _idp;
  private Input<Integer> _price;
  private Input<Integer> _valueCrit;
  private Input<String> _ids;
  private Input<String> _service;
  private Input<String> _serviceLevel;


  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    _idp = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _valueCrit = _form.addIntegerInput(Message.requestStockCriticalValue());
    _ids = _form.addStringInput(Message.requestSupplierKey());
    _service = _form.addStringInput(Message.requestServiceType());
    _serviceLevel = _form.addStringInput(Message.requestServiceLevel());

  }


  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.frontRegisterContainer(_idp.value(), _price.value(), _valueCrit.value(), _ids.value(), _service.value(), _serviceLevel.value());
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
    catch (ServiceLvlDoesntExistException e){
      throw new UnknownServiceLevelException(_serviceLevel.value());
    }
  }
}
