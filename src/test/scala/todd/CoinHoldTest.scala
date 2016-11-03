package todd
import org.scalatest._

class CoinHoldTest extends FunSpec with ShouldMatchers {

  import Money._
  
  describe("add coins") {
    
    it("accepts quarters") {
      val coinHold = new CoinHold()
      coinHold.insert(quarter)
      coinHold.insertedAmount should be(25)
      coinHold.quarters should be(1)
      coinHold.nickels should be(0)
      coinHold.dimes should be(0)
    }
    
    it("accepts nickels") {
      val coinHold = new CoinHold()
      coinHold.insert(nickel)
      coinHold.insertedAmount should be(5)
      coinHold.quarters should be(0)
      coinHold.nickels should be(1)
      coinHold.dimes should be(0)
    }
    
    it("accepts dimes") {
      val coinHold = new CoinHold()
      coinHold.insert(dime)
      coinHold.insertedAmount should be(10)
      coinHold.quarters should be(0)
      coinHold.nickels should be(0)
      coinHold.dimes should be(1)
    }
    
    it("accepts multiple coins") {
      val coinHold = new CoinHold()
      coinHold.insert(quarter)
      coinHold.insert(dime)
      coinHold.insert(dime)
      coinHold.insert(nickel)
      coinHold.insert(nickel)
      coinHold.insert(nickel)
      coinHold.insertedAmount should be(60)
      coinHold.quarters should be(1)
      coinHold.dimes should be(2)      
      coinHold.nickels should be(3)
    }
    
  }
}