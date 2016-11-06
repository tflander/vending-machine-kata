package todd

class VendingMachine(initialColas: Int = 10, initialChips: Int = 10, initialCandy: Int = 10, initialCash: CoinCounts = CoinCounts(0,5,10))  {
  
  val inventory = new Inventory(initialColas, initialChips, initialCandy)
  val coinReturn = new scala.collection.mutable.ListBuffer[Coin]()
  val dispensedProducts = new scala.collection.mutable.ListBuffer[Product]()
  val moneyBox = new MoneyBox(initialCash)
  var lastMessage: Option[String] = None
  
  private def penniesAsMoney(pennies: Int) = "%01.2f".format(pennies/100.0)
  
  def display: String = {
    if(lastMessage != None) {
      val msg = lastMessage.get
      lastMessage = None
      return msg
    }
    
    if(moneyBox.insertedAmount == 0) {
      if(moneyBox.coinVault.totalAmount < 20 || moneyBox.coinVault.nickels == 0) {
        return "EXACT CHANGE ONLY"
      } else {
        return "INSERT COIN"
      }
    }
    
    return penniesAsMoney(moneyBox.insertedAmount)
  }
    
  def insertCoin(coin: Coin) = {
    moneyBox.insert(coin)
    coinReturn.clear()
    coinReturn ++= moneyBox.rejectedCoins
  }
  
  def selectProduct(product: Product): Unit = {
    
      if(inventory.isSoldOut(product)) {
         lastMessage = Some("SOLD OUT")
         return        
      }

      if(product.cost > moneyBox.insertedAmount) {
        lastMessage = Some("PRICE $" + penniesAsMoney(product.cost))
        return
      }
      
      inventory.removeOne(product)
      lastMessage = Some("THANK YOU")      
      val change = moneyBox.acceptCoinsAndReturnChangeForProductCosting(product.cost)      
      dispensedProducts += product
      coinReturn ++= moneyBox.releaseChange(change)
    
  }
  
  def pressCoinReturn() = {
    coinReturn ++= moneyBox.returnCoins()
  }
}