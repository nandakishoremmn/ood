package app

import app.enums.AttributeValues._
import app.enums.{difficulty, topic}
import app.generator.QPGImpl
import app.manager.QuestionBank

object Driver extends App {
  val t0 = Template.create(100, Map(
    topic -> Map(OS -> 75, ALGO -> 25)
  )).get
  val t1 = Template.create(100, Map(
    topic -> Map(OS -> 60, ALGO -> 40)
  )).get
  val t2 = Template.create(100, Map(
    difficulty -> Map(EASY -> 50, HARD -> 50),
  )).get
  val t3 = Template.create(100, Map(
    topic -> Map(OS -> 60, ALGO -> 40)
  )).get
  val t5 = Template.create(100, Map()).get
  val t4 = Template.combine(t2, t3, t5)

  val qpg = new QPGImpl

  val questionBank = new QuestionBank
  questionBank.add(45, "Q1", Set(EASY, ALGO))
  questionBank.add(35, "Q2", Set(MEDIUM, ALGO))
  questionBank.add(25, "Q3", Set(HARD, ALGO))
  questionBank.add(5, "Q4", Set(EASY, OS))
  questionBank.add(5, "Q5", Set(MEDIUM, OS))
  questionBank.add(25, "Q6", Set(HARD, OS))
  questionBank.add(5, "Q61", Set(HARD, OS))
  questionBank.add(5, "Q7", Set(EASY, OS))
  questionBank.add(5, "Q8", Set(EASY, ALGO))
  questionBank.add(5, "Q9", Set(EASY, OS))
  questionBank.add(25, "Q10", Set(EASY, ALGO))
  questionBank.add(20, "Q101", Set(EASY, ALGO))
  questionBank.add(20, "Q102", Set(HARD, ALGO))
  questionBank.add(25, "Q11", Set(EASY, OS))
  questionBank.add(5, "Q12", Set(EASY, OS))

  questionBank.delete("Q12")
  questionBank.modify("Q11", "Q11x")

  Seq(t0, t1, t2, t3, t4, t5).map(t => {
    println("=================")
    println(t)
    println(qpg.generate(100, questionBank, t).getOrElse("failed"))
  })
}
