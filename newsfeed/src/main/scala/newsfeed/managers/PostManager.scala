package newsfeed.managers

import java.util.Date

import newsfeed.entities.{Post, User}
import newsfeed.exceptions.PostOrCommentNotFound

import scala.util.{Failure, Success, Try}

class PostManager {
  private var posts: Map[Int, Post] = Map()

  def getPosts: List[Post] = posts.values.toList

  def addPost(user: User, msg: String): Unit = {
    val postId = Post.getNextId
    posts += (postId -> new Post(postId, user, msg, new Date()))
  }

  def addComment(postId: Int, user: User, msg: String): Try[Unit] = {
    posts.get(postId) match {
      case Some(post) =>
        val commentId = Post.getNextId
        val comment = new Post(commentId, user, msg, new Date(), true)
        post.comment(comment)
        posts += (commentId -> comment)
        Success()
      case None => Failure(new PostOrCommentNotFound)
    }
  }

  def getPost(postId: Int): Option[Post] ={
    posts.get(postId)
  }

}
