package newsfeed.entities

import java.util.Date
import java.util.concurrent.atomic.AtomicInteger

import scala.collection.mutable.ListBuffer
import scala.util.Try

class Post(private val id: Int,
           private val user: User,
           private val msg: String,
           val postedOn: Date,
           val isComment: Boolean = false,
           private var comments: ListBuffer[Post] = ListBuffer(),
           private var upvoters: Set[User] = Set(),
           private var downvoters: Set[User] = Set()) {

  def isFollowedBy(user1: User): Boolean = {
    user1.isFollow(user)
  }


  def upvotes: Int = upvoters.size

  def downvotes: Int = downvoters.size

  def score: Int = upvotes - downvotes

  def commentsCount: Int = comments.length

  def upvote(user: User): Unit = {
    downvoters -= user
    upvoters += user
  }

  def downvote(user: User): Unit = {
    downvoters += user
    upvoters -= user
  }

  def print(level: Int = 0): Unit = {
    val tabs = (0 to level).map(i => "\t").mkString("")
    if(!isComment) {
      println("")
    }
    println(tabs + s"id: $id")
    if(!isComment){
      println(tabs + s"(upvotes: ${upvoters.size}, downvotes: ${downvoters.size})")
    }
    println(tabs + s"${user.getUsername}")
    println(tabs + msg)
    println(tabs + postedOn)
    comments.foreach(_.print(level + 1))
  }

  def comment(comment: Post): Unit = {
    comments += comment
  }
}

object Post {
  val id = new AtomicInteger

  def getNextId: Int = id.incrementAndGet()

}
