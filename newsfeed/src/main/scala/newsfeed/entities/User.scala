package newsfeed.entities

import scala.util.Try

class User(private val username: String,
           private var following: Set[User] = Set()) {
  def addFollower(user: User): Unit = {
    following += user
  }

  def getUsername: String = username

  def isFollow(user2: User): Boolean = {
    following.contains(user2)
  }
}
