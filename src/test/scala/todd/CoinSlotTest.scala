package todd
import org.scalatest._
import Money._

class CoinSlotTest extends FunSpec with ShouldMatchers {
  
  describe("insert coin tests") {
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
  }
  
  describe("coin release tests") {
    
    it("releases coins to purchase a product") {
      val coinSlot = new CoinSlot()
      coinSlot.insert(quarter)
      coinSlot.insert(dime)
      coinSlot.insert(nickel)
      coinSlot.insertedAmount should be(40)
      coinSlot.releaseCoinsForProductCosting(40) should be(CoinCounts(quarters=1, dimes=1, nickels=1))    
      coinSlot.changeAmount should be(0)
      coinSlot.insertedAmount should be(0)
    }
    
    it("releases coins to purchase a product using available coins") {
      val coinSlot = new CoinSlot()
      coinSlot.insert(dime)
      coinSlot.insert(dime)
      coinSlot.insert(dime)
      coinSlot.insert(nickel)
      coinSlot.insert(nickel)
      coinSlot.insertedAmount should be(40)
      coinSlot.releaseCoinsForProductCosting(40) should be(CoinCounts(quarters=0, dimes=3, nickels=2))    
      coinSlot.changeAmount should be(0)
      coinSlot.insertedAmount should be(0)
    }
    
    it("releases coins to purchase a product only when funds are available") {
      val coinSlot = new CoinSlot()
      coinSlot.insert(dime)
      coinSlot.insert(dime)
      coinSlot.insert(dime)
      coinSlot.insert(nickel)
      coinSlot.insertedAmount should be(35)
      coinSlot.releaseCoinsForProductCosting(40) should be(CoinCounts(quarters=0, dimes=0, nickels=0))    
      coinSlot.changeAmount should be(0)
      coinSlot.insertedAmount should be(35)
    }
  }
  
  describe("sundry tests") {
    
    it("calculates change required to return to the customer") {
      val coinSlot = new CoinSlot()
      coinSlot.insert(quarter)
      coinSlot.insert(dime)
      coinSlot.insert(dime)
      coinSlot.insertedAmount should be(45)
      coinSlot.releaseCoinsForProductCosting(40) should be(CoinCounts(quarters=1, dimes=2, nickels=0))
      coinSlot.changeAmount should be(5)
      coinSlot.insertedAmount should be(0)      
    }
    
    it("returns coins") {
      val coinSlot = new CoinSlot()
      coinSlot.insert(quarter)
      coinSlot.insert(dime)
      coinSlot.insert(dime)
      coinSlot.insertedAmount should be(45)
      coinSlot.returnCoins should be(Seq(quarter, dime, dime))
      coinSlot.changeAmount should be(0)
      coinSlot.insertedAmount should be(0)            
    }
  }
  
  describe("coin vault tests") {
    it("get requested amount from the coin vault for making change") {
      val coinSlot = new CoinSlot()
      coinSlot.addCoinsToCoinVault(CoinCounts(quarters=0, dimes=2, nickels=2))
      coinSlot.vaultAmount should be(30)
      // TODO: finish test
    }
  }
}