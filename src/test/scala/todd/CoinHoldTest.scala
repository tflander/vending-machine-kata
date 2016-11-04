package todd
import org.scalatest._

class CoinHoldTest extends FunSpec with ShouldMatchers {

  import Money._
    
  describe("coin count tests") {
    val coinCounts = CoinCounts(quarters=1, dimes=2, nickels=3)
    coinCounts.toCoins should be (Seq(quarter, dime, dime, nickel, nickel, nickel))
  }
  
  describe("generic coin hold tests") {
    
    it("accepts quarters") {
      val coinHold = new CoinHold()
      coinHold.addCoin(quarter)
      coinHold.coinCollection.totalAmount should be(25)
      coinHold.coinCollection.quarters should be(1)
      coinHold.coinCollection.nickels should be(0)
      coinHold.coinCollection.dimes should be(0)
    }
    
    it("accepts nickels") {
      val coinHold = new CoinHold()
      coinHold.addCoin(nickel)
      coinHold.coinCollection.totalAmount should be(5)
      coinHold.coinCollection.quarters should be(0)
      coinHold.coinCollection.nickels should be(1)
      coinHold.coinCollection.dimes should be(0)
    }
    
    it("accepts dimes") {
      val coinHold = new CoinHold()
      coinHold.addCoin(dime)
      coinHold.coinCollection.totalAmount should be(10)
      coinHold.coinCollection.quarters should be(0)
      coinHold.coinCollection.nickels should be(0)
      coinHold.coinCollection.dimes should be(1)
    }
    
    it("accepts multiple coins") {
      val coinHold = new CoinHold()
      coinHold.addCoin(quarter)
      coinHold.addCoin(dime)
      coinHold.addCoin(dime)
      coinHold.addCoin(nickel)
      coinHold.addCoin(nickel)
      coinHold.addCoin(nickel)
      coinHold.coinCollection.totalAmount should be(60)
      coinHold.coinCollection.quarters should be(1)
      coinHold.coinCollection.dimes should be(2)      
      coinHold.coinCollection.nickels should be(3)
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
}