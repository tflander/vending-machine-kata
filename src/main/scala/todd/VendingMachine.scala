package todd

class VendingMachine {
  var display = "INSERT COIN"
  val coinReturn = new scala.collection.mutable.ListBuffer[Coin]()
  var insertedAmount = 0
  
  def insertCoin(coin: Coin) = {
    if(coin.valid) {
      insertedAmount += coin.value
      display = "%01.2f".format(insertedAmount/100.0)
    } else {
      coinReturn += coin
    }
  }
}