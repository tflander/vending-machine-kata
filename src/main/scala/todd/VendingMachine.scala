package todd

class VendingMachine {
  
  val coinReturn = new scala.collection.mutable.ListBuffer[Coin]()
  val dispensedProducts = new scala.collection.mutable.ListBuffer[Product]()
  val coinSlot = new CoinSlot
  var lastMessage: Option[String] = None
  
  private def penniesAsMoney(pennies: Int) = "%01.2f".format(pennies/100.0)
  
  def display: String = {
    if(lastMessage != None) {
      val msg = lastMessage.get
      lastMessage = None
      return msg
    }
    
    if(coinSlot.coinHold.coinCollection.totalAmount == 0) {
      return "INSERT COIN" 
    }
    
    return penniesAsMoney(coinSlot.coinHold.coinCollection.totalAmount)
  }
    
  def insertCoin(coin: Coin) = {
    coinSlot.insert(coin)
    coinReturn.clear()
    coinReturn ++= coinSlot.rejectedCoins
  }
  
  def selectProduct(product: Product) = {
    
    val releasedCoins = coinSlot.releaseCoinsForProductCosting(product.cost)
    if(releasedCoins.totalAmount == 0) {
      lastMessage = Some("PRICE $" + penniesAsMoney(product.cost))
    } else {
      dispensedProducts += product
      lastMessage = Some("THANK YOU")
      
      // dispense change
      for(coin <- Money.validCoins) {
        while(coinSlot.changeAmount >= coin.value) {
          coinSlot.changeAmount -= coin.value
          coinReturn += coin.coin
        }
      }
    }
    
  }
  
  def pressCoinReturn() = {
    // TODO: move to coin slot
    import Money._
    val a = coinSlot.coinHold.coinCollection
    println(a)
    for(i <- 1 to a.quarters) {
      println("adding quarter")
      coinReturn += quarter
    }
    for(i <- 1 to a.dimes) {
      println("adding dime")
      coinReturn += dime
    }
    for(i <- 1 to a.nickels) {
      println("adding nickel")
      coinReturn += nickel
    }
    coinSlot.coinHold.coinCollection.clear()
  }
}