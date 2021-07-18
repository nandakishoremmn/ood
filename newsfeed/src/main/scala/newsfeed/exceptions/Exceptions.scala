package newsfeed.exceptions

class PostOrCommentNotFound extends Exception("Post or comment not found")
class UserExistsException extends Exception("User already exists")
class UserNotFoundException(s: String) extends Exception(s"User $s not found")
class ParseException(s: String) extends Exception(s"Failed to parse [$s]")
class NoLoggedInuserException extends Exception("No user has logged in")