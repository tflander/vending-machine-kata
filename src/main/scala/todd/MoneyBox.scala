package todd
import Money._

class MoneyBox(initalCash: CoinCounts = CoinCounts(0,0,0)) {
 private val customerCoins = new CoinHold()
 val rejectedCoins = new scala.collection.mutable.ListBuffer[Coin]()
 var coinVault = initalCash
 
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
 
 def acceptCoinsAndReturnChangeForProductCosting(cost: Int): Int = {
   require (cost <= insertedAmount)
   coinVault +=  customerCoins.coinCollection
   val change = insertedAmount - cost
   customerCoins.coinCollection.clear()
   return change
 }
 
 def returnCoins(): Seq[Coin] = {
   val coinsToReturn = customerCoins.coinCollection.toCoins
   customerCoins.coinCollection.clear()
   return coinsToReturn
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