package swiggy.manager

import swiggy._
import swiggy.entities.Order
import swiggy.enums.OrderStatus
import swiggy.exceptions.{NoFulfillingRestaurantException, OrderDispatchError, OrderIdExistsException, OrderNotFoundException}
import swiggy.restaurant.selectors.{MinPriceRestaurantSelector, RestaurantSelector}

import scala.util.{Failure, Success, Try}

class OrderManager(restaurantManager: RestaurantManager,
                   restaurantSelector: RestaurantSelector = new MinPriceRestaurantSelector,
                   var orders: Map[Int, Order] = Map()) {
  def dispatch(orderId: Int): Try[Unit] = {
    orders.get(orderId) match {
      case Some(order) => order.getStatus match {
        case OrderStatus.CREATED => order.dispatch()
          order.getRestaurant.decrementOrderCount()
          Success()
        case status => Failure(new OrderDispatchError(s"cannot dispatch order in $status state"))
      }
      case None => Failure(new OrderNotFoundException)
    }
  }

  def addOrder(id: Int, items: List[String]): Try[Order] = {
    orders.get(id) match {
      case Some(_) => Failure(new OrderIdExistsException)
      case None =>
        restaurantSelector.select(restaurantManager, items) match {
          case Some(restaurant) =>
            val itemPriceMap = items.map(itemName => itemName -> restaurant.getItem(itemName).get.price).toMap
            val order = new Order(id, itemPriceMap, restaurant)
            orders += (id -> order)
            restaurant.incrementOrderCount()
            Success(order)
          case None => Failure(new NoFulfillingRestaurantException)
        }
    }
  }

}
