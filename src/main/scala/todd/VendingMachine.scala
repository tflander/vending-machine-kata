package todd

class VendingMachine(var numColas: Int = 10, var numChips: Int = 10, var numCandy: Int = 10)  {
  
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
      val change = moneyBox.acceptCoinsAndReturnChangeForProductCosting(product.cost)
      lastMessage = Some("THANK YOU")
      coinReturn ++= moneyBox.releaseChange(change)
      product.name match {
        case "Cola" => {
          if(numColas == 0) {
            return
          } else {
            numColas -= 1
          }
        }
        case "Chips" => {
          if(numChips == 0) {
            return
          } else {
            numChips -= 1
          }
        }
        case "Candy" => {
          if(numCandy == 0) {
            return
          } else {
            numCandy -= 1
          }
        }
      }
      
      dispensedProducts += product
      
    }
    
  }
  
  def pressCoinReturn() = {
    coinReturn ++= moneyBox.returnCoins()
  }
}