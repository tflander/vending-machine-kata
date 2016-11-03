package todd

sealed trait CoinShape {}
case object ROUND extends CoinShape
case object NON_ROUND extends CoinShape

sealed trait CoinWithValue {
  def shape: CoinShape
  def size: Double
  def weight: Double
  def value: Int
  def name: String
  def coin = Coin(shape, size, weight)
}

case object Quarter extends CoinWithValue {
  val shape = ROUND
  val size = 24.26
  val weight = 5.67
  val value = 25
  val name = "Quarter"
}

case object Dime extends CoinWithValue {
  val shape = ROUND
  val size = 17.91
  val weight = 2.27
  val value = 10
  val name = "Dime"  
}

case object Nickel extends CoinWithValue {
  val shape = ROUND
  val size = 21.21
  val weight = 5.00
  val value = 5
  val name = "Nickel"  
}

object Money {
  val validCoins = Set(Quarter, Dime, Nickel)
  
  def validate(coin: Coin): Option[CoinWithValue] = {
    for (validCoin <- Money.validCoins) {
      if(coin.shape == validCoin.shape && coin.size == validCoin.size && coin.weight == validCoin.weight) {
        return Some(validCoin)
      }
    }
    return None
  }
  
}

case class Coin(shape: CoinShape, size: Double, weight: Double) {
  val coin = Money.validate(this)
  val valid = coin != None
  val value = if(valid) coin.get.value else 0
  val name = if(valid) coin.get.name else "Invalid"
}
