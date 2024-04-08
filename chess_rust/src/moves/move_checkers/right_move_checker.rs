use crate::board::Board;
use crate::gameplay::Position;
use crate::moves::move_checkers::MoveChecker;

#[derive(Debug, Clone, Copy)]
pub struct RightMoveChecker {
    steps: i8,
}

impl RightMoveChecker {
    pub(crate) fn new(steps: i8) -> RightMoveChecker {
        RightMoveChecker { steps }
    }
}

impl MoveChecker for RightMoveChecker {
    fn is_valid(&self, board: &Board, src: &Position, dst: &Position) -> bool {
        if src.x != dst.x {
            return false;
        }
        if self.steps > 0 {
            if dst.y < src.y && dst.y > src.y + self.steps as u8 {
                return false;
            }
            for i in (src.y + 1)..dst.y {
                if let Some(_piece) = board.get_piece(&Position { y: i, x: src.x }) {
                    return false;
                }
            }
        } else {
            if dst.y > src.y && dst.y < src.y - self.steps as u8 {
                return false;
            }
            for i in (dst.y + 1)..src.y {
                if let Some(_) = board.get_piece(&Position { y: i, x: src.x }) {
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