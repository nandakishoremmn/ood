package app.enums

import AttributeValues._

sealed class Attribute(values: Set[AttributeValue], name: String) {
  def isValid(attributeValue: AttributeValue): Boolean = values contains attributeValue

  override def toString: String = name
}

object difficulty extends Attribute(Set(EASY, MEDIUM, HARD), "Difficulty")
object topic extends Attribute(Set(OS, ALGO), "Topic")

object Attribute {
  val list = List(difficulty, topic)
}