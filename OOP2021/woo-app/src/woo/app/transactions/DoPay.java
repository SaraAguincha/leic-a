package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.exceptions.*;


/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<Storefront> {

  private Input<Integer> _idt;
  
  public DoPay(Storefront storefront) {
    super(Label.PAY, storefront);
    _idt = _form.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    try{
      _form.parse();
      _receiver.frontDoPay(_idt.value());
    }
    catch(NoSuchTransactionIdException e){
      throw new UnknownTransactionKeyException(_idt.value());

    }
  }
}
