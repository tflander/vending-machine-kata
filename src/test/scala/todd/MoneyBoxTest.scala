package todd
import org.scalatest._
import Money._

class MoneyBoxTest extends FunSpec with ShouldMatchers {
  
  describe("insert coin tests") {
    it("accepts valid coins that are inserted") {
      val moneyBox = new MoneyBox()
      moneyBox.insert(quarter)
      moneyBox.insert(dime)
      moneyBox.insert(nickel)
      moneyBox.insertedAmount should be(40)
      moneyBox.rejectedCoins.isEmpty should be (true)      
    }
    
    it("rejects invalid coins") {
      val moneyBox = new MoneyBox()
      moneyBox.insert(penny)
      moneyBox.insertedAmount should be(0)
      moneyBox.rejectedCoins should be (Seq(penny))      
    }
  }
  
  describe("coin release tests") {
    
    it("releases coins to purchase a product") {
      val moneyBox = new MoneyBox()
      moneyBox.insert(quarter)
      moneyBox.insert(dime)
      moneyBox.insert(nickel)
      moneyBox.insertedAmount should be(40)
      moneyBox.acceptCoinsAndReturnChangeForProductCosting(40)
      moneyBox.coinVault should be(CoinCounts(quarters=1, dimes=1, nickels=1))    
      moneyBox.changeAmount should be(0)
      moneyBox.insertedAmount should be(0)
    }
    
    it("releases coins to purchase a product using available coins") {
      val moneyBox = new MoneyBox()
      moneyBox.insert(dime)
      moneyBox.insert(dime)
      moneyBox.insert(dime)
      moneyBox.insert(nickel)
      moneyBox.insert(nickel)
      moneyBox.insertedAmount should be(40)
      moneyBox.acceptCoinsAndReturnChangeForProductCosting(40)
      moneyBox.coinVault should be(CoinCounts(quarters=0, dimes=3, nickels=2))    
      moneyBox.changeAmount should be(0)
      moneyBox.insertedAmount should be(0)
    }
    
    it("releases coins to purchase a product only when funds are available") {
      val moneyBox = new MoneyBox()
      intercept[IllegalArgumentException] {
        moneyBox.acceptCoinsAndReturnChangeForProductCosting(40)
      }
    }
  }
  
  describe("sundry tests") {
    
    it("calculates change required to return to the customer") {
      val moneyBox = new MoneyBox()
      moneyBox.insert(quarter)
      moneyBox.insert(dime)
      moneyBox.insert(dime)
      moneyBox.insertedAmount should be(45)
      moneyBox.acceptCoinsAndReturnChangeForProductCosting(40)
      moneyBox.coinVault should be(CoinCounts(quarters=1, dimes=2, nickels=0))
      moneyBox.changeAmount should be(5)
      moneyBox.insertedAmount should be(0)      
    }
    
    it("returns coins") {
      val moneyBox = new MoneyBox()
      moneyBox.insert(quarter)
      moneyBox.insert(dime)
      moneyBox.insert(dime)
      moneyBox.insertedAmount should be(45)
      moneyBox.returnCoins should be(Seq(quarter, dime, dime))
      moneyBox.changeAmount should be(0)
      moneyBox.insertedAmount should be(0)            
    }
  }
  
  describe("coin vault tests") {
    it("get requested amount from the coin vault for making change") {
      val moneyBox = new MoneyBox()
      moneyBox.addCoinsToCoinVault(CoinCounts(quarters=0, dimes=2, nickels=2))
      moneyBox.vaultAmount should be(30)
      moneyBox.releaseChange(10) should be (Seq(dime))
      moneyBox.vaultAmount should be(20)
      moneyBox.coinVault should be(CoinCounts(quarters=0, dimes=1, nickels=2))
    }
  }
}