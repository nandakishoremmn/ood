use crate::board::Board;
use crate::gameplay::Position;
use crate::moves::move_checkers::MoveChecker;

#[derive(Debug, Clone, Copy)]
pub struct UpMoveChecker {
    steps: i8,
}

impl UpMoveChecker {
    pub(crate) fn new(steps: i8) -> UpMoveChecker {
        UpMoveChecker { steps }
    }
}

impl MoveChecker for UpMoveChecker {
    fn is_valid(&self, board: &Board, src: &Position, dst: &Position) -> bool {
        if src.y != dst.y {
            return false;
        }
        if self.steps > 0 {
            if dst.x < src.x && dst.x > src.x + self.steps as u8 {
                return false;
            }
            for i in (src.x + 1)..dst.x {
                if let Some(_piece) = board.get_piece(&Position { x: i, y: src.y }) {
                    return false;
                }
            }
        } else {
            if dst.x > src.x && dst.x < src.x - self.steps as u8 {
                return false;
            }
            for i in (dst.x + 1)..src.x {
                if let Some(_piece) = board.get_piece(&Position { x: i, y: src.y }) {
                    return false;
                }
            }
        }
        let src_piece = board.get_piece(src);
        let dst_piece = board.get_piece(dst);

        if let (Some(dst_piece), Some(src_piece)) = (dst_piece, src_piece) {
            if dst_piece.color == src_piece.color {
                return false;
            }
        }

        true
    }
}