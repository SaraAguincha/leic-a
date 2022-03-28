package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.exceptions.*;
import woo.app.exceptions.UnknownSupplierKeyException;



/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {

  private Input<String> _ids;

  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _ids = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _display.addLine(_receiver.getStore().doShowSupplierTransactions(_ids.value()));
      _display.display();
    }
    catch (SupplierDoesntExistException e){
      throw new UnknownSupplierKeyException(_ids.value());
    }    
  }

}
