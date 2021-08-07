package app.domain

import app.enums.Attribute
import app.enums.AttributeValues.AttributeValue

case class Template(filters: Map[Attribute, Map[AttributeValue, Double]]){
  override def toString: String = {
    filters.map(f => {
      s"${f._1}\n${f._2.map(a => s"\t${a._1}: ${a._2.toInt}").mkString("\n")}"
    }).mkString("\n")
  }
}

object Template {
  def create(marks: Int, filters: Map[Attribute, Map[AttributeValue, Double]]): Option[Template] = {
    filters.forall(f => f._2.values.sum == 100 && f._2.keys.forall(f._1.isValid))
    match {
      case true => Option(Template(filters))
      case _ =>
        println("Sum of filters should be 100 and attribute mapping should be correct")
        None
    }
  }

  def combine(templates: Template *): Template = {
    templates.reduce((t1, t2) => {
      Template(t1.filters ++ t2.filters)
    })
  }
}