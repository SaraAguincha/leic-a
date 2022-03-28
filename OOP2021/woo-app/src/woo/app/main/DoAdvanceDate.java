package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.InvalidDateException;
import woo.exceptions.InvalidDateInputException;


/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<Storefront> {
  
  private Input<Integer> _d;

  public DoAdvanceDate(Storefront receiver){
    super(Label.ADVANCE_DATE, receiver);
    _d = _form.addIntegerInput(Message.requestDaysToAdvance());  
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.getStore().advanceDate(_d.value());
    }
    catch (InvalidDateInputException e){
      throw new InvalidDateException(_d.value());
    } 

  }
}
