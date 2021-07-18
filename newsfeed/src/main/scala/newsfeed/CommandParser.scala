package newsfeed

import newsfeed.exceptions.ParseException

import scala.util.{Failure, Success, Try}

class CommandParser {
  val app = new App

  def execute(command: String): Unit = {
    println(command)
    var result = command.split("~").toList match {
      case List("signup", username: String) =>  app.signup(username)
      case List("login", username: String) =>  app.login(username)
      case List("post", msg: String) =>  app.post(msg)
      case List("shownewsfeed") =>  app.shownewsfeed()
      case List("follow", username: String) =>  app.follow(username)
      case List("shownewsfeed") =>  app.shownewsfeed()
      case List("reply", postId: String, msg: String) =>  Try(postId.toInt) match {
        case Success(id: Int) => app.comment(id, msg)
        case Failure(e) => Failure(new ParseException(postId))
      }
      case List("upvote", postId: String) =>  Try(postId.toInt) match {
        case Success(id: Int) => app.upvote(id)
        case Failure(e) => Failure(new ParseException(postId))
      }
      case List("downvote", postId: String) =>  Try(postId.toInt) match {
        case Success(id: Int) => app.downvote(id)
        case Failure(e) => Failure(new ParseException(postId))
      }
    }
    result match {
      case Failure(e) => println(e.getMessage)
      case _ =>
    }
  }
}
