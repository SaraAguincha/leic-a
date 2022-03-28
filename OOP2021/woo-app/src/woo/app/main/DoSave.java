package woo.app.main;

import pt.tecnico.po.ui.Command;
import java.io.IOException;
import woo.Storefront;
import pt.tecnico.po.ui.Input;

import woo.exceptions.MissingFileAssociationException;



/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<Storefront> {

  private Input<String> _filename;

  /** @param receiver */
  public DoSave(Storefront receiver) {
    super(Label.SAVE, receiver);
    if(_receiver.getFileName() == null)
      _filename = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    if (_receiver.getSave())
      try {
        if (_receiver.getFileName() == null) {
          _form.parse();
          _receiver.saveAs(_filename.value());
        }
        _receiver.save();
      }
      catch (IOException | MissingFileAssociationException e){
        e.printStackTrace();
      }
    }
}
