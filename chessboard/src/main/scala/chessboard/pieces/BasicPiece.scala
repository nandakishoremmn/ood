package chessboard.pieces

import chessboard.enums.Color.{BLACK, Color, WHITE}
import chessboard.enums.PieceType
import chessboard.enums.PieceType.PieceType

class BasicPiece(`type`: PieceType) extends Piece {
  def getValidSquares(board: Array[Array[Option[PieceType]]], start: (Int, Int)): Set[(Int, Int)] = Set()

  override def color: Color = PieceType.getColor(`type`)

  override def toString: String = `type`.toString
}