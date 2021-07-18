package swiggy.manager

import swiggy.entities.Restaurant
import swiggy.exceptions.RestaurantExistsException

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class RestaurantManager() {
  val restaurants = new mutable.HashMap[String, Restaurant]();

  def getRestaurants: List[Restaurant] = {
    restaurants.values.toList
  }

  def getRestaurant(name: String): Option[Restaurant] = {
    restaurants.get(name)
  }

  def addRestaurant(name: String, maxCapacity: Int): Try[Unit] = {
    if(restaurants.contains(name)) {
      Failure(new RestaurantExistsException)
    }
    restaurants.put(name, new Restaurant(name, maxCapacity))
    Success()
  }
}
