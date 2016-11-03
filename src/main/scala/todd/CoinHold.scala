package todd
import Money._

class CoinHold {
  
  // TODO: remove coins and re-implement VendingMachine
  val coins = new scala.collection.mutable.ListBuffer[Coin]()

  var quarters = 0
  var dimes = 0
  var nickels = 0
  
  def addCoin(coin: Coin) = {
    val coinWithValue = validate(coin)
    coinWithValue match {
      case Some(goodCoin) => {
        goodCoin.value match {
          case quarter.value => quarters += 1
          case dime.value => dimes += 1
          case nickel.value => nickels += 1
        }
      }
      case None => { throw new IllegalArgumentException("The coin hold cannot accept invalid coins") }
    }
  }
  
  def totalAmount: Int = {
    quarters * quarter.value +
    dimes * dime.value +
    nickels * nickel.value
  }
}

class CoinSlot {
 val coinHold = new CoinHold()
 
 def insert(coin: Coin) = {
    val coinWithValue = validate(coin)
    if (coinWithValue != None) {
      coinHold.addCoin(coin)
    }
 }
 
 def insertedAmount = coinHold.totalAmount
}