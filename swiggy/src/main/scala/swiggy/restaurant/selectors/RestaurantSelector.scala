package swiggy.restaurant.selectors

import swiggy.entities.Restaurant
import swiggy.manager.RestaurantManager

trait RestaurantSelector {
  def select(restaurantManager: RestaurantManager, items: List[String]): Option[Restaurant]
}
