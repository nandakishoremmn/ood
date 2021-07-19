package chessboard

import chessboard.enums.PieceType
import PieceType.PieceType
import chessboard.pieces.factories.PieceFactory

import scala.util.{Failure, Success, Try}

object Chessboard {
  val defaultPos: String =
    """BR BH BB BQ BK BB BH BR
      |BP BP BP BP BP BP BP BP
      |[] [] [] [] [] [] [] []
      |[] [] [] [] [] [] [] []
      |[] [] [] [] [] [] [] []
      |[] [] [] [] [] [] [] []
      |WP WP WP WP WP WP WP WP
      |WR WH WB WQ WK WB WH WR""".stripMargin
}

class Chessboard(initPos: String = Chessboard.defaultPos) {
  private val board = initPos.split("\n")
    .map(_.split(" ").map(_.stripLineEnd).map(PieceType.parse))


  private def validateBoard(): Unit = {

  }

  def move(piece: PieceType, start: (Int, Int), end: (Int, Int)): Try[Unit] = {
    if(!(0 <= start._1 && start._1 < 8 && 0 <= start._2 && start._2 < 8)) {
      Failure(new Exception("Invalid move"))
    } else {
      board(start._1)(start._2).map(_ == piece) match {
        case Some(true) => PieceFactory.get(piece).isValidMove(board, start, `end`) match {
          case true =>
            board(`end`._1)(`end`._2) = board(start._1)(start._2)
            board(start._1)(start._2) = None
            Success()
          case false => Failure(new Exception("Invalid move"))
        }
        case Some(false) | None => Failure(new Exception(s"Piece $piece not found at location $start. [piece found : ${board(start._1)(start._2)}]"))
      }
    }
  }

  override def toString: String = {
    board.zipWithIndex.map {
      case (row, i) => row.zipWithIndex map {
        case (p, j) => p match {
          case Some(pieceType: PieceType) => pieceType.toString
          case _ => if((i+j) % 2 == 1) "██" else "[]"
        }
      }
    }.map(_.mkString(" ")).mkString("\n")
  }
}
