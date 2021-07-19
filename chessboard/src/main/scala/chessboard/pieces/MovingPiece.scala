package chessboard.pieces

import chessboard.enums.Color.{BLACK, Color, WHITE}
import chessboard.enums.PieceType.PieceType

class MovingPiece(piece: Piece, directions: List[(Int, Int)], steps: Int) extends Piece {
  def getValidSquares(board: Array[Array[Option[PieceType]]], start: (Int, Int)): Set[(Int, Int)] = {
    directions.flatMap(dir => {
      (1 to steps).map(stepNo => {
        (start._1 + stepNo * dir._1, start._2 + stepNo * dir._2)
      }).takeWhile(pos => {
        val validPos = 0 <= pos._1 && pos._1 < 8 && 0 <= pos._2 && pos._2 < 8
        if (!validPos || pos == start) {
          false
        } else {
          board(pos._1)(pos._2) match {
            case Some(curPiece) => false
            case None => true
          }
        }
      })
    }).toSet ++ piece.getValidSquares(board, start)
  }

  override def color: Color = piece.color

  override def toString: String = piece.color.toString
}