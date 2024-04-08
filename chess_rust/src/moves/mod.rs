mod move_checkers;

use crate::board::{Board, Color, PieceType};
use crate::gameplay::Position;
use move_checkers::MoveChecker;
use crate::moves::move_checkers::right_move_checker::RightMoveChecker;
use crate::moves::move_checkers::up_move_checker::*;

pub struct PieceMoveChecker {
    move_checkers: Vec<Box<dyn MoveChecker>>,
}
impl PieceMoveChecker {
    fn get_move_checkers(&self) -> &Vec<Box<dyn MoveChecker>> {
        return &self.move_checkers;
    }
    pub fn is_valid(&self, board: &Board, src: &Position, dst: &Position) -> bool {
        self.get_move_checkers()
            .iter()
            .any(|mc| mc.is_valid(board, src, dst))
    }

    pub fn new(piece_type: PieceType, color: Color) -> PieceMoveChecker {
        PieceMoveChecker {
            move_checkers: match piece_type {
                PieceType::Pawn => vec![Box::new(match color {
                        Color::White => UpMoveChecker::new(-1),
                        Color::Black => UpMoveChecker::new(1),
                    })],
                PieceType::Rook => vec![
                    Box::new(RightMoveChecker::new(7)),
                    Box::new(RightMoveChecker::new(-7)),
                    Box::new(UpMoveChecker::new(7)),
                    Box::new(UpMoveChecker::new(-7)),
                ],
                PieceType::Knight => vec![],
                PieceType::Bishop => vec![],
                PieceType::Queen => vec![],
                PieceType::King => vec![],
            },
        }
    }
}


