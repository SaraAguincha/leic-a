package woo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import woo.exceptions.*;
import java.io.*;


/**
 * Storefront: façade for the core classes.
 */
public class Storefront {

  /** Current filename. */
  private String _filename;
  private boolean _save = false; 

  /** The actual store. */
  private Store _store = new Store();

  /**creater */
  public Storefront(){
  }
  /**
   * 
   * @return store
   */
  public Store getStore(){
    return _store;
  }
  /**
   * 
   * @return File name
   */
  public String getFileName() {
    return _filename;
  }
  /**
   * 
   * @return save status
   */
  public boolean getSave(){
    return _save;
  }
  /**
   * sets save to true or false 
   * @param value of the save
   */
  public void setSave(boolean value){
    _save = value;
  }

  //------------------------------------FRONT FUNCTIONS---------------------

  public void frontRegisterClient(String idc, String name, String address) throws ClientAlreadyExistException{ 
    _store.registerClient(idc,name,address);
    setSave(true);
  }

  public void frontRegisterSupplier(String ids, String name, String address) throws SupplierAlreadyExistException {
    _store.registerSupplier(ids,name,address);
    setSave(true);
  }

  public void frontRegisterSale (String idc, int dueDate, String idp, int qtd) throws NoSuchClientIdException, NotEnoughStockException, NosuchProductKeyException{
    _store.registerSale(idc,dueDate,idp,qtd);
    setSave(true);
  }

  public Supplier frontToggleSupplier(String IDS) throws SupplierDoesntExistException{
    Supplier s = _store.ToggleSupplier(IDS);
    setSave(true);
    return s;
  }
  //-----------------------PRODUCTS

  public void frontRegisterBox(String idp, int price, int valueCrit, String ids, String service) throws ProductAlreadyExistException, SupplierDoesntExistException, ServiceDoesntExistException {
    _store.registerBox(idp,price,valueCrit,ids,service);
    setSave(true);
  }

  public void frontRegisterContainer(String idp, int price, int valueCrit, String ids, String service, String serviceLevel) throws ProductAlreadyExistException, SupplierDoesntExistException, ServiceDoesntExistException, ServiceLvlDoesntExistException {
    _store.registerContainer(idp,price,valueCrit,ids,service,serviceLevel);
    setSave(true);
  }

  public void frontRegisterBook(String idp, String Tittle, String Author, String ISBN, String ids, int price, int valueCrit) throws ProductAlreadyExistException, SupplierDoesntExistException {
    _store.registerBook(idp,Tittle,Author,ISBN,ids,price,valueCrit);
    setSave(true);
  }

  public void frontChangePrice(String idp,int price){
    _store.ChangeProductPrice(idp,price);
    setSave(true);
  }


  public void frontRegisterOrder(String ids, TreeMap<String,Integer> produtos)throws SupplierDoesntExistException, InvalidStatusSupplierException, NosuchProductKeyException, InvalidSupplierException{
    _store.registerOrder(ids,produtos);
    setSave(true);
  }

  //-----------------------TRANSACTIONS

  public void frontDoPay(int idt) throws NoSuchTransactionIdException{
    _store.doPay(idt);
    setSave(true);
  }

  //--------------------------------------------------------
  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
    out.writeObject(_store);
    out.close();
    _save = false;

}

  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String file) throws UnavailableFileException, FileNotFoundException, IOException, ClassNotFoundException{
    ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
    _store = (Store) in.readObject();
    in.close();
    _filename = file;

  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile);
    }
    _save = true;

  }

}
