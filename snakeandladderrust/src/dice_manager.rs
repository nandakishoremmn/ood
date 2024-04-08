use dashmap::DashMap;
use std::borrow::BorrowMut;
use std::sync::{PoisonError, RwLockWriteGuard};

#[derive(Debug)]
pub struct DiceError {
    pub(crate) message: String,
}

impl DiceError {
    pub fn new(message: String) -> DiceError {
        DiceError { message }
    }
}

pub trait DiceManager: Send + Sync {
    fn create(&self, game_id: &str);
    fn hold_dice(&self, game_id: &str, player_id: u32) -> Result<(), DiceError>;
    fn get_holder(&self, game_id: &str) -> Option<u32>;
    fn release_dice(&self, game_id: &str);
}

pub struct DiceManagerFactory {}

impl DiceManagerFactory {
    pub(crate) fn create() -> Box<dyn DiceManager> {
        Box::new(InMemDiceManager {
            player_with_dice: Default::default(),
        })
    }
}

#[derive(Debug)]
pub struct InMemDiceManager {
    player_with_dice: DashMap<String, u32>,
}

impl From<PoisonError<RwLockWriteGuard<'_, Option<u32>>>> for DiceError {
    fn from(_value: PoisonError<RwLockWriteGuard<'_, Option<u32>>>) -> Self {
        DiceError::new("Dice in use!".to_string())
    }
}

impl DiceManager for InMemDiceManager {
    fn create(&self, _game_id: &str) {}

    fn hold_dice(&self, game_id: &str, player_id: u32) -> Result<(), DiceError> {
        // check player part of game
        self.player_with_dice
            .entry(game_id.to_string())
            .or_insert(player_id);
        let mut t = *self
            .player_with_dice
            .get_mut(game_id)
            .ok_or(DiceError::new("board not found".to_string()))?;

        let player_with_dice: &mut u32 = t.borrow_mut();
        if *player_with_dice == player_id {
            Ok(())
        } else {
            Err(DiceError::new("Dice not available".to_string()))
        }
    }

    fn get_holder(&self, game_id: &str) -> Option<u32> {
        Some(*self.player_with_dice.get(game_id)?)
    }

    fn release_dice(&self, game_id: &str) {
        self.player_with_dice.remove(game_id);
    }
}
