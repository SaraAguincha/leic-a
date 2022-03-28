package woo.app.transactions;

import pt.tecnico.po.ui.Command;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;                                                                                                              import pt.tecnico.po.ui.DialogException;                                                                                                      import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;                                                                                                                        //FIXME import other classes
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.WrongSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.*;

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

  //FIXME add input fields
  private Input<String> _ids;
  private Input<String> _idp;
  private Input<Integer> _qtd;
  private TreeMap<String,Integer> _produtos = new TreeMap<String,Integer>();

  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);

  }

  @Override
  public final void execute() throws DialogException {
    _form.clear();
    _ids = _form.addStringInput(Message.requestSupplierKey());
    while (true){
      _idp = _form.addStringInput(Message.requestProductKey());
      _qtd = _form.addIntegerInput(Message.requestAmount());
      _form.parse();
      _produtos.put(_idp.value(),_qtd.value());
      _form.clear();
      Input<String> more = _form.addStringInput(Message.requestMore());
      _form.parse();
      if(more.value().equals("n"))
        break;
      _form.clear();
    }
    try{
      _receiver.frontRegisterOrder(_ids.value(),_produtos);
    }
    catch(SupplierDoesntExistException e){
      throw new UnknownSupplierKeyException(_ids.value());
    }
    catch (InvalidStatusSupplierException e){
      throw new UnauthorizedSupplierException(_ids.value());
    }
    catch (NosuchProductKeyException e){
      throw new UnknownProductKeyException(e.getIdp());
    }
    catch(InvalidSupplierException e){
      throw new WrongSupplierException(_ids.value(),e.getIdp());
    }
  }

}
