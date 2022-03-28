package woo.app.products;

import pt.tecnico.po.ui.Command;                                                                                                              import pt.tecnico.po.ui.DialogException;                                                                                                      import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;                                                                                                                        //FIXME import other classes

/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {

  private Input<String> _idp;
  private Input<Integer> _price;

  
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _idp = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());

  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    _receiver.frontChangePrice(_idp.value(),_price.value());
  }
}
