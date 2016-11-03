package todd
import org.scalatest._

class CoinHoldTest extends FunSpec with ShouldMatchers {

  val quarter = Coin(ROUND, size=24.26, weight=5.67)
  val dime = Coin(ROUND, size=17.91, weight=2.27)
  val nickel = Coin(ROUND, size=21.21, weight=5.00)
  val penny = Coin(ROUND, size=19.05, weight=2.50)
  
  describe("add coins") {
    
    it("accepts quarters") {
      val coinHold = new CoinHold()
      coinHold.insert(quarter)
      coinHold.insertedAmount should be(25)
      coinHold.quarters should be(1)
    }
    
  }
}