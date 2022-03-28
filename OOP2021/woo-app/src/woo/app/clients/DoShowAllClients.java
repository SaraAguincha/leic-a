package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;

/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<Storefront> {

  public DoShowAllClients(Storefront receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);

  }

  @Override
  public void execute() throws DialogException {
    _display.addLine(_receiver.getStore().showAllClients());
    _display.display();
  }
}
