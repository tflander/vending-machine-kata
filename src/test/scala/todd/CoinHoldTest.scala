package todd
import org.scalatest._

class CoinHoldTest extends FunSpec with ShouldMatchers {

  import Money._
    
  describe("add coins") {
    
    it("accepts quarters") {
      val coinHold = new CoinHold()
      coinHold.addCoin(quarter)
      coinHold.totalAmount should be(25)
      coinHold.quarters should be(1)
      coinHold.nickels should be(0)
      coinHold.dimes should be(0)
    }
    
    it("accepts nickels") {
      val coinHold = new CoinHold()
      coinHold.addCoin(nickel)
      coinHold.totalAmount should be(5)
      coinHold.quarters should be(0)
      coinHold.nickels should be(1)
      coinHold.dimes should be(0)
    }
    
    it("accepts dimes") {
      val coinHold = new CoinHold()
      coinHold.addCoin(dime)
      coinHold.totalAmount should be(10)
      coinHold.quarters should be(0)
      coinHold.nickels should be(0)
      coinHold.dimes should be(1)
    }
    
    it("accepts multiple coins") {
      val coinHold = new CoinHold()
      coinHold.addCoin(quarter)
      coinHold.addCoin(dime)
      coinHold.addCoin(dime)
      coinHold.addCoin(nickel)
      coinHold.addCoin(nickel)
      coinHold.addCoin(nickel)
      coinHold.totalAmount should be(60)
      coinHold.quarters should be(1)
      coinHold.dimes should be(2)      
      coinHold.nickels should be(3)
    }
    
    it("fails to accept invalid coins") {
      val coinHold = new CoinHold()
      intercept[IllegalArgumentException] { 
        coinHold.addCoin(penny)
      }
    }
    
  }
}