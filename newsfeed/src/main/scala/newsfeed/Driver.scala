package newsfeed

import java.sql.Time

object Driver {
  def main(args: Array[String]): Unit = {
    val commandParser = new CommandParser
    List("signup~lucious",
    "signup~albus",
    "signup~tom",
    "login~tom",
    "post~darkest dark wizard",
    "post~i am lord voldemort",
    "login~lucious",
    "upvote~1",
    "follow~tom",
    "reply~1~im with you dark lord",
    "login~albus",
    "post~happiness can be found",
    "follow~tom",
    "downvote~1",
    "downvote~2",
    "reply~2~LOL!",
    "shownewsfeed").foreach(cmd => {
      Thread.sleep(1000)
      commandParser.execute(cmd)
    })
  }
}
