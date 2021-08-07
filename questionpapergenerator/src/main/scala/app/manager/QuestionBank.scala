package app.manager

import app.domain.Question
import app.enums.Attribute
import app.enums.AttributeValues.AttributeValue

import scala.collection.mutable.ArrayBuffer
import scala.util.{Failure, Success, Try}

class QuestionBank(questions: ArrayBuffer[Question] = new ArrayBuffer[Question]()) {
  def add(marks: Int, question: String, attrs: Set[AttributeValue]): Try[Unit] = {
    Attribute.list.forall(attr => attrs.count(attr.isValid) == 1) match {
      case true => questions.addOne(new Question(marks, question, attrs))
        Success()
      case _ => Failure(new RuntimeException("Multiple attributes of the same type not allowed"))
    }
  }

  def modify(question: String, newQuestion: String): Unit = {
    questions.zipWithIndex.filter(_._1.getQuestion.eq(question)).foreach(i => questions(i._2).setQuestion(newQuestion))
  }

  def delete(question: String): Unit = {
    questions.zipWithIndex.filter(_._1.getQuestion.eq(question)).map(i => questions.remove(i._2))
  }

  def getQuestions(filters: Set[AttributeValue]): ArrayBuffer[Question] = {
    questions.filter(q => filters.forall(q.isOfType))
  }
}
