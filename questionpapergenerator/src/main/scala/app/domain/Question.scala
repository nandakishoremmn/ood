package app.domain

import app.enums.AttributeValues.AttributeValue

class Question(marks: Int, var question: String, attrs: Set[AttributeValue]) {
  def setQuestion(newQuestion: String): Unit = question = newQuestion

  def isOfType(attributeValue: AttributeValue): Boolean = attrs.contains(attributeValue)

  def getMarks: Int = marks
  def getQuestion: String = question

  override def toString = s"$question [$marks] [${attrs.mkString(",")}]"
}