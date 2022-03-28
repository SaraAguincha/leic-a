package woo.app.transactions;

import woo.exceptions.*;
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.UnknownProductKeyException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<Storefront> {

  private Input<Integer> _idt;

  public DoShowTransaction(Storefront receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _idt = _form.addIntegerInput(Message.requestTransactionKey());  
  }


  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _display.addLine(_receiver.getStore().lookupTransaction(_idt.value()).toString());
      _display.display();
    }
    catch (NoSuchTransactionIdException e){
      throw new UnknownTransactionKeyException(_idt.value());
    }

  }

}
