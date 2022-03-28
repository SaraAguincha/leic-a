package woo.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import woo.app.exceptions.FileOpenFailedException;
import woo.exceptions.*;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.main.Message;
import woo.Storefront;

//FIXME import other classes

/**
 * Open existing saved state.
 */
public class DoOpen extends Command<Storefront> {

  private Input<String> _filename;

  /** @param receiver */
  public DoOpen(Storefront receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.load(_filename.value());
    } 
    
    catch (FileNotFoundException | UnavailableFileException e) {
      throw new FileOpenFailedException(_filename.value());
    }
    
    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  

}
