package todd
import Money._

class CoinHold(var quarters: Int, var dimes: Int, var nickels: Int) {
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
  
  def +=(coins: CoinHold) = {
    quarters += coins.quarters
    dimes += coins.dimes
    nickels += coins.nickels
  }
  
  def removeAmountWithFewestCoins (amount: Int): Seq[Coin] = {
    val change = new scala.collection.mutable.ListBuffer[Coin]()
    var remainder = amount
    while(quarters > 0 && remainder >= quarter.value) {
      change += quarter
      remainder -= quarter.value
      quarters -= 1
    }
    while(dimes > 0 && remainder >= dime.value) {
      change += dime
      remainder -= dime.value
      dimes -= 1
    }
    while(nickels > 0 && remainder >= nickel.value) {
      change += nickel
      remainder -= nickel.value
      nickels -= 1
    }
    return change    
  }
  
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

  def canEqual(a: Any) = a.isInstanceOf[CoinHold]
      override def equals(that: Any): Boolean =
          that match {
              case that: CoinHold => that.canEqual(this) && this.hashCode == that.hashCode
              case _ => false
       }
      override def hashCode: Int = {
          return 31 * quarters + 37 * dimes + 41 + nickels 
      }  
  }
