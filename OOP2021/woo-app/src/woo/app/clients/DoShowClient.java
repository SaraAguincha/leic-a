package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.exceptions.*;
import woo.app.exceptions.UnknownClientKeyException;
import pt.tecnico.po.ui.DialogException;                                                                                                      import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;                                                                                                                        //FIXME import other classes

/**
 * Show client.
 */
public class DoShowClient extends Command<Storefront> {

  private Input<String> _idc;

  public DoShowClient(Storefront receiver) {
    super(Label.SHOW_CLIENT, receiver);
    _idc = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _display.addLine(_receiver.getStore().lookupClient(_idc.value()).toString());
      _display.display();
    }
    catch (NoSuchClientIdException e){
      throw new UnknownClientKeyException(_idc.value());
    }
  }

}
