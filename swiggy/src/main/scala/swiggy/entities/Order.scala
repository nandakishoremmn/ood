package swiggy.entities

import swiggy.enums.OrderStatus.OrderStatus
import swiggy.enums.OrderStatus

class Order(id: Int, items: Map[String, Double],
            restaurant: Restaurant,
            private var status: OrderStatus = OrderStatus.CREATED) {

  def total: Double = items.values.sum

  def dispatch(): Unit = {
    status = OrderStatus.DISPATCHED
  }

  def getRestaurant: Restaurant = restaurant

  def getStatus: OrderStatus = status

  override def toString = s"Order(status=$status, restaurant=$restaurant, total=$total)"
}
