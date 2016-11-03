package todd
import org.scalatest._

class VendingMachineTest extends FunSpec with ShouldMatchers {
  
  val quarter = Coin(ROUND, size=24.26, weight=5.67)
  val dime = Coin(ROUND, size=17.91, weight=2.27)
  val nickel = Coin(ROUND, size=21.21, weight=5.00)
  val penny = Coin(ROUND, size=19.05, weight=2.50)
  
  describe("display tests") {
    val vendingMachine = new VendingMachine()
    
    it("initially displays 'INSERT COIN'") {
      vendingMachine.display should be("INSERT COIN")
    }
    
  }
  
  describe("coin insertion tests") {
            
    it("accepts a quarter") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.25")
    }

    it("accepts a dime") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(dime)    
      vendingMachine.display should be("0.10")
    }
    
    it("accepts a nickel") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(nickel)    
      vendingMachine.display should be("0.05")
    }
    
    it("rejects a penny") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(penny)    
      vendingMachine.display should be("INSERT COIN")
    }

    it("rejects an invalid coin") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(Coin(NON_ROUND, size=19, weight=3))    
      vendingMachine.display should be("INSERT COIN")
    }
  }
  
  describe("coin return tests") {
    it("returns pennies") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(penny)    
      vendingMachine.insertCoin(penny)    
      vendingMachine.coinReturn should be (Seq(penny, penny))
    }
  }
}