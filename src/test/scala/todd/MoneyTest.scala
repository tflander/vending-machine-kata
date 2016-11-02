package todd
import org.scalatest._

class MoneyTest extends FunSpec with ShouldMatchers {
  
  describe("quarter validation tests") {
    
    it("identifies a quarter") {
      Coin(ROUND, size=24.26, weight=5.67).valid should be(true)
    }
    
    it("rejects a non-round quarter") {
      Coin(NON_ROUND, size=24.26, weight=5.67).valid should be(false)      
    }
    
    it("rejects a big quarter") {
      Coin(ROUND, size=24.27, weight=5.67).valid should be(false)      
    }
    
    it("rejects a small quarter") {
      Coin(ROUND, size=24.25, weight=5.67).valid should be(false)      
    }
    
    it("rejects a heavy quarter") {
      Coin(ROUND, size=24.26, weight=5.68).valid should be(false)      
    }
    
    it("rejects a light quarter") {
      Coin(ROUND, size=24.26, weight=5.66).valid should be(false)      
    }
    
  }

  describe("dime validation tests") {
    
    it("identifies a dime") {
      Coin(ROUND, size=17.91, weight=2.27).valid should be(true)
    }

    it("rejects a non-round dime") {
      Coin(NON_ROUND, size=17.91, weight=2.27).valid should be(false)      
    }
    
    it("rejects a big dime") {
      Coin(ROUND, size=17.92, weight=2.27).valid should be(false)
    }
    
    it("rejects a small dime") {
      Coin(ROUND, size=17.90, weight=2.27).valid should be(false)
    }
    
    it("rejects a heavy dime") {
      Coin(ROUND, size=17.91, weight=2.28).valid should be(false)
    }
    
    it("rejects a light dime") {
      Coin(ROUND, size=17.91, weight=2.26).valid should be(false)
    }
  }
  
  describe("nickel validation tests") {
    
    it("identifies a nickel") {
      Coin(ROUND, size=21.21, weight=5.00).valid should be(true)
    }

    it("rejects a non-round nickel") {
      Coin(NON_ROUND, size=21.21, weight=5.00).valid should be(false)
    }
    
    it("rejects a big nickel") {
      Coin(ROUND, size=21.22, weight=5.00).valid should be(false)
    }
    
    it("rejects a small nickel") {
      Coin(ROUND, size=21.20, weight=5.00).valid should be(false)
    }
    
//    it("rejects a heavy nickel") {
//      Coin(ROUND, size=17.91, weight=2.28).valid should be(false)
//    }
//    
//    it("rejects a light nickel") {
//      Coin(ROUND, size=17.91, weight=2.26).valid should be(false)
//    }
//    
  }  
}