package woo.app.products;

import woo.exceptions.*;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;

/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {

  private Input<String> _idp;
  private Input<String> _Tittle;
  private Input<String> _Author;
  private Input<String> _ISBN;
  private Input<String> _ids;
  private Input<Integer> _price;
  private Input<Integer> _valueCrit;

  //String idp, String Tittle, String Author, String ISBN, String ids, int price, int valueCrit
  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    _idp = _form.addStringInput(Message.requestProductKey());
    _Tittle = _form.addStringInput(Message.requestBookTitle());
    _Author = _form.addStringInput(Message.requestBookAuthor());
    _ISBN = _form.addStringInput(Message.requestISBN());
    _ids = _form.addStringInput(Message.requestSupplierKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _valueCrit = _form.addIntegerInput(Message.requestStockCriticalValue());
  }

  @Override
  public final void execute() throws DialogException {
    try{
      _form.parse();
      _receiver.frontRegisterBook(_idp.value(), _Tittle.value(), _Author.value(), _ISBN.value(), _ids.value(), _price.value(), _valueCrit.value());
      
      }
      catch ( ProductAlreadyExistException e){
        throw new DuplicateProductKeyException(_idp.value());
  
      }
      catch (SupplierDoesntExistException e){
        throw new UnknownSupplierKeyException(_ids.value());
      }
  }
}
