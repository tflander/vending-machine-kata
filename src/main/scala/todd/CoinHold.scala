package todd

class CoinHold {
  
  // TODO: remove coins and re-implement VendingMachine
  val coins = new scala.collection.mutable.ListBuffer[Coin]()

  var quarters = 0
  
  def insert(coin: Coin) = {
    quarters += 1
  }
  
  def insertedAmount: Int = {
    quarters * 25
  }
}