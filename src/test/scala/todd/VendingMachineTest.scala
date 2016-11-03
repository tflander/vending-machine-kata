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
    
    it("accepts multiple coins") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(dime)    
      vendingMachine.insertCoin(dime)    
      vendingMachine.insertCoin(nickel)    
      vendingMachine.insertCoin(nickel)    
      vendingMachine.insertCoin(nickel)    
      vendingMachine.insertCoin(penny)    
      vendingMachine.display should be("0.60")
      vendingMachine.coinReturn should be (Seq(penny))      
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
  
  describe("product dispensing tests") {
    it("dispenses cola") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("1.00")
      vendingMachine.selectProduct(Cola)
      vendingMachine.dispensedProducts should be(Seq(Cola))
      vendingMachine.display should be("THANK YOU")
      vendingMachine.display should be("INSERT COIN")
    }
    
    it("dispenses chips") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.50")
      vendingMachine.selectProduct(Chips)
      vendingMachine.dispensedProducts should be(Seq(Chips))
      vendingMachine.display should be("THANK YOU")
      vendingMachine.display should be("INSERT COIN")
    }
    
    it("dispenses candy") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(dime)    
      vendingMachine.insertCoin(nickel)    
      vendingMachine.display should be("0.65")
      vendingMachine.selectProduct(Candy)
      vendingMachine.dispensedProducts should be(Seq(Candy))
      vendingMachine.display should be("THANK YOU")
      vendingMachine.display should be("INSERT COIN")
    }
    
  }
}