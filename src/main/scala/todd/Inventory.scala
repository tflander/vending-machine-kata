package todd

class Inventory(initialColas: Int = 10, initialChips: Int = 10, initialCandy: Int = 10) {
  
  private val inventory = new scala.collection.mutable.HashMap[Product, Int]()
  inventory += Cola -> initialColas
  inventory += Chips -> initialChips
  inventory += Candy -> initialCandy
  
  def quantityFor(product: Product): Int = {
    return inventory.getOrElse(product, throw new IllegalArgumentException("invalid product " + product))
  }
  
  def removeOne(product: Product) = {
    val currentQuantity = quantityFor(product)
    if(currentQuantity == 0) throw new IllegalStateException("quantity for " + product + " is already zero")
    inventory += product -> (currentQuantity - 1)
  }
  
  def isAvailable(product: Product) = quantityFor(product) > 0
}