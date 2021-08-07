package app.domain

case class QuestionPaper(marks: Int, questions: List[Question]) {
  override def toString: String = s"Marks: $marks\n${questions.mkString("\n")}"
}
