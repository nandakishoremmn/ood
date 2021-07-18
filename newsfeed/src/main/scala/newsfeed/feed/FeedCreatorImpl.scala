package newsfeed.feed

import java.util.Date

import newsfeed.entities.{Post, User}
import newsfeed.managers.PostManager

class FeedCreatorImpl extends FeedCreator {
  override def getFeed(postManager: PostManager, user: User): List[Post] = {
    postManager.getPosts.filter(!_.isComment)
      .sortBy(p => (!p.isFollowedBy(user), -p.score, -p.commentsCount, -p.postedOn.getTime))
  }
}
