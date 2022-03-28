package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.Supplier;
import woo.exceptions.*;
import woo.app.exceptions.UnknownSupplierKeyException;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {
  private Input<String> _ids;



  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _ids = _form.addStringInput(Message.requestSupplierKey());

  }

  public String aux(String s, Supplier sup) {
    if ( sup.getStatus() == true)
      return Message.transactionsOn(s);
    else {
      return Message.transactionsOff(s);
    }

  }

  @Override
  public void execute() throws DialogException {
    try{
      _form.parse();
      Supplier sup;
      sup = _receiver.frontToggleSupplier(_ids.value());
      String s = aux(_ids.value(),sup);
      _display.addLine(s);
      _display.display();  

    }
    catch ( SupplierDoesntExistException e){
      throw new UnknownSupplierKeyException(_ids.value());
    }

  }
}
