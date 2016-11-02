package todd
import org.scalatest._

class vendingMachineBddTest extends FunSpec with ShouldMatchers {
  
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
      
    }
    
    it("accepts a quarter (given no  coins inserted), then displays 0.25") {
      
    }
    
    it("accepts a dime (given no  coins inserted), then displays 0.10") {
      
    }
    
    it("accepts a nickle (given no  coins inserted), then displays 0.05") {
      
    }
    
    it("rejects a penny (given no  coins inserted), then continues to display INSERT COIN") {
      
    }

    it("rejects an object of unknown weight (given no  coins inserted), then continues to display INSERT COIN") {
      
    }
    
    it("rejects an object of unknown size (given no  coins inserted), then continues to display INSERT COIN") {
      
    }
    
    it("accepts multiple valid coins and updates the display accordingly") {
      
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
      
    }
    
    it("dispenses chips given $0.50 entered and chips button is pressed, then displays THANK YOU until the display is checked") {
      
    }
    
    it("dispenses candy given $0.65 entered and chips button is pressed, then displays THANK YOU until the display is checked") {
      
    }
    
    it("displays 'PRICE $1.00' given not enough money inserted and cola button pressed") {
      
    }
    
    it("displays 'PRICE $0.50' given not enough money inserted and chips button pressed") {
      
    }
    
    it("displays 'PRICE $0.65' given not enough money inserted and candy button pressed") {
      
    }
    
    it("displays INSERT COIN given price check and no coins inserted") {
      
    }
    
    it("displays $0.15 given price check and one nickle and one dime inserted") {
      
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

    it("returns one quarter and one dime given $1.00 inserted and candy purchased, and the machine has adequate change") {
      
    }
  }

  describe("Return Coins") {
    /*
As a customer
I want to have my money returned
So that I can change my mind about buying stuff from the vending machine

When the return coins button is pressed, the money the customer has placed in the machine is returned and the display shows INSERT COIN.
     */
    
    it("returns coins, given one nickle and one dime inserted, and the 'return coins' button is pressed, then displays INSERT COIN") {
      
    }
    
    it("does not return coins when no coins inserted and the 'return coins' button is pressed") {
      
    }
  }
  
  describe("Sold Out") {
    /*
As a customer
I want to be told when the item I have selected is not available
So that I can select another item

When the item selected by the customer is out of stock, the machine displays SOLD OUT. If the display is checked again, it will display the amount of money remaining in the machine or INSERT COIN if there is no money in the machine.


     */
    
    it("displays SOLD OUT given coke is sold out, $1.00 inserted and coke button is pressed, then displays $1.00") {
      
    }

    it("displays SOLD OUT given coke is sold out, $0.50 inserted and coke button is pressed, then displays $0.50") {
      
    }
    
    it("displays SOLD OUT given coke is sold out, no money inserted and coke button is pressed, then displays INSERT COIN") {
      
    }
    
    it("displays SOLD OUT given chips are sold out, $1.00 inserted and chips button is pressed, then displays $1.00") {
      
    }
    
    it("displays SOLD OUT given candy is sold out, $1.00 inserted and candy button is pressed, then displays $1.00") {
      
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
    
    // TODO: think about the cases where exact change is available for some items
    // what is the minimal amount of change required to make change?
    // assume you can return any coins not needed for purchase.  Probably should test for this.
    
    //   coke is $1.00.  Could have 3 quarters and 3 dimes, requiring one nickel change
    //   chips are $0.50.  Could have one quarter and 3 dimes, requiring one nickel change
    //   candy is $0.65.  Could have three quarters, requiring one dime or two nickels change
    //                    Could have two quarters and two dimes, requiring one nickel change
    //   if something cost $0.05 and have a quarter, need two dimes, four nickels, or one dime two nickels.
    
    // Assumption:  we need either two dimes, four nickels, or one dime two nickels to make change.
  }
      

}