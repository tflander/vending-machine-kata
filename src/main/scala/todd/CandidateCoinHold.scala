package todd
import Money._

class CandidateCoinHold(initialQuarters: Int, initialDimes: Int, initialNickels: Int) {
  
  private val cash = new scala.collection.mutable.HashMap[CoinWithValue, Int]()
  cash += Quarter -> initialQuarters
  cash += Dime -> initialDimes
  cash += Nickel -> initialNickels

  private def getCountForCoin(coin: CoinWithValue) = cash.getOrElse(coin, throw new IllegalArgumentException("invalid coin"))
  
  def quarters = getCountForCoin(Quarter)
  def dimes = getCountForCoin(Dime)
  def nickels = getCountForCoin(Nickel)
  
  private def applyToCash(f: Set[(Coin, Int)] => Any): Any = {
    
    for(coinAndCount: (CoinWithValue, Int) <- cash) {
    }
    
//    val coinsAndCounts = for(coin <- validCoins) yield (coin.coin, getCountForCoin(coin))
//    return f(coinsAndCounts)
  }
  
  def totalAmount: Int = {
    
    def accumulateCoinValues(data: Set[(Coin, Int)]) = data.foldLeft(0)((accumulatedTotal, coinAndCount) => { 
      val coinValue = coinAndCount._1.value
      val coinCount = coinAndCount._2
      (accumulatedTotal + (coinValue * coinCount))
    })
    
    return applyToCash(accumulateCoinValues).asInstanceOf[Int]

  }
  
  def clear() = {
    
    def zeroOutCoinValues(data: Set[(Coin, Int)]) = for(datum <- data) {
      cash += (datum._1.coin.get -> 0)
    }
    applyToCash(zeroOutCoinValues)
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
