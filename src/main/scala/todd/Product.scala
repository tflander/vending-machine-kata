package todd

sealed trait Product {
  def name: String
  def cost: Int
}

case object Cola extends Product {
  val name = "Cola"
  val cost = 100
}
//case class Product(name: String, cost: Int)