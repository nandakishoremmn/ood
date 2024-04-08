use crate::gameplay::Position;
use crate::moves::PieceMoveChecker;
use colored::{ColoredString, Colorize};
use std::fmt;
use std::ops::Index;

trait Printable {
    fn print(&self) -> ColoredString;
}
#[derive(Copy, Clone, Debug, PartialEq)]
pub enum Color {
    White,
    Black,
}
#[derive(Copy, Clone, Debug, PartialEq)]
pub enum PieceType {
    Pawn,
    Rook,
    Knight,
    Bishop,
    Queen,
    King,
}

impl fmt::Display for PieceType {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "{:?}", self)
    }
}

pub struct Piece {
    piece_type: PieceType,
    pub color: Color,
    piece_move_checker: PieceMoveChecker,
}

impl Piece {
    fn new(piece_type: PieceType, color: Color) -> Piece {
        Piece {
            piece_type,
            color,
            piece_move_checker: PieceMoveChecker::new(piece_type, color),
        }
    }

    pub fn is_valid_move(&self, board: &Board, src: &Position, dst: &Position) -> bool {
        self.piece_move_checker.is_valid(board, src, dst)
    }
}

trait GetChessSymbol {
    fn get(&self) -> &str;
}

impl GetChessSymbol for Piece {
    fn get(&self) -> &str {
        match *self {
            Piece {
                piece_type: PieceType::Pawn,
                color: Color::White,
                ..
            } => "♙",
            Piece {
                piece_type: PieceType::Rook,
                color: Color::White,
                ..
            } => "♖",
            Piece {
                piece_type: PieceType::Knight,
                color: Color::White,
                ..
            } => "♘",
            Piece {
                piece_type: PieceType::Bishop,
                color: Color::White,
                ..
            } => "♗",
            Piece {
                piece_type: PieceType::Queen,
                color: Color::White,
                ..
            } => "♕",
            Piece {
                piece_type: PieceType::King,
                color: Color::White,
                ..
            } => "♔",
            Piece {
                piece_type: PieceType::Pawn,
                color: Color::Black,
                ..
            } => "♟",
            Piece {
                piece_type: PieceType::Rook,
                color: Color::Black,
                ..
            } => "♜",
            Piece {
                piece_type: PieceType::Knight,
                color: Color::Black,
                ..
            } => "♞",
            Piece {
                piece_type: PieceType::Bishop,
                color: Color::Black,
                ..
            } => "♝",
            Piece {
                piece_type: PieceType::Queen,
                color: Color::Black,
                ..
            } => "♛",
            Piece {
                piece_type: PieceType::King,
                color: Color::Black,
                ..
            } => "♚",
        }
    }
}

impl Printable for Piece {
    fn print(&self) -> ColoredString {
        let binding = self.piece_type.to_string();
        let letter = binding.index(0..1);
        match self.color {
            Color::White => letter.blue(),
            Color::Black => letter.black(),
        }
    }
}

pub struct Board {
    grid: Vec<Vec<Option<Piece>>>,
}

impl Board {
    pub fn get_piece(&self, pos: &Position) -> &Option<Piece> {
        &self.grid[pos.x as usize][pos.y as usize]
    }

    pub fn update(&mut self, src: &Position, dst: &Position) {
        self.grid[dst.x as usize][dst.y as usize] =
            self.grid[src.x as usize][src.y as usize].take();
    }
    pub fn new() -> Board {
        let grid: Vec<Vec<Option<Piece>>> = vec![
            vec![
                Some(Piece::new(PieceType::Rook, Color::Black)),
                Some(Piece::new(PieceType::Knight, Color::Black)),
                Some(Piece::new(PieceType::Bishop, Color::Black)),
                Some(Piece::new(PieceType::Queen, Color::Black)),
                Some(Piece::new(PieceType::King, Color::Black)),
                Some(Piece::new(PieceType::Bishop, Color::Black)),
                Some(Piece::new(PieceType::Knight, Color::Black)),
                Some(Piece::new(PieceType::Rook, Color::Black)),
            ],
            vec![
                Some(Piece::new(PieceType::Pawn, Color::Black)),
                Some(Piece::new(PieceType::Pawn, Color::Black)),
                Some(Piece::new(PieceType::Pawn, Color::Black)),
                Some(Piece::new(PieceType::Pawn, Color::Black)),
                Some(Piece::new(PieceType::Pawn, Color::Black)),
                Some(Piece::new(PieceType::Pawn, Color::Black)),
                Some(Piece::new(PieceType::Pawn, Color::Black)),
                Some(Piece::new(PieceType::Pawn, Color::Black)),
            ],
            vec![None, None, None, None, None, None, None, None],
            vec![None, None, None, None, None, None, None, None],
            vec![None, None, None, None, None, None, None, None],
            vec![None, None, None, None, None, None, None, None],
            vec![
                Some(Piece::new(PieceType::Pawn, Color::White)),
                Some(Piece::new(PieceType::Pawn, Color::White)),
                Some(Piece::new(PieceType::Pawn, Color::White)),
                Some(Piece::new(PieceType::Pawn, Color::White)),
                Some(Piece::new(PieceType::Pawn, Color::White)),
                Some(Piece::new(PieceType::Pawn, Color::White)),
                Some(Piece::new(PieceType::Pawn, Color::White)),
                Some(Piece::new(PieceType::Pawn, Color::White)),
            ],
            vec![
                Some(Piece::new(PieceType::Rook, Color::White)),
                Some(Piece::new(PieceType::Knight, Color::White)),
                Some(Piece::new(PieceType::Bishop, Color::White)),
                Some(Piece::new(PieceType::Queen, Color::White)),
                Some(Piece::new(PieceType::King, Color::White)),
                Some(Piece::new(PieceType::Bishop, Color::White)),
                Some(Piece::new(PieceType::Knight, Color::White)),
                Some(Piece::new(PieceType::Rook, Color::White)),
            ],
        ];
        Board { grid }
    }

    pub fn show(&self) {
        println!("------------------------------------------------");
        self.grid.iter().enumerate().for_each(|(i, row)| {
            row.iter().enumerate().for_each(|(j, elem)| match elem {
                Some(elem) => print!("{}", elem.get()),
                None => {
                    if (i + j) % 2 == 0 {
                        print!("{}", "_".white())
                    } else {
                        print!("{}", "_".black())
                    }
                }
            });
            // print!("|");
            println!();
        });
        println!("------------------------------------------------");
    }
}
