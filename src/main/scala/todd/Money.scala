package todd

sealed trait CoinShape {}
case object ROUND extends CoinShape
case object NON_ROUND extends CoinShape

sealed trait ValidCoin {
  def shape: CoinShape
  def size: Double
  def weight: Double
  def value: Int
  def name: String
}

case object Quarter extends ValidCoin {
  val shape = ROUND
  val size = 24.26
  val weight = 5.67
  val value = 25
  val name = "Quarter"  
}

case object Dime extends ValidCoin {
  val shape = ROUND
  val size = 17.91
  val weight = 2.27
  val value = 10
  val name = "Dime"  
}

case object Nickel extends ValidCoin {
  val shape = ROUND
  val size = 21.21
  val weight = 5.00
  val value = 5
  val name = "Nickel"  
}

object Money {
  val validCoins = Set(Quarter, Dime, Nickel)
  
  def validate(coin: Coin): Option[ValidCoin] = {
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
}
