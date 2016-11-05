package todd

class VendingMachine(initialColas: Int = 10, var initialChips: Int = 10, var initialCandy: Int = 10)  {
  
  val inventory = new Inventory(initialColas, initialChips, initialCandy)
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
  
  def selectProduct(product: Product): Unit = {
    
    if(product.cost > moneyBox.insertedAmount) {
      lastMessage = Some("PRICE $" + penniesAsMoney(product.cost))      
    } else {
      product.name match {
        case "Cola" => {
          if(inventory.isSoldOut(Cola)) {
            lastMessage = Some("SOLD OUT")
            return
          } else {
            inventory.removeOne(Cola)
          }
        }
        case "Chips" => {
          if(inventory.isSoldOut(Chips)) {
            lastMessage = Some("SOLD OUT")
            return
          } else {
            inventory.removeOne(Chips)
          }
        }
        case "Candy" => {
          if(inventory.isSoldOut(Candy)) {
            lastMessage = Some("SOLD OUT")
            return
          } else {
            inventory.removeOne(Candy)
          }
        }
      }
      
      lastMessage = Some("THANK YOU")      
      val change = moneyBox.acceptCoinsAndReturnChangeForProductCosting(product.cost)      
      dispensedProducts += product
      coinReturn ++= moneyBox.releaseChange(change)
      
    }
    
  }
  
  def pressCoinReturn() = {
    coinReturn ++= moneyBox.returnCoins()
  }
}