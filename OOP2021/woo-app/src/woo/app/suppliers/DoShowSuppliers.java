package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.Storefront;
import woo.Supplier;
import java.util.TreeMap;
import java.util.Map;


/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<Storefront> {

  public DoShowSuppliers(Storefront receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);
  }

  public String aux(Supplier s) {
    if ( s.getStatus() == true)
      return Message.yes();
    else {
      return Message.no();
    }

  }

  @Override
  public void execute() throws DialogException {
    String s = "";
    TreeMap <String,Supplier> suppliers = _receiver.getStore().getSuppliers();

    for (Map.Entry<String,Supplier> entry : suppliers.entrySet()){
      s += entry.getValue().toString() + aux(entry.getValue()) + "\n";
    }
    _display.addLine(s);
    _display.display();  
  }
}
