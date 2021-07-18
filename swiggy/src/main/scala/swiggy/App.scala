package swiggy

import swiggy.manager.{OrderManager, RestaurantManager}

import scala.util.{Failure, Success}

class App {
  val restaurantManager = new RestaurantManager();
  val orderManger = new OrderManager(restaurantManager);

  def onboardRestaurant(restaurantName: String, maxCapacity: Int): Unit = {
    restaurantManager.addRestaurant(restaurantName, maxCapacity) match {
      case Failure(e) => println(e.getMessage)
      case Success(s) => println(s"Restaurant [$restaurantName] added");
    }
  }

  def placeOrder(orderId: Int, items: List[String]): Unit = {
    orderManger.addOrder(orderId, items) match {
      case Success(order) => println(s"order placed $order")
      case Failure(e) => println(e.getMessage)
    }
  }

  def updatePrice(restaurantName: String, itemName: String, newPrice: Double): Unit = {
    restaurantManager.getRestaurant(restaurantName) match {
      case Some(restaurant) => {
        restaurant.updatePrice(itemName, newPrice) match {
          case Success(_) => println(s"Updated $itemName price to $newPrice")
          case Failure(e) => println(e.getMessage)
        }
      }
      case None => println(s"Restaurant $restaurantName not found")
    }
  }

  def dispatchOrder(orderId: Int): Unit = {
    orderManger.dispatch(orderId) match {
      case Success(_) => println("Dispatched order")
      case Failure(e) => println(e.getMessage)
    }
  }

  def addItems(restaurantName: String, itemName: String, price: Double): Unit = {
    restaurantManager.getRestaurant(restaurantName) match {
      case Some(restaurant) =>
        restaurant.addItem(itemName, price) match {
          case Success(s) => println(s"Added $itemName to $restaurantName")
          case Failure(e) => println(e.getMessage)
        }
      case None => println(s"Restaurant $restaurantName not found")
    }
  }

  def removeItems(restaurantName: String, itemName: String): Unit = {
    restaurantManager.getRestaurant(restaurantName) match {
      case Some(restaurant) =>
        restaurant.removeItem(itemName) match {
          case Success(s) => println(s"Removed $itemName from $restaurantName")
          case Failure(e) => println(e.getMessage)
        }
      case None => println(s"Restaurant $restaurantName not found")
    }
  }
}
