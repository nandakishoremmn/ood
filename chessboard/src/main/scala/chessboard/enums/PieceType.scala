package chessboard.enums

import chessboard.enums.Color.{BLACK, WHITE}

object PieceType extends Enumeration {
  type PieceType = Value
  val BR, BH, BB, BQ, BK, BP, WR, WH, WB, WQ, WK, WP = Value

  def getColor(pieceType: PieceType): Color.Value = if (pieceType.toString.startsWith("B")) BLACK else WHITE

  def parse(name: String): Option[PieceType.Value] = values.find(_.toString == name)
}
