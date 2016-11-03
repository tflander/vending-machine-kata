package todd

class VendingMachine {
  
  val coinReturn = new scala.collection.mutable.ListBuffer[Coin]()
  val dispensedProducts = new scala.collection.mutable.ListBuffer[Product]()
  var lastMessage: Option[String] = None
  var insertedAmount = 0
  
  private def penniesAsMoney(pennies: Int) = "%01.2f".format(pennies/100.0)
  
  def display: String = {
    if(lastMessage != None) {
      val msg = lastMessage.get
      lastMessage = None
      return msg
    }
    
    if(insertedAmount == 0) {
      return "INSERT COIN" 
    }
    
    return penniesAsMoney(insertedAmount)
  }
    
  def insertCoin(coin: Coin) = {
    if(coin.valid) {
      insertedAmount += coin.value
    } else {
      coinReturn += coin
    }
  }
  
  def selectProduct(product: Product) = {
    
    def dispenseChange() = {
      for(coin <- Money.validCoins) {
        while(insertedAmount >= coin.value) {
          println(coin.name)
          println(insertedAmount)
          insertedAmount -= coin.value
          coinReturn += coin.coin
        }
      }
    }
    
    if(insertedAmount < product.cost) {
      lastMessage = Some("PRICE $" + penniesAsMoney(product.cost))
    } else {
      insertedAmount -= product.cost
      dispensedProducts += product
      lastMessage = Some("THANK YOU")
      if(insertedAmount > 0) {
        dispenseChange()
      }
    }
  }
}