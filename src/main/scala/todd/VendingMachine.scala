package todd

class VendingMachine {
  
  val coinReturn = new scala.collection.mutable.ListBuffer[Coin]()
  val dispensedProducts = new scala.collection.mutable.ListBuffer[Product]()
  val moneyBox = new MoneyBox
  var lastMessage: Option[String] = None
  
  private def penniesAsMoney(pennies: Int) = "%01.2f".format(pennies/100.0)
  
  def display: String = {
    if(lastMessage != None) {
      val msg = lastMessage.get
      lastMessage = None
      return msg
    }
    
    if(moneyBox.insertedAmount == 0) {
      return "INSERT COIN" 
    }
    
    return penniesAsMoney(moneyBox.insertedAmount)
  }
    
  def insertCoin(coin: Coin) = {
    moneyBox.insert(coin)
    coinReturn.clear()
    coinReturn ++= moneyBox.rejectedCoins
  }
  
  def selectProduct(product: Product) = {
    
    if(product.cost > moneyBox.insertedAmount) {
      lastMessage = Some("PRICE $" + penniesAsMoney(product.cost))      
    } else {
      moneyBox.acceptCoinsAndReturnChangeForProductCosting(product.cost)
      dispensedProducts += product
      lastMessage = Some("THANK YOU")
      coinReturn ++= moneyBox.releaseChange(moneyBox.changeAmount)
      moneyBox.changeAmount = 0
    }
    
  }
  
  def pressCoinReturn() = {
    coinReturn ++= moneyBox.returnCoins()
  }
}