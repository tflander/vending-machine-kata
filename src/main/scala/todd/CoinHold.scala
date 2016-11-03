package todd
import Money._

case class CoinCounts(var quarters: Int, var dimes: Int, var nickels: Int)

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
 val rejectedCoins = new scala.collection.mutable.ListBuffer[Coin]()
 
 def insert(coin: Coin) = {
    val coinWithValue = validate(coin)
    if (coinWithValue == None) {
      rejectedCoins += coin
    } else {
      coinHold.addCoin(coin)
    }
 }
 
 def insertedAmount = coinHold.totalAmount
 
 def purchaseProductCosting(cost: Int): CoinCounts = {
   val coinsUsedToPurchase = CoinCounts(0,0,0)
   var remaining = cost
   while (remaining >= quarter.value) {
     remaining -= quarter.value
     coinsUsedToPurchase.quarters += 1
     coinHold.quarters -= 1
   }
   while (remaining >= dime.value) {
     remaining -= dime.value
     coinsUsedToPurchase.dimes += 1
     coinHold.dimes -= 1
   }
   while (remaining >= nickel.value) {
     remaining -= nickel.value
     coinsUsedToPurchase.nickels += 1
     coinHold.nickels -= 1
   }
   
   return coinsUsedToPurchase
 }
}