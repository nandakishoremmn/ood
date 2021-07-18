package swiggy.entities

import swiggy.exceptions.{ItemAlreadyExists, ItemDoesntExists}

import scala.util.{Failure, Success, Try}

class Restaurant(name: String,
                 maxCapacity: Int,
                 var currentOrderCount: Int = 0,
                 var menu: Map[String, Item] = Map()
                ) {
  def containsItems(items: List[String]): Boolean = {
    items.forall(itemName => menu.contains(itemName))
  }

  def getItem(name: String): Option[Item] = {
    menu.get(name)
  }

  def removeItem(itemName: String): Try[Unit] = {
    menu.get(name) match {
      case Some(_) =>
        menu -= itemName
        Success()
      case None => Failure(new ItemDoesntExists)
    }
  }

  def addItem(name: String, price: Double): Try[Unit] = {
    menu.get(name) match {
      case Some(_) => Failure(new ItemAlreadyExists)
      case None =>
        menu += (name -> new Item(name, price))
        Success()
    }
  }

  def updatePrice(name: String, price: Double): Try[Unit] = {
    menu.get(name) match {
      case Some(item) => menu += (name -> new Item(name, price))
        Success()
      case None => Failure(new ItemDoesntExists)
    }

  }

  def incrementOrderCount(): Unit = {
    currentOrderCount += 1
  }

  def decrementOrderCount(): Unit = {
    currentOrderCount -= 1
  }

  def canTakeOrders: Boolean = {
    currentOrderCount < maxCapacity
  }


  override def toString = s"Restaurant(name=$name, currentOrderCount = $currentOrderCount, maxCapacity=$maxCapacity)"
}
