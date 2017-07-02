package BowlingGame;

import BowlingGame.Bowling.Game;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Julian on 29.06.2017.
 */
public class GameTest {
    @Test
    public void addRoll() throws Exception {
        Game bowling = new Game();
        bowling.addRoll(5);
        assertFalse(bowling.over());
        bowling.addRoll(3);

        bowling.addRoll(0);
        bowling.addRoll(10);
    }

    @Test
    public void testSpare() throws Exception {
        Game bowling = new Game();
        bowling.addRoll(2);
        bowling.addRoll(8); // this is a spare

        bowling.addRoll(3);
        assertEquals("Extrapoints from Spare", 13, bowling.frames()[0].getScore());
        bowling.addRoll(7);
        assertEquals("Previous frame mustn't change", 13, bowling.frames()[0].getScore());
    }

    @Test
    public void testSpare2() throws Exception {
        Game bowling = new Game();
        // this is not a strike
        bowling.addRoll(0);
        bowling.addRoll(10);

        bowling.addRoll(5);
        bowling.addRoll(5);
        assertEquals("Spare only grants extra points for the next roll", 15, bowling.frames()[0].getScore());
    }


    @Test
    public void testStrike() throws Exception {
        Game bowling = new Game();
        bowling.addRoll(10);
        assertEquals("Next Frame after strike", 1, bowling.getFrameId());
        bowling.addRoll(2);
        assertEquals("Extra points from Strike", 12, bowling.frames()[0].getScore());
        bowling.addRoll(3);
        assertEquals("Extra points from Strike", 15, bowling.frames()[0].getScore());

        bowling.addRoll(7);
        assertEquals("Only 2 times extra points from strike", 15, bowling.frames()[0].getScore());
    }


    @Test
    public void totalScore() throws Exception {
        Game perfectGame = new Game();
        perfectGame.addRoll(10);
        assertEquals("Start", 10, perfectGame.totalScore());

        perfectGame.addRoll(10);
        assertEquals("Update Previous", 30, perfectGame.totalScore());

        perfectGame.addRoll(10);
        assertEquals(60, perfectGame.totalScore());

        for (int i = 4; i <= 12; i++)
            perfectGame.addRoll(10);
        assertEquals("Perfect game", 300, perfectGame.totalScore());
        assertTrue(perfectGame.over());
    }

}