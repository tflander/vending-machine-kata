package todd
import org.scalatest._

class CoinHoldTest extends FunSpec with ShouldMatchers {

  import Money._
  
  describe("add coins") {
    
    it("accepts quarters") {
      val coinHold = new CoinHold()
      coinHold.insert(quarter)
      coinHold.insertedAmount should be(25)
      coinHold.quarters should be(1)
    }
    
  }
}