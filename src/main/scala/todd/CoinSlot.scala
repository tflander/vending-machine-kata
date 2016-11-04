package todd
import Money._

class CoinSlot {
 private val coinHold = new CoinHold()
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
 
 def returnCoins(): Seq[Coin] = {
   val coinsToReturn = coinHold.coinCollection.toCoins
   coinHold.coinCollection.clear()
   return coinsToReturn
 }
}