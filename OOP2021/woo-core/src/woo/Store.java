package woo;


import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import woo.exceptions.*;
import java.io.*;


/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Initialization of 3 TreeMaps for Clients, Suppliers and Products */
  private TreeMap<String,Client> _clients = new TreeMap<String,Client>();
  private TreeMap<String,Supplier> _suppliers = new TreeMap<String,Supplier>();
  private TreeMap<String,Product> _products = new TreeMap<String,Product>();
  private TreeMap<Integer,Transaction> _transactions = new TreeMap<Integer,Transaction>();

  private int _date = 0;
  private int _saldo = 0;

  private int _idt = 0;

  public Store() {
  }

  //---------DATA RELATED------------
  /**
   * Advances date by set ammount of days
   * @param d time meant to advance
   * @throws InvalidDateInputException if the date is a value < 0
   * @see woo.exceptions.InvalidDateInputException
   */
  public void advanceDate(int d) throws InvalidDateInputException {
    if (d >= 0)
      _date += d;
    else {
      throw new InvalidDateInputException(d);
    }
  }

  /**
   * Gets date 
   * @return int that is the date
   */
  public int getDate() {
    return _date;
  }

  public int getSaldo(){
    return _saldo;
  }

  public int getSaldoConta(){
    int saldo_temp = 0;
    
    for (Map.Entry<Integer,Transaction> entry : _transactions.entrySet()){
      if (entry.getValue().getPaid() == false){
        Sale s = (Sale) entry.getValue();
        saldo_temp += s.getRealPrice(_date);
      }
    }
    return saldo_temp + _saldo;
  }
  //-----------------------CLIENTS RELATED ---------------------------------

  /**
   * Register a Client
   * 
   * @param idc Client's id
   * @param name  Client's name
   * @param address Client's address
   * @throws ClientAlreadyExistException if there's a client with the same id
   * @see woo.exceptions.NoSuchClientIdException 
   * @return  Client created
   */

  public Client registerClient(String idc, String name, String address) throws ClientAlreadyExistException {
    if (_clients.get(idc) != null) {
      throw new ClientAlreadyExistException (idc);
    }
    Client client = new Client(idc,name,address);
    _clients.put(idc,client);
    return client; 
  }

  /**
   * Shows specific Client
   * 
   * @param idc Client id
   * @throws NoSuchClientIdException if the idc doesnt exist
   * @see woo.exceptions.NoSuchClientIdException
   * @return Client
   */

  public Client lookupClient(String idc) throws NoSuchClientIdException {
    Client client = _clients.get(idc);
    if (client == null)
      throw new NoSuchClientIdException(idc);
    return client;
  }

  /**
   * Shows all Clients
   * @return String with all Clients
   */

  public String showAllClients (){
    String s = "";
    for (Map.Entry<String,Client> entry : _clients.entrySet()){
      s += entry.getValue().toString() + "\n";
    }
    return s;
  }

  public String showClientTransactions(String idc) throws NoSuchClientIdException {
    Client client = lookupClient(idc);
    String s = "";
    if (client == null)
      throw new NoSuchClientIdException(idc);
    List<Sale> lista = client.getTransactions();
    for (Sale entry: lista){
      entry.setAuxDate(_date);
      s += entry.toString() + "\n";
    }
    return s;
  }
  

  //-----------------------SUPPLIERS RELATED ---------------------------------

  /**
   * Register Supplier
   *  
   * @param ids Supplier's id
   * @param name Supplier's name
   * @param address Supplier's address
   * @throws SupplierAlreadyExistException if the Supplier id already exist
   * @see woo.exceptions.SupplierAlreadyExistException 
   * @return Supplier created
   */

  public Supplier registerSupplier(String ids, String name, String address) throws SupplierAlreadyExistException {
    if ( _suppliers.get(ids) != null) {
      throw new SupplierAlreadyExistException (ids);
    }
    Supplier supplier = new Supplier (ids, name, address);
    _suppliers.put(ids,supplier);
    return supplier;
  }

  /**
   * 
   * @return Map of suppliers
   */

  public TreeMap<String, Supplier> getSuppliers() {
    return _suppliers;
  }

  public Supplier LookupSupplier(String ids){
    return _suppliers.get(ids);
  }

  public Supplier ToggleSupplier(String ids) throws SupplierDoesntExistException{
    if (LookupSupplier(ids) == null){
      throw new SupplierDoesntExistException (ids);
    }
    Supplier sup = _suppliers.get(ids);
    sup.toggle();
    return sup;
  }

  public String doShowSupplierTransactions(String ids) throws SupplierDoesntExistException{
    Supplier supplier = LookupSupplier(ids);
    String s = "";
    if (supplier == null)
      throw new SupplierDoesntExistException(ids);
    List<Order> lista = supplier.getTransactions();
    for (Order entry: lista){
      s += entry.toString() + "\n";
    }
    return s;
  }



  //----------------------------PRODUCTS RELATED-----------------------
  
  /**
   * Register Book
   * 
   * @param idp Product's id
   * @param Tittle Book's Tittle
   * @param Author Book's Author
   * @param ISBN Book's ISBN
   * @param ids Product's supplier id
   * @param price Product's price
   * @param valueCrit Product's Critical Value
   * @throws ProductAlreadyExistException if the product's id already exists
   * @see woo.exceptions.ProductAlreadyExistException
   * @return Book created
   */

  public Book registerBook(String idp, String Tittle, String Author, String ISBN, String ids, int price, int valueCrit) throws ProductAlreadyExistException, SupplierDoesntExistException {
    if (_products.get(idp) != null) {
      throw new ProductAlreadyExistException (idp);
    }
    if (_suppliers.get(ids) == null){
      throw new SupplierDoesntExistException(ids);
    }
    Book book = new Book (idp, Tittle, Author, ISBN, ids, price, valueCrit);
    _products.put(idp,book);
    return book;
  }

  /**
   * Register Box
   * 
   * @param idp Product's Id
   * @param price Product's price
   * @param valueCrit Product's critValue
   * @param ids Product's ids
   * @param service Box service
   * @throws ProductAlreadyExistException if the product's id already exists
   * @see ProductAlreadyExistException
   * @return created Box
   */
  public Box registerBox(String idp, int price, int valueCrit, String ids, String service) throws ProductAlreadyExistException, SupplierDoesntExistException, ServiceDoesntExistException {
    if(_products.get(idp) != null) {
      throw new ProductAlreadyExistException (idp);
    }
    if(_suppliers.get(ids)== null){
      throw new SupplierDoesntExistException(ids);
    }
    if(! (service.matches("NORMAL|AIR|EXPRESS|PERSONAL"))){
      throw new ServiceDoesntExistException(service);
    }
    Box box = new Box (idp, price, valueCrit, ids, service);
    _products.put(idp,box);
    return box;
  }
  /**
   * Register's Container
   * 
   * @param idp Product's id
   * @param price Product's price
   * @param valueCrit Product's critValue
   * @param ids Product's supplier Id
   * @param service Container's service
   * @param serviceLevel Container's service level
   * @throws ProductAlreadyExistException if the product's id already exists
   * @see ProductAlreadyExistException
   * @return created Container
   */

  public Container registerContainer(String idp, int price, int valueCrit, String ids, String service, String serviceLevel) throws ProductAlreadyExistException, SupplierDoesntExistException, ServiceDoesntExistException, ServiceLvlDoesntExistException{
    if(_products.get(idp) != null) {
      throw new ProductAlreadyExistException (idp);
    }
    if(_suppliers.get(ids)== null){
      throw new SupplierDoesntExistException(ids);
    }
    if(! (service.matches("NORMAL|AIR|EXPRESS|PERSONAL"))){
      throw new ServiceDoesntExistException(service);
    }
    if (! (serviceLevel.matches("B4|C4|C5|DL"))){
      throw new ServiceLvlDoesntExistException(serviceLevel);
    }
    Container container = new Container (idp, price, valueCrit, ids, service, serviceLevel);
    _products.put(idp,container);
    return container;
  }
