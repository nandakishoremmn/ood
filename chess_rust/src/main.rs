use std::thread;
use std::time::Duration;

use crate::gameplay::{ChessGame, Game};

mod board;
mod gameplay;
mod moves;

fn main() {
    let mut game = ChessGame::new();

    game.show();
    let moves = "0020\
    6151\
    1020\
    5141\
    2030\
    6353\
    0020\
    5343\
    2021\
    4333\
    2141";
    moves
        .chars()
        .collect::<Vec<char>>() // Collect chars into a vector for chunking
        .chunks(4)
        .map(|chunk| chunk.iter().collect::<String>())
        .for_each(|m| {
            let res = game.make_move(m.as_str().into());
            match res {
                Ok(_) => println!(),
                Err(ge) => println!("{:?}", ge),
            }
            game.show()
        });
    thread::sleep(Duration::from_secs(5))
}
