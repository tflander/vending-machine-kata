package todd

class CoinHold {
  
  // TODO: remove coins and re-implement VendingMachine
  val coins = new scala.collection.mutable.ListBuffer[Coin]()

  import Money._
  var quarters = 0
  var dimes = 0
  var nickels = 0
  
  def insert(coin: Coin) = {
    val coinWithValue = validate(coin)
    coinWithValue match {
      case Some(goodCoin) => {
        goodCoin.value match {
          case quarter.value => quarters += 1
          case dime.value => dimes += 1
          case nickel.value => nickels += 1
        }
      }
      case None => {}
    }
  }
  
  def insertedAmount: Int = {
    quarters * quarter.value +
    dimes * dime.value +
    nickels * nickel.value
  }
}