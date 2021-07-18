package newsfeed.managers

import newsfeed.entities.User
import newsfeed.exceptions.UserExistsException

import scala.util.{Failure, Success, Try}

class UserManager {
  private var users: Map[String, User] = Map()

  def signup(username: String): Try[Unit] = {
    users.get(username) match {
      case Some(_) => Failure(new UserExistsException)
      case None => users += (username -> new User(username))
        Success()
    }
  }

  def get(username: String): Option[User] = {
    users.get(username)
  }
}
