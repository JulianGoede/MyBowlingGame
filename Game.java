package BowlingGame.Bowling;

import java.util.Arrays;

/**
 * Created by Julian on 28.06.2017.
 * A class to count the pins of a bowling game
 */
public class Game {

    private final Frame[] frames; // a bowling game consists of 10 frames

    private int frameId; // current frame
    private int curRoll; // roll of current frame

    private boolean over = false;

    public Game() {
        frames = new Frame[10];
        frameId = 0;
        curRoll = 1;
        for (int i = 0; i < frames.length; i++)
            frames[i] = new Frame();
    }

    private void updatePreviousFrames(int pins) {
        if (frameId > 0)
            frames[frameId - 1].updateFromNextRoll(pins);
        if (frameId > 1)
            frames[frameId - 2].updateFromNextRoll(pins);
    }


    /**
     * Adds a role to the current frame and updates scores.
     *
     * @param pins the amount of pins the player managed to knock down
     * @throws Exception if the method gets called after the game is over
     */
    public void addRoll(int pins) throws Exception {
        if (over()) throw new RuntimeException("Game is already over.");
        frames[frameId].addRoll(pins, curRoll);

        // update the score of the previous frames in case of strike/spare
        updatePreviousFrames(pins);
        if (frameId < 9 && (curRoll == 2 || pins == 10)) {
            // go to the next frame
            curRoll = 1;
            frameId++;
        } else
            curRoll++;
    }

    /*
        Prints the game after each roll to the console
         */
    public void addRoll(int pins, boolean gameLog) throws Exception {
        addRoll(pins);
        if (!gameLog) return;


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= frameId; i++) {
            sb.append(frames[i].toString());
            sb.append(", ");
        }
        sb.append("total = ");
        sb.append(totalScore());
        System.out.println(sb.toString());
    }

    public Frame[] frames() {
        return frames;
    }

    public int getFrameId() {
        return frameId;
    }

    public int getCurRoll() {
        return curRoll;
    }

    public int totalScore() {
        return Arrays.stream(frames).mapToInt(frame -> frame.getScore()).sum();
    }

    public boolean over() {
        if (over) return true;
        if (frameId == 9 && curRoll == 4 || (curRoll == 3 && frames[9].getScore() < 10))
            over = true;
        return over;
    }

    /**
     * Calculates the score of a valid game
     *
     * @param frames contains all rolls
     * @return the score
     */
    public static void calculateBowlingScore(String frames, boolean gameLog) {
        Game bowlingGame = new Game();
        try {
            String s = frames.replaceAll("[ \\_\\-]", "");
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'X')
                    bowlingGame.addRoll(10, gameLog);
                else if (s.charAt(i) == '/')
                    bowlingGame.addRoll(10 - Character.getNumericValue(s.charAt(i - 1)), gameLog);
                else
                    bowlingGame.addRoll(Character.getNumericValue(s.charAt(i)), gameLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(String.format("The final score is %d.", bowlingGame.totalScore()));
    }

    private static final boolean game_log = true; // enables/disables the complete log of the game

    public static void main(String[] args) {
        // a sample run from the website
        // the frames can be seperated by -,_ or a space char
        calculateBowlingScore("14 45 6/ 5/ X 01 7/ 6/ X 2/6", game_log);
        System.out.println();
        //calculateBowlingScore("X X X X X X X X X XXX", game_log);
    }

}
