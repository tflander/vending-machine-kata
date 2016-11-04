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
  
}