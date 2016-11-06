package todd
import org.scalatest._

class CoinHoldTest extends FunSpec with ShouldMatchers {

  import Money._
    
  describe("coin count tests") {
    
    it("counts out coins") {
      val coinCounts = CoinHold(quarters=1, dimes=2, nickels=3)
      coinCounts.toCoins should be (Seq(quarter, dime, dime, nickel, nickel, nickel))
    }
    
    it("adds coin counts") {
      val coins = CoinHold(quarters=1, dimes=2, nickels=3) 
      coins += CoinHold(quarters=1, dimes=2, nickels=3) 
      coins should be(CoinHold(quarters=2, dimes=4, nickels=6))
    }
    
    it("subtracts coin counts") {
      val coins = CoinHold(quarters=1, dimes=2, nickels=3) 
      val coinsRemoved = coins.removeAmountWithFewestCoins(40) 
      coins should be(CoinHold(quarters=0, dimes=1, nickels=2))
      coinsRemoved should be(Seq(quarter, dime, nickel))
    }
  }
  
  describe("generic coin hold tests") {
    
    it("accepts quarters") {
      val coinHold = CoinHold(0,0,0)
      coinHold.addCoin(quarter)
      coinHold.totalAmount should be(25)
      coinHold.quarters should be(1)
      coinHold.nickels should be(0)
      coinHold.dimes should be(0)
    }
    
    it("accepts nickels") {
      val coinHold = CoinHold(0,0,0)
      coinHold.addCoin(nickel)
      coinHold.totalAmount should be(5)
      coinHold.quarters should be(0)
      coinHold.nickels should be(1)
      coinHold.dimes should be(0)
    }
    
    it("accepts dimes") {
      val coinHold = CoinHold(0,0,0)
      coinHold.addCoin(dime)
      coinHold.totalAmount should be(10)
      coinHold.quarters should be(0)
      coinHold.nickels should be(0)
      coinHold.dimes should be(1)
    }
    
    it("accepts multiple coins") {
      val coinHold = CoinHold(0,0,0)
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
      val coinHold = new CoinHold(0,0,0)
      intercept[IllegalArgumentException] { 
        coinHold.addCoin(penny)
      }
    }
    
  }
  
}