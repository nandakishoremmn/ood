package chessboard.pieces.factories

import chessboard.enums.PieceType.{BB, BH, BK, BP, BQ, BR, PieceType, WB, WH, WK, WP, WQ, WR}
import chessboard.pieces._

object PieceFactory {
  val DIAG_DIRS = List((1, 1), (-1, -1), (1, -1), (-1, 1))
  val PLUS_DIRS = List((0, 1), (0, -1), (1, 0), (-1, 0))
  val WHITE_PAWN_CAPTURE_DIRS = List((-1, -1), (-1, 1))
  val BLACK_PAWN_CAPTURE_DIRS = List((1, -1), (1, 1))
  val HORSE_DIRS = List((1, 2), (1, -2), (-1, 2), (-1, -2), (2, 1), (2, -1), (-2, 1), (-2, -1))

  def get(piece: PieceType): Piece = {
    val basicPiece = new BasicPiece(piece)

    piece match {
      case WB | BB => new CapturingPiece(new MovingPiece(basicPiece, DIAG_DIRS, 7), DIAG_DIRS, 7)
      case WR | BR => new CapturingPiece(new MovingPiece(basicPiece, PLUS_DIRS, 7), PLUS_DIRS, 7)
      case WQ | BQ => new CapturingPiece(new MovingPiece(basicPiece, DIAG_DIRS ++ PLUS_DIRS, 7), DIAG_DIRS ++ PLUS_DIRS, 7)
      case WH | BH => new CapturingPiece(new MovingPiece(basicPiece, HORSE_DIRS, 1), HORSE_DIRS, 1)
      case WK | BK => new CapturingPiece(new MovingPiece(basicPiece, DIAG_DIRS ++ PLUS_DIRS, 1), DIAG_DIRS ++ PLUS_DIRS, 1)
      case WP => new FastPawnPiece(
        new CapturingPiece(
          new MovingPiece(basicPiece, List((-1, 0)), 1), WHITE_PAWN_CAPTURE_DIRS, 1
        ), List((-1, 0)), 6)
      case BP => new FastPawnPiece(
        new CapturingPiece(
          new MovingPiece(basicPiece, List((1, 0)), 1), BLACK_PAWN_CAPTURE_DIRS, 1
        ), List((1, 0)), 1)
    }
  }
}
