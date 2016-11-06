package todd
import Money._

class CoinHold(initialQuarters: Int, initialDimes: Int, initialNickels: Int) {
  
  private val cash = new scala.collection.mutable.HashMap[CoinWithValue, Int]()
  cash += Quarter -> initialQuarters
  cash += Dime -> initialDimes
  cash += Nickel -> initialNickels

  def quarters = cash.getOrElse(Quarter, throw new IllegalArgumentException("invalid coin"))
  def dimes = cash.getOrElse(Dime, throw new IllegalArgumentException("invalid coin"))
  def nickels = cash.getOrElse(Nickel, throw new IllegalArgumentException("invalid coin"))
  
  def totalAmount: Int = {
    quarters * quarter.value +
    dimes * dime.value +
    nickels * nickel.value
  }
  
  def clear() = {
    cash += Quarter -> 0
    cash += Dime -> 0
    cash += Nickel -> 0
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
    cash += Quarter -> (quarters + coins.quarters)
    cash += Dime -> (dimes + coins.dimes)
    cash += Nickel -> (nickels + coins.nickels)
  }
  
  def removeAmountWithFewestCoins (amount: Int): Seq[Coin] = {
    val change = new scala.collection.mutable.ListBuffer[Coin]()
    var remainder = amount
    while(quarters > 0 && remainder >= quarter.value) {
      change += quarter
      remainder -= quarter.value
      cash += Quarter -> (quarters - 1)
    }
    while(dimes > 0 && remainder >= dime.value) {
      change += dime
      remainder -= dime.value
      cash += Dime -> (dimes - 1)
    }
    while(nickels > 0 && remainder >= nickel.value) {
      change += nickel
      remainder -= nickel.value
      cash += Nickel -> (nickels - 1)
    }
    return change    
  }
  
  def addCoin(coin: Coin) = {
    val coinWithValue = validate(coin)
    coinWithValue match {
      case Some(goodCoin) => {
        goodCoin.value match {
          case quarter.value => cash += Quarter -> (quarters + 1)
          case dime.value => cash += Dime -> (dimes + 1)
          case nickel.value => cash += Nickel -> (nickels + 1)
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
