package todd
import org.scalatest._

class VendingMachineTest extends FunSpec with ShouldMatchers {

  import Money._
  
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
  
  describe("display price tests") {
    
    it("displays price when not enough money entered") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.25")
      vendingMachine.selectProduct(Chips)
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.display should be("PRICE $0.50")
      vendingMachine.display should be("0.25")
    }
  }
  
  describe("change tests") {
    it("gives change when necessary") {
      val vendingMachine = new VendingMachine()
      vendingMachine.moneyBox.coinVault.dimes=1
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.75")
      vendingMachine.selectProduct(Candy)
      vendingMachine.dispensedProducts should be(Seq(Candy))
      vendingMachine.coinReturn should be (Seq(dime))
    }
  }
  
  describe ("coin return tests") {
    it("return coins") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.25")
      vendingMachine.pressCoinReturn()
      vendingMachine.coinReturn should be (Seq(quarter))
      vendingMachine.display should be("INSERT COIN")      
    }
  }
  
  describe ("product quantity tests") {
    it("has 10 of each product by default") {
      val vendingMachine = new VendingMachine()
      vendingMachine.numColas should be(10)      
      vendingMachine.numChips should be(10)      
      vendingMachine.numCandy should be(10)
    }
    
    it("can have custom quantities") {
      val vendingMachine = new VendingMachine(numColas=2, numChips=1, numCandy=0)
      vendingMachine.numColas should be(2)      
      vendingMachine.numChips should be(1)      
      vendingMachine.numCandy should be(0)
    }
    
    it("has one less when a purchase is completed") {
      val vendingMachine = new VendingMachine()
      vendingMachine.numColas should be(10)      
      vendingMachine.numChips should be(10)      
      vendingMachine.numCandy should be(10)
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)
      vendingMachine.selectProduct(Cola)
      vendingMachine.numColas should be(9)            
      vendingMachine.numChips should be(10)      
      vendingMachine.numCandy should be(10)
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Chips)
      vendingMachine.numColas should be(9)            
      vendingMachine.numChips should be(9)      
      vendingMachine.numCandy should be(10)
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Candy)
      vendingMachine.numColas should be(9)            
      vendingMachine.numChips should be(9)      
      vendingMachine.numCandy should be(9)
    }
    
    it("doesn't allow purchase when out") {
      val vendingMachine = new VendingMachine(numColas=2, numChips=1, numCandy=0)
      vendingMachine.moneyBox.coinVault.dimes=1

      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Candy)
      vendingMachine.numCandy should be(0)
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.moneyBox.coinVault.totalAmount should be(10)
      // TODO: test display
    }
  }
}