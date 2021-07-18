package swiggy

object Driver {
  def main(args: Array[String]): Unit = {
    val app = new App();
    app.onboardRestaurant("r1", 2)
    app.addItems("r1", "i1", 10)
    app.addItems("r1", "i2", 10)
    app.onboardRestaurant("r2", 2)
    app.addItems("r2", "i1", 15)
    app.addItems("r2", "i1", 15)

    app.placeOrder(1, List("i1"))
    app.updatePrice("r2", "i1", 5)
    app.placeOrder(1, List("i1"))
    app.placeOrder(2, List("i1"))
    app.placeOrder(3, List("i1"))
    app.placeOrder(4, List("i1"))
    app.placeOrder(5, List("i1"))
    app.dispatchOrder(3)
    app.placeOrder(5, List("i1"))
  }
}
