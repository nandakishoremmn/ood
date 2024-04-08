pub(crate) mod up_move_checker;
pub(crate) mod right_move_checker;

use crate::board::Board;
use crate::gameplay::Position;

pub trait MoveChecker {
    fn is_valid(&self, board: &Board, src: &Position, dst: &Position) -> bool;
}