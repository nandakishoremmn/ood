package newsfeed.feed

import newsfeed.entities.{Post, User}
import newsfeed.managers.PostManager

trait FeedCreator {
  def getFeed(postManager: PostManager, user: User): List[Post]
}
