package todd

class VendingMachine {
  var display = "INSERT COIN"
  
  def insertCoin(coin: Coin) = {
    display = "0.25"
  }
}