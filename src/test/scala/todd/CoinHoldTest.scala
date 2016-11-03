package todd
import org.scalatest._

class CoinHoldTest extends FunSpec with ShouldMatchers {

  import Money._
    
  describe("generic coin hold tests") {
    
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
  
  describe("coin slot tests") {
    it("accepts valid coins that are inserted") {
      val coinSlot = new CoinSlot()
      coinSlot.insert(quarter)
      coinSlot.insert(dime)
      coinSlot.insert(nickel)
      coinSlot.insertedAmount should be(40)
      coinSlot.rejectedCoins.isEmpty should be (true)      
    }
    
    it("rejects invalid coins") {
      val coinSlot = new CoinSlot()
      coinSlot.insert(penny)
      coinSlot.insertedAmount should be(0)
      coinSlot.rejectedCoins should be (Seq(penny))      
    }
    
    it("releases coins to purchase a product") {
      val coinSlot = new CoinSlot()
      coinSlot.insert(quarter)
      coinSlot.insert(dime)
      coinSlot.insert(nickel)
      coinSlot.insertedAmount should be(40)
      coinSlot.releaseCoinsForProductCosting(40) should be(CoinCounts(quarters=1, dimes=1, nickels=1))    
      coinSlot.insertedAmount should be(0)
    }
    
    // TODO: don't purchase with coins you don't have
  }
}