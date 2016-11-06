package todd
import Money._

class MoneyBox(initalCash: CoinHold = CoinHold(0,0,0)) {
 private val customerCoins = CoinHold(0,0,0)
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
 
 def insertedAmount = customerCoins.totalAmount
 
 def vaultAmount = coinVault.totalAmount
 
 def acceptCoinsAndReturnChangeForProductCosting(cost: Int): Int = {
   require (cost <= insertedAmount)
   coinVault +=  customerCoins
   val change = insertedAmount - cost
   customerCoins.clear()
   return change
 }
 
 def returnCoins(): Seq[Coin] = {
   val coinsToReturn = customerCoins.toCoins
   customerCoins.clear()
   return coinsToReturn
 }
 
  def releaseChange(amount: Int): Seq[Coin] = {
    return coinVault.removeAmountWithFewestCoins(amount)
  }
 
}