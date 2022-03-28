package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.app.exceptions.UnknownClientKeyException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.exceptions.*;


/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<Storefront> {

  private Input<String> _idc;

  public DoShowClientTransactions(Storefront storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    _idc = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _display.addLine(_receiver.getStore().showClientTransactions(_idc.value()));
      _display.display();
    }
    catch (NoSuchClientIdException e){
      throw new UnknownClientKeyException(_idc.value());
    }  
  }

}