/**
 * Shows all products (books,boxes and containers)
 * 
 * @return string with all the products
 */

  public String showAllProducts(){
    String s = "";
    for (Map.Entry<String,Product> entry : _products.entrySet()){
      s += entry.getValue().toString() + "\n";
    }
    return s;
  }


  public void ChangeProductPrice(String idp,int price){
    if (_products.get(idp) != null){
      if (price > 0){
        Product p = _products.get(idp);
        p.setPrice(price);
      }
    }
  }
  //----------------------------LOOKUPS RELATED-----------------------

  public Product LookupProduct(String idp){
    return _products.get(idp);
  }

  public String showProductsUnderTopPrice(int price){
    String s= "";
    for (Map.Entry<String,Product> entry : _products.entrySet()){
      if (entry.getValue().getPrice() < price){
        s += entry.getValue().toString() + "\n";
      }
    }
    return s;
  }

  public String doLookupPaymentsByClient(String idc) throws NoSuchClientIdException {
    Client client = lookupClient(idc);
    String s = "";
    if (client == null)
      throw new NoSuchClientIdException(idc);
    List<Sale> lista = client.getTransactions();
    for (Sale entry: lista){
      
      if (entry.getPaid() == true){
        entry.setAuxDate(_date);
        s += entry.toString() + "\n";
      }
    }
    return s;
  }




  //----------------------------TRANSACTIONS RELATED-----------------------

  public Sale registerSale(String idc, int dueDate, String idp, int qtd) throws NoSuchClientIdException, NotEnoughStockException, NosuchProductKeyException{
    if (lookupClient(idc) == null)
      throw new NoSuchClientIdException(idc);
    if(LookupProduct(idp)== null)
      throw new NosuchProductKeyException(idp);
    if(LookupProduct(idp).getStock() < qtd)
      throw new NotEnoughStockException(qtd, LookupProduct(idp).getStock());
    
    Sale sale = new Sale (lookupClient(idc), dueDate, LookupProduct(idp), qtd,_idt);
    _transactions.put(sale.getIdt(),sale);
    int price = _products.get(idp).getPrice() * qtd;
    lookupClient(idc).addTransaction(sale);
    sale.setPrice(price);
    _idt++;
    return sale;
  }


  public Order registerOrder(String ids, TreeMap<String,Integer> produtos) throws SupplierDoesntExistException, InvalidStatusSupplierException, NosuchProductKeyException, InvalidSupplierException{
    int price = 0;
    if (LookupSupplier(ids) == null){
      throw new SupplierDoesntExistException(ids);
    }
    if (LookupSupplier(ids).getStatus() == false){ //Verificar o supplier
      throw new InvalidStatusSupplierException(ids);
    }
    TreeMap<String,Aux_T> produ = new TreeMap<String,Aux_T>();
    for (Map.Entry<String,Integer> entry : produtos.entrySet()){ //Percorrer a lista dada
      Product p = LookupProduct(entry.getKey());
      if (p == null){
        throw new NosuchProductKeyException(entry.getKey()); //Verificar se o produto existe
      }
      if (! p.getSupplier().equals(ids)){
        throw new InvalidSupplierException(ids,entry.getKey()); //Verificar se o supplier e igual ao dado 
      }
      price += p.getPrice()*entry.getValue(); //Atualizar preço da encomenda
      Aux_T t = new Aux_T(p,entry.getValue()); //Meter na lista do Aux_t
      produ.put(entry.getKey(),t);
    }
    Order order = new Order(ids,produ,price,getDate(),_idt); //data
    _transactions.put(order.getIdt(),order);
    LookupSupplier(ids).addTransaction(order);
    _saldo-= price;
    _idt++;
    return order;

  }

  public Transaction lookupTransaction(int idt) throws NoSuchTransactionIdException {
    Transaction transaction = _transactions.get(idt);
    if (transaction == null)
      throw new NoSuchTransactionIdException(idt);
    transaction.setAuxDate(_date);
    return transaction;
  }

  public void doPay(int idt) throws NoSuchTransactionIdException{
    Transaction transaction = lookupTransaction(idt);
    if (transaction == null)
      throw new NoSuchTransactionIdException(idt);
    transaction.payUp(_date);
    _saldo +=transaction.getRealPrice();
  }

  //----------------------------  IMPORT  -----------------------


  /**
   * Imports Clients, Suppliers, Products (Book,Box,Container) from a file
   * 
   * @param txtfile its the name of the file to import
   * @throws ImportFileException reading error
   * @throws IOException 
   * @throws BadEntryException
   * @see woo.exceptions.ImportFileException
   */

  void importFile(String txtfile) throws IOException, BadEntryException, ImportFileException {
    int lineno = 0;
    try {
      BufferedReader in = new BufferedReader(new FileReader(txtfile));
      String s;

      while ((s = in.readLine()) != null) {
        String line = new String(s.getBytes(), "UTF-8");
        lineno++;
        String[] split = line.split("\\|");
        if (split[0].equals("SUPPLIER")) {
          try{
            registerSupplier(split[1],split[2],split[3]);
          }
          catch (SupplierAlreadyExistException e) {
            System.err.println(e);
          }
        } 
        else if (split[0].equals("CLIENT")) {
          try{
            registerClient(split[1],split[2],split[3]);
          }
          catch(ClientAlreadyExistException e){
            System.err.println(e);
          }
        } 
        else if (split[0].equals("BOX")) {
          try{
            Box box = registerBox(split[1],Integer.parseInt(split[4]),Integer.parseInt(split[5]),split[3],split[2]);
            box.setStock(Integer.parseInt(split[6]));
          }
          catch (ProductAlreadyExistException e) {
            System.err.println(e);
          }
          catch (SupplierDoesntExistException e){
            System.err.println(e);
          }
          catch (ServiceDoesntExistException e){
            System.err.println(e);
          }
        } 
        else if (split[0].equals("CONTAINER")) {
          try{
            Container container = registerContainer(split[1],Integer.parseInt(split[5]),Integer.parseInt(split[6]),split[4],split[2],split[3]);
            container.setStock(Integer.parseInt(split[7]));
          }
          catch (ProductAlreadyExistException e) {
            System.err.println(e);
          }
          catch (SupplierDoesntExistException e){
            System.err.println(e);
          }
          catch (ServiceDoesntExistException e){
            System.err.println(e);
          }
          catch (ServiceLvlDoesntExistException e){
            System.err.println(e);
          }
        } 
        else if (split[0].equals("BOOK")) {
          try {
            Book book = registerBook(split[1],split[2],split[3],split[4],split[5],Integer.parseInt(split[6]),Integer.parseInt(split[7]));
            book.setStock(Integer.parseInt(split[8]));
          }
          catch (ProductAlreadyExistException e) {
            System.err.println(e);
          }
          catch (SupplierDoesntExistException e){
            System.err.println(e);
          }
        }
      }
      in.close();
    } catch (FileNotFoundException e){
      System.out.println("File not found:" + txtfile + ":" + e);
    } catch (IOException e){
      System.out.println("IO error:" + txtfile + ":" + lineno + ": line " + e);
    }
  }

}

