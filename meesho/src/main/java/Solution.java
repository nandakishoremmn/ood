import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;


interface Board {
    void move(Integer playerId, Integer diceValue);
    boolean validateMove(Integer playerId, Integer diceValue);
    boolean isEnded();

    boolean hasPlayer(int playerId);
}

class SnakeAndLadderBoard implements Board {
    private final int size; // N*N
    private Integer maxCellLimit = 1;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;
    private final Map<Integer, Integer> playerPos; // positions on the board

    public SnakeAndLadderBoard(int size, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, List<Integer> playerPos) {
        this.size = size;
        this.snakes = snakes;
        this.ladders = ladders;
        this.playerPos = playerPos.stream().collect(Collectors.toMap(e->e, e->-1));
    }

    @Override
    public void move(Integer playerId, Integer diceValue) {
        if(!validateMove(playerId, diceValue)) {
            return;
        }
        Integer newPos = playerPos.get(playerId) + diceValue;
        // assuming no snake or ladder at the end of another snake or ladder
        newPos = snakes.getOrDefault(newPos, newPos);
        newPos = ladders.getOrDefault(newPos, newPos);
        Integer finalNewPos = newPos;
        long noOfPlayerInNewPos = playerPos.values().stream().filter(p -> p.equals(finalNewPos)).count();
        if(newPos != size-1 && noOfPlayerInNewPos >= maxCellLimit) {
            System.out.println("Max players exceeded");
            return;
        }
        playerPos.put(playerId, newPos);
    }

    @Override
    public boolean validateMove(Integer playerId, Integer diceValue) {
        // check bounds, initial move
        if(!playerPos.containsKey(playerId)) {
            System.err.println("Player not in board");
            return false;
        }
        Integer curPos = playerPos.get(playerId);
        if(curPos + diceValue >= size) {
            return false;
        }
//        if(curPos == -1 && diceValue != 1) { // get 1 on dice to start
//            return false;
//        }
        return true;
    }

    @Override
    public boolean isEnded() {
        return playerPos.values().stream().allMatch(v -> v == (size-1));
    }

    @Override
    public boolean hasPlayer(int playerId) {
        return playerPos.containsKey(playerId);
    }
}


interface GameApplication {

    /*
    return unique game id
    */
    String createGame(int boardSize, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, List<Integer> playerIds);

    /*
    return false
        - if player not part of this game
        - if the game is already ended
        - if the game id doesn't exist
        - if dice already allocated
    return true if hold dice is succeeded
    */
    Boolean holdDice(String gameId, int playerId);

    /*
    return false 
        - if any player who doesn't hold the dice calls this
        - if dice is not allocated
        - if the game is already ended
        - if the game id doesn't exist
    otherwise roll dice and move and return true
    */
    Boolean rollDiceAndMove(String gameId, int playerId);
}

interface BoardManager {
    String createGame(int boardSize, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, List<Integer> playerIds);
    Board get(String gameId);
}
class InMemBoardManager implements BoardManager {
    private final Map<String, Board> boards = new ConcurrentHashMap<>();
    @Override
    public String createGame(int boardSize, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, List<Integer> playerIds) {
        Board board = new SnakeAndLadderBoard(boardSize, snakes, ladders, playerIds);
        String gameId = Utils.generateId();
        boards.put(gameId, board);
        return gameId;
    }

    @Override
    public Board get(String gameId) {
        return boards.getOrDefault(gameId, null);
    }
}

interface DiceManager {
    void create(String gameId);
    boolean holdDice(String gameId, int playerId);
    Integer getHolder(String gameId);
    void releaseDice(String gameId);
}

class LockDiceManager implements DiceManager {
    private final Map<String, Integer> playerWithDice;
    private final BoardManager boardManager;

    LockDiceManager(BoardManager boardManager) {
        this.boardManager = boardManager;
        this.playerWithDice = new ConcurrentHashMap<>();
    }

    @Override
    public void create(String gameId) {

    }

