mod board_manager;
mod dice_manager;

use crate::board_manager::{BoardError, BoardManager};
use crate::dice_manager::{DiceManager, DiceManagerFactory};
use board_manager::BoardManagerFactory;
use maplit::hashmap;
use std::collections::HashMap;
use std::sync::Arc;
use std::thread;

use rand::prelude::StdRng;
use rand::{Rng, SeedableRng};

trait GameApplication: Send + Sync {
    fn create_game(
        &self,
        board_size: u32,
        snakes: HashMap<u32, u32>,
        ladders: HashMap<u32, u32>,
        player_ids: Vec<u32>,
    ) -> Result<String, BoardError>;
    fn hold_dice(&self, game_id: &str, player_id: u32) -> Result<(), BoardError>;
    fn roll_dice_and_move(&self, game_id: &str, player_id: u32) -> Result<(), BoardError>;
}

struct SnakeLadderGame {
    board_manager: Arc<Box<dyn BoardManager>>,
    dice_manager: Arc<Box<dyn DiceManager>>,
}

impl SnakeLadderGame {
    fn new() -> SnakeLadderGame {
        SnakeLadderGame {
            board_manager: BoardManagerFactory::get(),
            dice_manager: Arc::new(DiceManagerFactory::create()),
        }
    }
}

impl GameApplication for SnakeLadderGame {
    fn create_game(
        &self,
        board_size: u32,
        snakes: HashMap<u32, u32>,
        ladders: HashMap<u32, u32>,
        player_ids: Vec<u32>,
    ) -> Result<String, BoardError> {
        let gid = self
            .board_manager
            .create_board(board_size, snakes, ladders, player_ids)?;
        self.dice_manager.create(gid.as_str());
        Ok(gid)
    }

    fn hold_dice(&self, game_id: &str, player_id: u32) -> Result<(), BoardError> {
        self.board_manager.game_live(game_id)?;
        self.board_manager
            .contains_player(game_id, player_id)?
            .then(|| ())
            .ok_or(BoardError::new("Player not in game".to_string()))?;
        self.dice_manager
            .hold_dice(game_id, player_id)
            .map_err(|e| BoardError::new(e.message.to_string()))
    }

    fn roll_dice_and_move(&self, game_id: &str, player_id: u32) -> Result<(), BoardError> {
        let mut rng = StdRng::from_entropy();
        self.board_manager.game_live(game_id)?;
        self.board_manager.contains_player(game_id, player_id)?;
        self.dice_manager
            .get_holder(game_id)
            .ok_or(BoardError::new("Please hold dice first".to_string()))
            .map(|holder_id| holder_id == player_id)?
            .then(|| {
                self.board_manager
                    .make_move(game_id, player_id, rng.gen_range(1..7))
            })
            .ok_or(BoardError::new(
                "Player does not have dice control".to_string(),
            ))??;
        self.dice_manager.release_dice(game_id);
        Ok(())
    }
}

fn main() {
    let gameapp: Arc<Box<dyn GameApplication>> = Arc::new(Box::new(SnakeLadderGame::new()));

    let snakes = hashmap! {
        8 => 2
    };
    let ladders = hashmap! {
        4 => 7
    };
    let res1 = gameapp
        .create_game(10, snakes.clone(), ladders.clone(), vec![1, 3])
        .unwrap()
        .to_string();
    let res = res1.as_str();

    println!("1. {:?}", gameapp.hold_dice("abc", 1));
    println!("2. {:?}", gameapp.hold_dice(res, 2));
    println!("4. {:?}", gameapp.roll_dice_and_move("abc", 1));
    println!("5. {:?}", gameapp.roll_dice_and_move(res, 2));
    println!("6. {:?}", gameapp.hold_dice(res, 1));

    let g1 = gameapp.clone();
    let g2 = gameapp.clone();

    let gid1 = res.to_string();
    let gid2 = res.to_string();

    let t1 = thread::spawn(move || {
        for i in 1..1000 {
            // println!("{} 1 iter : ", i);
            let _ = g1.hold_dice(gid1.as_str(), 1);
            if let Err(e) = g1.roll_dice_and_move(gid1.as_str(), 1) {
                if e.message != "Player does not have dice control" {
                    println!("p1 wins, {:?}, {}", e, i);
                    break;
                } else {
                    println!("player 1 does not have dice control")
                }
            } else {
                // println!("1 rolling")
            }
        }
    });

    let t2 = thread::spawn(move || {
        for i in 1..1000 {
            // println!("{} 3 iter : ", i);
            let _ = g2.hold_dice(gid2.as_str(), 3);
            if let Err(e) = g2.roll_dice_and_move(gid2.as_str(), 3) {
                if e.message != "Player does not have dice control" {
                    println!("p3 wins, {:?}, {}", e, i);
                    break;
                } else {
                    println!("player 3 does not have dice control")
                }
            } else {
                // println!("3 rolling")
            }
        }
    });

    t1.join().unwrap();
    t2.join().unwrap();

    println!("7. {:?}", gameapp.hold_dice(res, 1));
    println!("8. {:?}", gameapp.roll_dice_and_move(res, 1));

    let game2 = gameapp
        .create_game(3, snakes.clone(), ladders.clone(), vec![1, 2])
        .unwrap();

    println!("9. {:?}", gameapp.hold_dice(game2.as_str(), 1));
    println!("10. {:?}", gameapp.roll_dice_and_move(game2.as_str(), 2));
}
