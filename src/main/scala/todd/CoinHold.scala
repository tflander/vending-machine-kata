package todd
import Money._

case class CoinCounts(var quarters: Int, var dimes: Int, var nickels: Int) {
  def totalAmount: Int = {
    quarters * quarter.value +
    dimes * dime.value +
    nickels * nickel.value
  }
  
  def clear() = {
    quarters=0
    dimes=0
    nickels=0
  }
  
  def toCoins: Seq[Coin] = {
    val coins = new scala.collection.mutable.ListBuffer[Coin]
    for(i <- 1 to quarters) {
      coins += quarter
    }
    for(i <- 1 to dimes) {
      coins += dime
    }
    for(i <- 1 to nickels) {
      coins += nickel
    }
    return coins
  }
  
}

class CoinHold {
  
  val coinCollection = CoinCounts(0,0,0)
  
  def addCoin(coin: Coin) = {
    val coinWithValue = validate(coin)
    coinWithValue match {
      case Some(goodCoin) => {
        goodCoin.value match {
          case quarter.value => coinCollection.quarters += 1
          case dime.value => coinCollection.dimes += 1
          case nickel.value => coinCollection.nickels += 1
        }
      }
      case None => { throw new IllegalArgumentException("The coin hold cannot accept invalid coins") }
    }
  }
  
}

class CoinSlot {
 val coinHold = new CoinHold()
 val rejectedCoins = new scala.collection.mutable.ListBuffer[Coin]()
 var changeAmount = 0
 
 def insert(coin: Coin) = {
    val coinWithValue = validate(coin)
    if (coinWithValue == None) {
      rejectedCoins += coin
    } else {
      coinHold.addCoin(coin)
    }
 }
 
 def insertedAmount = coinHold.coinCollection.totalAmount
 
 def releaseCoinsForProductCosting(cost: Int): CoinCounts = {
   val coins = coinHold.coinCollection 
   if (cost > coins.totalAmount) {
     return CoinCounts(0,0,0)
   }
   
   changeAmount = coins.totalAmount - cost
   val coinsReleased = CoinCounts(coins.quarters, coins.dimes, coins.nickels)
   coinHold.coinCollection.clear()
   return coinsReleased
 }
}