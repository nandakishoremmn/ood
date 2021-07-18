package newsfeed

import newsfeed.entities.User
import newsfeed.exceptions.{NoLoggedInuserException, PostOrCommentNotFound, UserNotFoundException}
import newsfeed.feed.{FeedCreator, FeedCreatorImpl}
import newsfeed.managers.{PostManager, UserManager}

import scala.util.{Failure, Success, Try}

class App {
  private val userManager = new UserManager
  private val postManager = new PostManager
  private val feedCreator: FeedCreator = new FeedCreatorImpl
  private var activeUser: Option[String] = None

  def signup(username: String): Try[Unit] = {
    userManager.signup(username) match {
      case Success(_) => Success()
      case Failure(e) => Failure(e)
    }
  }

  def login(username: String): Try[Unit] = {
    userManager.get(username) match {
      case Some(user) => activeUser = Some(user.getUsername)
        shownewsfeed()
        Success()
      case None => Failure(new UserNotFoundException(username))
    }
  }

  def getActiveUser: Try[User] = {
    activeUser match {
      case Some(username) => userManager.get(username) match {
        case Some(user) => Success(user)
        case None => Failure(new UserNotFoundException(username))
      }
      case None => Failure(new NoLoggedInuserException)
    }
  }

  def post(msg: String): Try[Unit] = {
    getActiveUser match {
      case Success(user) =>
        postManager.addPost(user, msg)
        Success()
      case Failure(e) => Failure(e)
    }
  }

  def comment(postId: Int, msg: String): Try[Unit] = {
    getActiveUser match {
      case Success(user) =>
        postManager.addComment(postId, user, msg) match {
          case Success(_) => Success()
          case Failure(e) => Failure(e)
        }
      case Failure(e) => Failure(e)
    }
  }

  def upvote(postId: Int): Try[Unit] = {
    getActiveUser match {
      case Success(user) =>
        postManager.getPost(postId) match {
          case Some(post) => post.upvote(user)
            Success()
          case None => Failure(new PostOrCommentNotFound)
        }
      case Failure(e) => Failure(e)
    }
  }


  def follow(username: String): Try[Unit] = {
    getActiveUser match {
      case Success(user) =>
        userManager.get(username) match {
          case Some(user2) => user.addFollower(user2)
            Success()
          case None => Failure(new UserNotFoundException(username))
        }
      case Failure(e) => Failure(e)
    }

  }

  def downvote(postId: Int): Try[Unit] = {
    getActiveUser match {
      case Success(user) =>
        postManager.getPost(postId) match {
          case Some(post) => post.downvote(user)
            Success()
          case None => Failure(new PostOrCommentNotFound)
        }
      case Failure(e) => Failure(e)
    }
  }

  def shownewsfeed(): Try[Unit] = {
    getActiveUser match {
      case Success(user) => feedCreator.getFeed(postManager, user).foreach(_.print())
        Success()
      case Failure(e) => Failure(e)
    }
  }
}
