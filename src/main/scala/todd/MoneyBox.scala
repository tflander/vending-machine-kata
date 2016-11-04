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
    return coinVault.removeAmountWithFewestCoins(amount)
  }
 
}