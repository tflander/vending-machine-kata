package todd
import Money._

class MoneyBox {
 private val coinHold = new CoinHold()
 val rejectedCoins = new scala.collection.mutable.ListBuffer[Coin]()
 var changeAmount = 0
 var coinVault = CoinCounts(0,0,0)
 
 def insert(coin: Coin) = {
    val coinWithValue = validate(coin)
    if (coinWithValue == None) {
      rejectedCoins += coin
    } else {
      coinHold.addCoin(coin)
    }
 }
 
 def insertedAmount = coinHold.coinCollection.totalAmount
 
 def vaultAmount = coinVault.totalAmount
 
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
 
  def addCoinsToCoinVault(coins: CoinCounts) = {
    coinVault.quarters += coins.quarters
    coinVault.dimes += coins.dimes
    coinVault.nickels += coins.nickels
  }
  
  def releaseChange(amount: Int): Seq[Coin] = {
    val change = new scala.collection.mutable.ListBuffer[Coin]()
    var remainder = amount
    while(coinVault.quarters > 0 && remainder >= quarter.value) {
      change += quarter
      remainder -= quarter.value
      coinVault.quarters -= 1
    }
    while(coinVault.dimes > 0 && remainder >= dime.value) {
      change += dime
      remainder -= dime.value
      coinVault.dimes -= 1
    }
    while(coinVault.nickels > 0 && remainder >= nickel.value) {
      change += nickel
      remainder -= nickel.value
      coinVault.nickels -= 1
    }
    return change
  }
 
}