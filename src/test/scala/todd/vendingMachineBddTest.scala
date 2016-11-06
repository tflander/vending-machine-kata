package todd
import org.scalatest._

class vendingMachineBddTest extends FunSpec with ShouldMatchers {

  import Money._
  
  describe("Accept Coins") {
    
    /*
As a vendor
I want a vending machine that accepts coins
So that I can collect money from the customer

The vending machine will accept valid coins (nickels, dimes, and quarters) and reject invalid ones (pennies). 
When a valid coin is inserted the amount of the coin will be added to the current amount and the display will be updated. 
When there are no coins inserted, the machine displays INSERT COIN. Rejected coins are placed in the coin return.

NOTE: The temptation here will be to create Coin objects that know their value. However, this is not how a real 
vending machine works. Instead, it identifies coins by their weight and size and then assigns a value to what was 
inserted. You will need to do something similar. This can be simulated using strings, constants, enums, symbols, or 
something of that nature.
     */
    
    it("displays INSERT COIN when no coins are inserted") {
      val vendingMachine = new VendingMachine()
      vendingMachine.display should be("INSERT COIN")
    }
    
    it("accepts a quarter (given no  coins inserted), then displays 0.25") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.25")      
    }
    
    it("accepts a dime (given no  coins inserted), then displays 0.10") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(dime)    
      vendingMachine.display should be("0.10")      
    }
    
    it("accepts a nickel (given no  coins inserted), then displays 0.05") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(nickel)    
      vendingMachine.display should be("0.05")      
    }
    
    it("rejects a penny (given no  coins inserted), then continues to display INSERT COIN") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(penny)    
      vendingMachine.display should be("INSERT COIN")  
      vendingMachine.coinReturn should be(Seq(penny))
    }

    it("rejects an invalid object (given no  coins inserted), then continues to display INSERT COIN") {
      val vendingMachine = new VendingMachine()
      val invalidCoin = Coin(NON_ROUND, size=19, weight=3)
      vendingMachine.insertCoin(invalidCoin)    
      vendingMachine.display should be("INSERT COIN")      
      vendingMachine.coinReturn should be(Seq(invalidCoin))
    }
    
    it("accepts multiple valid coins and updates the display accordingly") {
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
  
  describe("Select Product") {
    /*
As a vendor
I want customers to select products
So that I can give them an incentive to put money in the machine

There are three products: cola for $1.00, chips for $0.50, and candy for $0.65. When the respective button is pressed 
and enough money has been inserted, the product is dispensed and the machine displays THANK YOU. If the display is 
checked again, it will display INSERT COIN and the current amount will be set to $0.00. If there is not enough money 
inserted then the machine displays PRICE and the price of the item and subsequent checks of the display will display 
either INSERT COIN or the current amount as appropriate.

     */
    
    it("dispenses cola given $1.00 entered and cola button is pressed, then displays THANK YOU until the display is checked") {
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
    
    it("dispenses chips given $0.50 entered and chips button is pressed, then displays THANK YOU until the display is checked") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.50")
      vendingMachine.selectProduct(Chips)
      vendingMachine.dispensedProducts should be(Seq(Chips))
      vendingMachine.display should be("THANK YOU")
      vendingMachine.display should be("INSERT COIN")
    }
    
    it("dispenses candy given $0.65 entered and candy button is pressed, then displays THANK YOU until the display is checked") {
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
    
    it("displays 'PRICE $1.00' given not enough money inserted and cola button pressed") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.25")
      vendingMachine.selectProduct(Cola)
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.display should be("PRICE $1.00")
      vendingMachine.display should be("0.25")      
    }
    
    it("displays 'PRICE $0.50' given not enough money inserted and chips button pressed") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.25")
      vendingMachine.selectProduct(Chips)
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.display should be("PRICE $0.50")
      vendingMachine.display should be("0.25")            
    }
    
    it("displays 'PRICE $0.65' given not enough money inserted and candy button pressed") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(quarter)    
      vendingMachine.display should be("0.25")
      vendingMachine.selectProduct(Candy)
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.display should be("PRICE $0.65")
      vendingMachine.display should be("0.25")      
    }
    
    it("displays INSERT COIN given price check and no coins inserted") {
      val vendingMachine = new VendingMachine()
      vendingMachine.display should be("INSERT COIN")
      vendingMachine.selectProduct(Chips)
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.display should be("PRICE $0.50")
      vendingMachine.display should be("INSERT COIN")            
    }
  }

  describe("Make Change") {
    /*
As a vendor
I want customers to receive correct change
So that they will use the vending machine again

When a product is selected that costs less than the amount of money in the machine, then the remaining amount is placed 
in the coin return.
     */

    it("returns one dime given $0.75 inserted and candy purchased, and the machine has adequate change") {
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

  describe("Return Coins") {
    /*
As a customer
I want to have my money returned
So that I can change my mind about buying stuff from the vending machine

When the return coins button is pressed, the money the customer has placed in the machine is returned and the display shows INSERT COIN.
     */
    
    it("returns coins, given one nickel and one dime inserted, and the 'return coins' button is pressed, then displays INSERT COIN") {
      val vendingMachine = new VendingMachine()
      vendingMachine.insertCoin(nickel)    
      vendingMachine.insertCoin(dime)    
      vendingMachine.display should be("0.15")
      vendingMachine.pressCoinReturn()
      vendingMachine.coinReturn.toSet should be (Set(nickel, dime))
      vendingMachine.display should be("INSERT COIN")      
    }
    
    it("does not return coins when no coins inserted and the 'return coins' button is pressed") {
      val vendingMachine = new VendingMachine()
      vendingMachine.display should be("INSERT COIN")
      vendingMachine.pressCoinReturn()
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.display should be("INSERT COIN")            
    }
  }
  
  describe("Sold Out") {
    /*
As a customer
I want to be told when the item I have selected is not available
So that I can select another item

When the item selected by the customer is out of stock, the machine displays SOLD OUT. If the display is checked again, it will display the amount of money remaining in the machine or INSERT COIN if there is no money in the machine.


     */
    
    it("displays SOLD OUT given cola is sold out, $1.00 inserted and cola button is pressed, then displays $1.00") {
      val vendingMachine = new VendingMachine(initialColas=0, initialChips=1, initialCandy=2)
      val initialVaultCash = vendingMachine.moneyBox.coinVault.totalAmount
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Cola)
      vendingMachine.inventory.isSoldOut(Cola) should be(true)      
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.moneyBox.coinVault.totalAmount should be(initialVaultCash)
      vendingMachine.display should be("SOLD OUT")
      vendingMachine.display should be("1.00")      
    }

    it("displays SOLD OUT given cola is sold out, $0.50 inserted and cola button is pressed, then displays $0.50") {
      val vendingMachine = new VendingMachine(initialColas=0, initialChips=1, initialCandy=2)
      val initialVaultCash = vendingMachine.moneyBox.coinVault.totalAmount
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Cola)
      vendingMachine.inventory.isSoldOut(Cola) should be(true)      
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.moneyBox.coinVault.totalAmount should be(initialVaultCash)
      vendingMachine.display should be("SOLD OUT")
      vendingMachine.display should be("0.50")            
    }
    
    it("displays SOLD OUT given cola is sold out, no money inserted and cola button is pressed, then displays INSERT COIN") {
      val vendingMachine = new VendingMachine(initialColas=0, initialChips=1, initialCandy=2)
      val initialVaultCash = vendingMachine.moneyBox.coinVault.totalAmount
      vendingMachine.selectProduct(Cola)
      vendingMachine.inventory.isSoldOut(Cola) should be(true)      
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.moneyBox.coinVault.totalAmount should be(initialVaultCash)
      vendingMachine.display should be("SOLD OUT")
      vendingMachine.display should be("INSERT COIN")                  
    }
    
    it("displays SOLD OUT given chips are sold out, $1.00 inserted and chips button is pressed, then displays $1.00") {
      val vendingMachine = new VendingMachine(initialColas=3, initialChips=0, initialCandy=2)
      val initialVaultCash = vendingMachine.moneyBox.coinVault.totalAmount
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Chips)
      vendingMachine.inventory.isSoldOut(Chips) should be(true)      
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.moneyBox.coinVault.totalAmount should be(initialVaultCash)
      vendingMachine.display should be("SOLD OUT")
      vendingMachine.display should be("1.00")            
    }
    
    it("displays SOLD OUT given candy is sold out, $1.00 inserted and candy button is pressed, then displays $1.00") {
      val vendingMachine = new VendingMachine(initialColas=3, initialChips=9, initialCandy=0, initialCash = new CoinHold(0,0,0))
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.insertCoin(quarter)    
      vendingMachine.selectProduct(Candy)
      vendingMachine.inventory.isSoldOut(Candy) should be(true)      
      vendingMachine.dispensedProducts.isEmpty should be(true)
      vendingMachine.coinReturn.isEmpty should be (true)
      vendingMachine.moneyBox.coinVault.totalAmount should be(0)
      vendingMachine.display should be("SOLD OUT")
      vendingMachine.display should be("1.00")                  
    }
    
  }
  
  describe("Exact Change Only") {
    /*
As a customer
I want to be told when exact change is required
So that I can determine if I can buy something with the money I have before inserting it

When the machine is not able to make change with the money in the machine for any of the items that it sells, it will 
display EXACT CHANGE ONLY instead of INSERT COIN.
     */
    // Assumption:  we need either four nickels, or one dime two nickels to make change.
    // The advantage of this approach is that it decouples the money logic from the inventory logic.
    // The disadvantage is that there are edge cases were we could get away with not requiring exact change.
    
    // Note also that we need at least one nickel to ensure that we can make change
    
    it("requires exact change when the money box is empty") {
      val vendingMachine = new VendingMachine(initialCash = new CoinHold(0,0,0))
      vendingMachine.moneyBox.coinVault.totalAmount should be(0)
      vendingMachine.display should be("EXACT CHANGE ONLY")
    }
    
    it("requires exact change when the money box has three nickels") {
      val initialCash = new CoinHold(quarters=0,dimes=0,nickels=3)
      val vendingMachine = new VendingMachine(initialCash = initialCash)
      vendingMachine.display should be("EXACT CHANGE ONLY")            
    }
    
    it("requires exact change when the money box has one dime and one nickel") {
      val initialCash = new CoinHold(quarters=0,dimes=1,nickels=1)
      val vendingMachine = new VendingMachine(initialCash = initialCash)
      vendingMachine.display should be("EXACT CHANGE ONLY")                  
    }
    
    it("doesn't require exact change when the money box has one dime and two nickels") {
      val initialCash = new CoinHold(quarters=0,dimes=1,nickels=2)
      val vendingMachine = new VendingMachine(initialCash = initialCash)
      vendingMachine.display should be("INSERT COIN")                        
    }
    
    it("doesn't require exact change when the money box has four nickels") {
      val initialCash = new CoinHold(quarters=0,dimes=0,nickels=4)
      val vendingMachine = new VendingMachine(initialCash = initialCash)
      vendingMachine.display should be("INSERT COIN")                              
    }
    
    it("gives customers their product and keeps the change when exact change is required") {
      val vendingMachine = new VendingMachine(initialCash = new CoinHold(0,0,0))
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