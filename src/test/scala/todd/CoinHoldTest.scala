package todd
import org.scalatest._

class CoinHoldTest extends FunSpec with ShouldMatchers {

  import Money._
    
  describe("coin count tests") {
    
    it("counts out coins") {
      val coinCounts = new CoinHold(initialQuarters=1, initialDimes=2, initialNickels=3)
      coinCounts.toCoins should be (Seq(quarter, dime, dime, nickel, nickel, nickel))
    }
    
    it("adds coin counts") {
      val coins = new CoinHold(initialQuarters=1, initialDimes=2, initialNickels=3) 
      coins += new CoinHold(initialQuarters=1, initialDimes=2, initialNickels=3) 
      coins should be(new CoinHold(initialQuarters=2, initialDimes=4, initialNickels=6))
    }
    
    it("subtracts coin counts") {
      val coins = new CoinHold(initialQuarters=1, initialDimes=2, initialNickels=3) 
      val coinsRemoved = coins.removeAmountWithFewestCoins(40) 
      coins should be(new CoinHold(initialQuarters=0, initialDimes=1, initialNickels=2))
      coinsRemoved should be(Seq(quarter, dime, nickel))
    }
  }
  
  describe("generic coin hold tests") {
    
    it("accepts quarters") {
      val coinHold = new CoinHold(0,0,0)
      coinHold.addCoin(quarter)
      coinHold.totalAmount should be(25)
      coinHold.quarters should be(1)
      coinHold.nickels should be(0)
      coinHold.dimes should be(0)
    }
    
    it("accepts nickels") {
      val coinHold = new CoinHold(0,0,0)
      coinHold.addCoin(nickel)
      coinHold.totalAmount should be(5)
      coinHold.quarters should be(0)
      coinHold.nickels should be(1)
      coinHold.dimes should be(0)
    }
    
    it("accepts dimes") {
      val coinHold = new CoinHold(0,0,0)
      coinHold.addCoin(dime)
      coinHold.totalAmount should be(10)
      coinHold.quarters should be(0)
      coinHold.nickels should be(0)
      coinHold.dimes should be(1)
    }
    
    it("accepts multiple coins") {
      val coinHold = new CoinHold(0,0,0)
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