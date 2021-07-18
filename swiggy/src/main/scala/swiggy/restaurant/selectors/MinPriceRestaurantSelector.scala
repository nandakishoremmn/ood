package swiggy.restaurant.selectors

import swiggy.entities.Restaurant
import swiggy.manager.RestaurantManager

class MinPriceRestaurantSelector extends RestaurantSelector {
  override def select(restaurantManager: RestaurantManager, items: List[String]): Option[Restaurant] = {
    restaurantManager.getRestaurants
      .filter(_.canTakeOrders)
      .filter(_.containsItems(items))
      .minByOption(restaurant => items.map(restaurant.getItem(_).get.price).sum)
  }
}
