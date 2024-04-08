use dashmap::DashMap;
use lazy_static::lazy_static;
use std::fmt;
use std::fmt::Formatter;
use std::{borrow::BorrowMut, collections::HashMap};

use std::sync::Arc;

use uuid::Uuid;

#[derive(Debug)]
pub struct BoardError {
    pub message: String,
}

impl BoardError {
    pub fn new(message: String) -> BoardError {
        BoardError { message }
    }
}

impl fmt::Display for BoardError {
    fn fmt(&self, f: &mut Formatter) -> fmt::Result {
        write!(f, "Board error : {}", self.message)
    }
}

// impl From<PoisonError<RwLockReadGuard<'_, HashMap<String, Board>>>> for BoardError {
//     fn from(_value: PoisonError<RwLockReadGuard<'_, HashMap<String, Board>>>) -> Self {
//         BoardError::new("Board lock error!".to_string())
//     }
// }

// impl From<PoisonError<RwLockWriteGuard<'_, HashMap<String, Board>>>> for BoardError {
//     fn from(_value: PoisonError<RwLockWriteGuard<'_, HashMap<String, Board>>>) -> Self {
//         BoardError::new("Board lock error!".to_string())
//     }
// }

// impl From<PoisonError<RwLockReadGuard<'_, Box<dyn BoardManager>>>> for BoardError {
//     fn from(_value: PoisonError<RwLockReadGuard<'_, Box<dyn BoardManager>>>) -> Self {
//         BoardError::new("Board lock error!".to_string())
//     }
// }

// impl From<PoisonError<RwLockWriteGuard<'_, Box<dyn BoardManager>>>> for BoardError {
//     fn from(_value: PoisonError<RwLockWriteGuard<'_, Box<dyn BoardManager>>>) -> Self {
//         BoardError::new("Board lock error!".to_string())
//     }
// }

#[derive(Default)]
pub struct BoardManagerFactory {}

pub trait BoardManager: Send + Sync {
    fn create_board(
        &self,
        board_size: u32,
        snakes: HashMap<u32, u32>,
        ladders: HashMap<u32, u32>,
        player_ids: Vec<u32>,
    ) -> Result<String, BoardError>;
    fn validate(&self, game_id: &str, player_id: u32) -> Result<(), BoardError>;
    fn make_move(&self, game_id: &str, player_id: u32, steps: u32) -> Result<(), BoardError>;
    fn contains_player(&self, game_id: &str, player_id: u32) -> Result<bool, BoardError>;
    fn game_live(&self, game_id: &str) -> Result<(), BoardError>;
}

#[derive(Default)]
pub struct InMemBoardManager {
    boards: DashMap<String, Board>,
}

impl InMemBoardManager {
    fn new() -> InMemBoardManager {
        InMemBoardManager::default()
    }
}

#[derive(Debug)]
struct Board {
    // id: String,
    size: u32,
    // snakes: HashMap<u32, u32>,
    // ladders: HashMap<u32, u32>,
    player_pos: HashMap<u32, u32>,
    // max_cell_limit: u32,
}

impl BoardManager for InMemBoardManager {
    fn create_board(
        &self,
        board_size: u32,
        _snakes: HashMap<u32, u32>,
        _ladders: HashMap<u32, u32>,
        player_ids: Vec<u32>,
    ) -> Result<String, BoardError> {
        if board_size == 0 {
            return Err(BoardError {
                message: format!("Invalid board size: {}", board_size),
            });
        }

        let board_id = Uuid::new_v4().to_string();
        let board = Board {
            // id: board_id.clone(),
            size: board_size,
            // snakes,
            // ladders,
            player_pos: HashMap::from_iter(player_ids.iter().map(|&player_id| (player_id, 0))),
            // max_cell_limit: 1,
        };

        self.boards.insert(board_id.clone(), board);
        Ok(board_id)
    }

    fn validate(&self, game_id: &str, player_id: u32) -> Result<(), BoardError> {
        self.boards
            .get(game_id)
            .ok_or_else(|| BoardError {
                message: "game not found".to_string(),
            })?
            .player_pos
            .get(&player_id)
            .ok_or(BoardError::new("player not found".to_string()))
            .map(|_| ())
    }

    fn make_move(&self, game_id: &str, player_id: u32, steps: u32) -> Result<(), BoardError> {
        println!("player {} rolled {}", player_id, steps);
        self.validate(game_id, player_id)?;
        let mut t = self
            .boards
            .get_mut(game_id)
            .ok_or(BoardError::new("board not found".to_string()))?;
        let board: &mut Board = t.borrow_mut();
        let cur_pos = board
            .player_pos
            .get(&player_id)
            .ok_or(BoardError::new("player not on board".to_string()))?;

        let new_pos = if cur_pos + steps > board.size {
            *cur_pos
        } else {
            *cur_pos + steps
        };

        println!(
            "player {} rolled {} and moved from {} to {}",
            player_id, steps, *cur_pos, &new_pos
        );

        board.player_pos.insert(player_id, new_pos);

        Ok(())
    }

    fn contains_player(&self, game_id: &str, player_id: u32) -> Result<bool, BoardError> {
        self.boards
            .get(game_id)
            .map(|board| board.player_pos.contains_key(&player_id))
            .ok_or(BoardError::new("Board not found".to_string()))
    }

    fn game_live(&self, game_id: &str) -> Result<(), BoardError> {
        let board = self
            .boards
            .get(game_id)
            .ok_or(BoardError::new("invalid game".to_string()))?;
        let size = board.size;

        board
            .player_pos
            .values()
            .all(|p| *p != size)
            .then(|| ())
            .ok_or(BoardError::new("game over".to_string()))
    }
}

lazy_static! {
    static ref BOARD_MANAGER: Arc<Box<dyn BoardManager>> =
        Arc::new(Box::new(BoardManagerFactory::create_inmemboard()));
}

impl BoardManagerFactory {
    pub fn create_inmemboard() -> InMemBoardManager {
        InMemBoardManager::new()
    }

    pub fn get() -> Arc<Box<dyn BoardManager>> {
        BOARD_MANAGER.clone()
    }
}
