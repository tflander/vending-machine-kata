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
      vendingMachine.inventory.quantityFor(Cola) should be(10)      
      vendingMachine.inventory.quantityFor(Chips) should be(10)      
      vendingMachine.inventory.quantityFor(Candy) should be(10)      
    }
    
    it("can have custom quantities") {
      val vendingMachine = new VendingMachine(initialColas=2, initialChips=1, initialCandy=0)
      vendingMachine.inventory.quantityFor(Cola) should be(2)      
      vendingMachine.inventory.quantityFor(Chips) should be(1)      
      vendingMachine.inventory.quantityFor(Candy) should be(0)      
    }
    
    it("has one less when a purchase is completed") {
      val vendingMachine = new VendingMachine()
      vendingMachine.inventory.quantityFor(Cola) should be(10)      
      vendingMachine.inventory.quantityFor(Chips) should be(10)      
      vendingMachine.inventory.quantityFor(Candy) should be(10)      
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)
      vendingMachine.selectProduct(Cola)
      vendingMachine.inventory.quantityFor(Cola) should be(9)      
      vendingMachine.inventory.quantityFor(Chips) should be(10)      
      vendingMachine.inventory.quantityFor(Candy) should be(10)      
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Chips)
      vendingMachine.inventory.quantityFor(Cola) should be(9)      
      vendingMachine.inventory.quantityFor(Chips) should be(9)      
      vendingMachine.inventory.quantityFor(Candy) should be(10)      
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Candy)
      vendingMachine.inventory.quantityFor(Cola) should be(9)      
      vendingMachine.inventory.quantityFor(Chips) should be(9)      
      vendingMachine.inventory.quantityFor(Candy) should be(9)      
    }
    
    it("doesn't allow purchase when out") {
      val vendingMachine = new VendingMachine(initialColas=2, initialChips=1, initialCandy=0, initialCash=CoinCounts(0,0,0))
      vendingMachine.moneyBox.coinVault.dimes=1

      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Candy)
      vendingMachine.inventory.isSoldOut(Candy) should be(true)      
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.moneyBox.coinVault.totalAmount should be(10)
      vendingMachine.display should be("SOLD OUT")
      vendingMachine.display should be("0.75")
    }
    
    it("shows if a product is sold out when you press the button without inserting coins") {
      val vendingMachine = new VendingMachine(initialColas=2, initialChips=0, initialCandy=1)
      vendingMachine.selectProduct(Chips)
      vendingMachine.inventory.isSoldOut(Chips) should be(true)      
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.display should be("SOLD OUT")
      vendingMachine.display should be("INSERT COIN")      
    }
  }  
  
  describe("Exact change tests") {
    it("requires exact change when the money box is empty") {
      val vendingMachine = new VendingMachine(initialCash=CoinCounts(0,0,0))
      vendingMachine.moneyBox.coinVault.totalAmount should be(0)
      vendingMachine.display should be("EXACT CHANGE ONLY")
    }
    
    it("requires exact change when the money box has three nickels") {
      val initialCash = CoinCounts(quarters=0,dimes=0,nickels=3)
      val vendingMachine = new VendingMachine(initialCash=initialCash)
      vendingMachine.display should be("EXACT CHANGE ONLY")      
    }
    
    it("requires exact change when the money box has one dime and one nickel") {
      val initialCash = CoinCounts(quarters=0,dimes=1,nickels=1)
      val vendingMachine = new VendingMachine(initialCash=initialCash)
      vendingMachine.display should be("EXACT CHANGE ONLY")            
    }
    
    it("requires exact change when the money box has two dimes and no nickels") {
      val initialCash = CoinCounts(quarters=0,dimes=2,nickels=0)
      val vendingMachine = new VendingMachine(initialCash=initialCash)
      vendingMachine.display should be("EXACT CHANGE ONLY")            
    }
    
    it("gives customers their product and keeps the change when exact change is required") {
      val vendingMachine = new VendingMachine(initialCash=CoinCounts(0,0,0))
      vendingMachine.display should be("EXACT CHANGE ONLY")            
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Candy)
      vendingMachine.dispensedProducts should be(Seq(Candy))
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.display should be("THANK YOU")            
      vendingMachine.display should be("EXACT CHANGE ONLY")     
      vendingMachine.moneyBox.coinVault.totalAmount should be(75)
    }

  }
}