    @Override
    public boolean holdDice(String gameId, int playerId) {
        Integer playerIdWithDice = this.playerWithDice.computeIfAbsent(gameId, (gId) -> playerId);
        if(playerIdWithDice.equals(playerId)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer getHolder(String gameId) {
        return playerWithDice.get(gameId);
    }

    @Override
    public void releaseDice(String gameId) {
        playerWithDice.remove(gameId);
    }
}

class SnakeLadderGameService implements GameApplication {
    private final BoardManager boardManager;
    private final DiceManager diceManager;

    SnakeLadderGameService() {
        this.boardManager = new InMemBoardManager();
        this.diceManager = new LockDiceManager(boardManager);
    }

    /*
    return unique game id
    */
    public String createGame(int boardSize, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, List<Integer> playerIds) {
        // input validation boardSize is N*N?
        String gameId = boardManager.createGame(boardSize, snakes, ladders, playerIds);
        diceManager.create(gameId);
        return gameId;
    }

    /*
    return false
        - if player not part of this game
        - if the game is already ended
        - if the game id doesn't exist
        - if dice already allocated
    return true if hold dice is succeeded
    */
    public Boolean holdDice(String gameId, int playerId) {
        Board board = boardManager.get(gameId);
        if(board == null) {
            return false;
        }
        if(board.isEnded() == true) {
            return false;
        }
        if(!board.hasPlayer(playerId)) {
            return false;
        }
        return diceManager.holdDice(gameId, playerId);
    }

    /*
    return false 
        - if any player who doesn't hold the dice calls this
        - if dice is not allocated
        - if the game is already ended
        - if the game id doesn't exist
    otherwise roll dice and move and return true
    */
    public Boolean rollDiceAndMove(String gameId, int playerId) {

        Board board = boardManager.get(gameId);
        if(board == null) {
            return false;
        }
        if(board.isEnded() == true) {
            return false;
        }
        if(!board.hasPlayer(playerId)) {
            return false;
        }
        if(diceManager.getHolder(gameId) != null && !diceManager.getHolder(gameId).equals(playerId)) {
            return false;
        }
        board.move(playerId, Utils.generateRandomNumber(1, 6));
        diceManager.releaseDice(gameId);
        return true;
    }
}

class Utils {

    private static final Random random = new Random();

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    // Both inclusive
    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        executeTestCases();

//        bufferedWriter.write("Test cases executed");
//        bufferedWriter.newLine();
//
//        bufferedReader.close();
//        bufferedWriter.close();
    }

    private static void executeTestCases() {
        GameApplication gameApp = new SnakeLadderGameService();
        Map<Integer, Integer> snakes = new HashMap<>();
        Map<Integer, Integer> ladders = new HashMap<>();
        snakes.put(8, 2);
        ladders.put(4, 7);
        String gameId = gameApp.createGame(3, snakes, ladders, Arrays.asList(1, 3));

        print(1, !gameApp.holdDice("abc", 1)); // gameId doesn't exist
        print(2, !gameApp.holdDice(gameId, 2)); // player not part
        print(3, !gameApp.rollDiceAndMove("abc", 1)); // gameId doesn't exist
        print(4, !gameApp.rollDiceAndMove(gameId, 2)); // player not part
        print(5, gameApp.holdDice(gameId, 1)); // should be true as there is only one player
        int i=0;
        while(i<1000) { // should win
            gameApp.holdDice(gameId, 1);
            if(!gameApp.rollDiceAndMove(gameId, 1)){
                break;
            }
            gameApp.holdDice(gameId, 3);
            if(!gameApp.rollDiceAndMove(gameId, 3)){
                break;
            }
            i++;
        }
        print(6, i!=1000);
        print(7, !gameApp.holdDice(gameId, 1)); // game is already ended
        print(8, !gameApp.rollDiceAndMove(gameId, 1)); // game is already ended

        String gameId2 = gameApp.createGame(3, snakes, ladders, Arrays.asList(1, 2));
        print(9, gameApp.holdDice(gameId2, 1) && !gameApp.holdDice(gameId2, 2)); // hold dice
        print(10, !gameApp.rollDiceAndMove(gameId2, 2)); // player not holding dice calls
    }

    private static void print(int testCaseNumber, boolean success) {
        if(success) {
            System.out.println(String.format("Test case: %d - Success", testCaseNumber));
        } else {
            System.out.println(String.format("Test case: %d - Failed", testCaseNumber));
        }
    }
}