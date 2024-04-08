use std::thread::sleep;
use std::time::Duration;

use crate::board::{Board, Color};

pub struct Move {
    src: Position,
    dst: Position,
}

impl From<&str> for Move {
    fn from(value: &str) -> Self {
        let digits: Vec<u8> = value
            .chars()
            .map(|c| c.to_digit(10).expect("Not a digit") as u8)
            .collect();
        Move {
            src: Position::new(
                *digits.get(0).expect("First digit missing"),
                *digits.get(1).expect("Second digit missing"),
            ),
            dst: Position::new(
                *digits.get(2).expect("Third digit missing"),
                *digits.get(3).expect("Fourth digit missing"),
            ),
        }
    }
}
pub struct Position {
    pub x: u8,
    pub y: u8,
}

impl Position {
    pub fn new(x: u8, y: u8) -> Position {
        Position { x, y }
    }
}

pub trait Game {
    fn make_move(&mut self, mve: Move) -> Result<(), GameError>;
    fn show(&self);
}

pub struct ChessGame {
    pub(crate) board: Board,
    color: Color,
}

impl ChessGame {
    pub fn new() -> ChessGame {
        ChessGame {
            board: Board::new(),
            color: Color::White,
        }
    }
}

#[derive(Debug)]
pub enum GameError {
    NoPieceError,
    InvalidMoveError,
    NotYourTurn,
}

impl Game for ChessGame {
    fn make_move(&mut self, mve: Move) -> Result<(), GameError> {
        let Move { src, dst } = mve;
        match self.board.get_piece(&src) {
            None => Err(GameError::NoPieceError),
            Some(piece) => {
                if piece.color != self.color {
                    return Err(GameError::NotYourTurn);
                }
                match piece.is_valid_move(&self.board, &src, &dst) {
                    true => {
                        self.board.update(&src, &dst);
                        Ok(())
                    }
                    false => Err(GameError::InvalidMoveError),
                }
            }
        }
        .map(|_| match self.color {
            Color::White => self.color = Color::Black,
            Color::Black => self.color = Color::White,
        })
    }

    fn show(&self) {
        sleep(Duration::from_secs(1));
        self.board.show();
    }
}
