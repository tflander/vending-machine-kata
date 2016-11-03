package todd

class VendingMachine {
  var display = "INSERT COIN"
  
  def insertCoin(coin: Coin) = {
    if(coin.valid) {
      display = "%01.2f".format((coin.value)/100.0)
    }
  }
}