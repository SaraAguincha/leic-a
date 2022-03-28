package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.exceptions.*;
import woo.app.exceptions.UnknownClientKeyException;



/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<Storefront> {

  private Input<String> _idc;

  public DoLookupPaymentsByClient(Storefront storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    _idc = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _display.addLine(_receiver.getStore().doLookupPaymentsByClient(_idc.value()));
      _display.display();
    }
    catch (NoSuchClientIdException e){
      throw new UnknownClientKeyException(_idc.value());
    }    
  }

}
