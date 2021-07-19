package chessboard.pieces

import chessboard.enums.Color.Color
import chessboard.enums.PieceType.PieceType

trait Piece {
  def color: Color
  def getValidSquares(board: Array[Array[Option[PieceType]]], start: (Int, Int)): Set[(Int, Int)]
  final def isValidMove(board: Array[Array[Option[PieceType]]], start: (Int, Int), end: (Int, Int)): Boolean = {
    getValidSquares(board, start).contains(end)
  }
}