package app.generator

import app.manager.QuestionBank
import app.Template
import app.domain.{QuestionPaper, Template}

import scala.util.Try

trait QuestionPaperGenerator {
  def generate(marks: Int, questionBank: QuestionBank, template: Template): Try[QuestionPaper]
}
