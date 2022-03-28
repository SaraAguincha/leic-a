package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnavailableProductException;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.*;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {

  private Input<String> _idc;
  private Input<Integer> _dueDate;
  private Input<String> _idp;
  private Input<Integer> _qtd;


  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _idc = _form.addStringInput(Message.requestClientKey());
    _dueDate = _form.addIntegerInput(Message.requestPaymentDeadline());
    _idp = _form.addStringInput(Message.requestProductKey());
    _qtd = _form.addIntegerInput(Message.requestAmount());

  }

// FALTAM AS EXCEPTIONS

  @Override
  public final void execute() throws DialogException {
    try{
      _form.parse();
      _receiver.frontRegisterSale(_idc.value(), _dueDate.value(), _idp.value(), _qtd.value()); 
    }
    catch(NoSuchClientIdException e){
      throw new UnknownClientKeyException(_idc.value());
    }
    catch(NosuchProductKeyException e){
      throw new UnknownProductKeyException(_idp.value());
    }
    catch(NotEnoughStockException e){
      throw new UnavailableProductException(_idp.value(),_qtd.value(),e.getStock());
    }
  }
}
