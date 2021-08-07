package app.generator

import app.enums.AttributeValues.AttributeValue
import app.manager.QuestionBank
import app.{Template, domain}
import app.domain.{Question, QuestionPaper, Template}

import scala.collection.mutable.ArrayBuffer
import scala.util.{Failure, Success, Try}

class QPGImpl extends QuestionPaperGenerator {
  override def generate(marks: Int, questionBank: QuestionBank, template: Template): Try[QuestionPaper] = {
    val finalQuestions = ArrayBuffer[Question]()
    for ((attr, value) <- permuteAttrs(template)) {
      val questionsList = questionBank.getQuestions(attr)
      getQuestionsForMarks((marks * value / 100.0).intValue(), questionsList) match {
        case None =>
          println(s"Unable to generate for ${attr.mkString(",")}")
          return Failure(new RuntimeException("Unable to generate"))
        case Some(value) => finalQuestions.addAll(value)
      }
    }
    Success(domain.QuestionPaper(marks, finalQuestions.toList))
  }

  private def getQuestionsForMarks(marks: Int, questions: ArrayBuffer[Question], pos: Int = 0): Option[List[Question]] = {
    if (pos >= questions.length) {
      None
    } else if (questions(pos).getMarks == marks) {
      Some(List(questions(pos)))
    } else if (questions(pos).getMarks > marks) {
      getQuestionsForMarks(marks, questions, pos + 1)
    } else {
      getQuestionsForMarks(marks - questions(pos).getMarks, questions, pos + 1) match {
        case Some(qs) => Some(qs :+ questions(pos))
        case None => getQuestionsForMarks(marks, questions, pos + 1)
      }
    }
  }


  private def permuteAttrs(template: Template): Map[Set[AttributeValue], Double] = {
    template.filters.foldLeft(Map(Set[AttributeValue]() -> 100.0))((cur, next) => {
      next._2.flatMap(attr => cur.map(curattr => curattr._1 + attr._1 -> curattr._2 * attr._2 / 100.0))
    })
  }
}
