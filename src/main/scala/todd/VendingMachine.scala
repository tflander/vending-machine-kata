package todd

class VendingMachine {
  var display = "INSERT COIN"
  val coinReturn = new scala.collection.mutable.ListBuffer[Coin]()
  
  def insertCoin(coin: Coin) = {
    if(coin.valid) {
      display = "%01.2f".format((coin.value)/100.0)
    } else {
      coinReturn += coin
    }
  }
}