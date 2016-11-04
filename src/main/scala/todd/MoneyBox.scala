package todd
import Money._

class MoneyBox {
 private val customerCoins = new CoinHold()
 val rejectedCoins = new scala.collection.mutable.ListBuffer[Coin]()
 var changeAmount = 0
 var coinVault = CoinCounts(0,0,0)
 
 def insert(coin: Coin) = {
    val coinWithValue = validate(coin)
    if (coinWithValue == None) {
      rejectedCoins += coin
    } else {
      customerCoins.addCoin(coin)
    }
 }
 
 def insertedAmount = customerCoins.coinCollection.totalAmount
 
 def vaultAmount = coinVault.totalAmount
 
 def releaseCoinsForProductCosting(cost: Int) = {
   require (cost <= insertedAmount)
   
   changeAmount = insertedAmount - cost
   
   val coins = customerCoins.coinCollection 
   coinVault.quarters += coins.quarters
   coinVault.dimes += coins.dimes
   coinVault.nickels += coins.nickels
   customerCoins.coinCollection.clear()
 }
 
 def returnCoins(): Seq[Coin] = {
   val coinsToReturn = customerCoins.coinCollection.toCoins
   customerCoins.coinCollection.clear()
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