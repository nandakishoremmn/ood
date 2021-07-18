package swiggy.exceptions

class RestaurantExistsException extends Exception("Restaurant already exists")
class ItemAlreadyExists extends Exception("Item already exists")
class ItemDoesntExists extends Exception("Item doesn't exists")
class OrderIdExistsException extends Exception("Order id already exists")
class NoFulfillingRestaurantException extends Exception("No restaurant can fulfill this order")
class OrderNotFoundException extends Exception("Order not found")
class OrderDispatchError(s: String) extends Exception(s)

