package BowlingGame.Bowling;

/**
 * Created by Julian on 28.06.2017.
 * The game consists of 10 frames.
 */
public class Frame {

    private int[] pinsRolled = {-1, -1, -1};

    private int score;

    private int extraRolls; // extra points for 2 rolls if strike and 1 if spare, respectively

    Frame() {
    }

    public int getScore() {
        return score;
    }

    void addRoll(int pins, int roll) {
        pinsRolled[roll - 1] = pins;
        score += pins;
        if (pinsRolled[0] == 10) extraRolls = 2; // strike
        else if (pinsRolled[0] + pinsRolled[1] == 10) extraRolls = 1; // spare
    }

    void updateFromNextRoll(int pins) {
        if (extraRolls > 0) {
            score += pins;
            extraRolls--;
        }
    }

    @Override
    public String toString() {
        if (pinsRolled[0] == -1)
            return "([],0)";
        else if (pinsRolled[1] == -1)
            return String.format("([%d],%d)", pinsRolled[0], score);
        else if (pinsRolled[2] == -1)
            return String.format("([%d,%d],%d)", pinsRolled[0], pinsRolled[1], score);
        else
            return String.format("([%d,%d,%d],%d)", pinsRolled[0], pinsRolled[1], pinsRolled[2], score);
    }
}
