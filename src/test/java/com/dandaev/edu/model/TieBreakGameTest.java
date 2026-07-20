package com.dandaev.edu.model;

import com.dandaev.edu.exceptions.domain.GameSetAlreadyFinishedException;
import com.dandaev.edu.exceptions.domain.InvalidPlayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TieBreakGameTest {
    private Player firstPlayer;
    private Player secondPlayer;

    private TieBreakGame tieBreakGame;

    @BeforeEach
    void setup() {
        firstPlayer = new Player("Player I");
        secondPlayer = new Player("Player II");

        tieBreakGame = new TieBreakGame(firstPlayer, secondPlayer);
    }

    @Test
    void shouldStartWithGameNotOver() {
        assertFalse(tieBreakGame.isOver());
    }

    @Test
    void shouldStartWithNoWinner() {
        assertNull(tieBreakGame.getWinner());
    }

    @Test
    void shouldStartWithZeroPointsForBothPlayers() {
        assertAll("Initial points",
                () -> assertEquals(0, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(0, tieBreakGame.getSecondPlayerPoints()));
    }

    @Test
    void shouldScorePointToFirstPlayerAndNotFinishGame() {
        tieBreakGame.scorePointTo(firstPlayer);
        assertAll(() -> assertEquals(0, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(1, tieBreakGame.getFirstPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldScorePointToSecondPlayerAndNotFinishGame() {
        tieBreakGame.scorePointTo(secondPlayer);
        assertAll(() -> assertEquals(0, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(1, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldAccumulatePointsForFirstPlayerWithoutFinishing() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }

        assertAll(() -> assertEquals(6, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(0, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldAccumulatePointsForSecondPlayerWithoutFinishing() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        assertAll(() -> assertEquals(6, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(0, tieBreakGame.getFirstPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldIncreaseFirstPlayerScoreToSix() {
        tieBreakGame.scorePointTo(firstPlayer);
        assertEquals(1, tieBreakGame.getFirstPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        assertEquals(2, tieBreakGame.getFirstPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        assertEquals(3, tieBreakGame.getFirstPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        assertEquals(4, tieBreakGame.getFirstPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        assertEquals(5, tieBreakGame.getFirstPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        assertEquals(6, tieBreakGame.getFirstPlayerPoints());

        assertAll(() -> assertEquals(6, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(0, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldIncreaseSecondPlayerScoreToSix() {
        tieBreakGame.scorePointTo(secondPlayer);
        assertEquals(1, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(secondPlayer);
        assertEquals(2, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(secondPlayer);
        assertEquals(3, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(secondPlayer);
        assertEquals(4, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(secondPlayer);
        assertEquals(5, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(secondPlayer);
        assertEquals(6, tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(0, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(6, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldTrackPointsCorrectlyWhenBothPlayersScore() {
        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        assertEquals(1, tieBreakGame.getFirstPlayerPoints());
        assertEquals(1, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        assertEquals(2, tieBreakGame.getFirstPlayerPoints());
        assertEquals(2, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        assertEquals(3, tieBreakGame.getFirstPlayerPoints());
        assertEquals(3, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        assertEquals(4, tieBreakGame.getFirstPlayerPoints());
        assertEquals(4, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        assertEquals(5, tieBreakGame.getFirstPlayerPoints());
        assertEquals(5, tieBreakGame.getSecondPlayerPoints());

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        assertEquals(6, tieBreakGame.getFirstPlayerPoints());
        assertEquals(6, tieBreakGame.getSecondPlayerPoints());

        assertFalse(tieBreakGame.isOver());
    }

    @Test
    void shouldNotFinishGameAtSixAll() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }

        assertAll(() -> assertEquals(6, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(6, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void firstPlayerShouldWinAtSevenZero() {
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(0, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(7, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void firstPlayerShouldWinAtSevenOne() {
        for (int i = 0; i < 1; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(1, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(7, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void firstPlayerShouldWinAtSevenTwo() {
        for (int i = 0; i < 2; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(2, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(7, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void shouldNotAllowWinnerToChangeAfterTieBreakOver() {
        TieBreakGame game = new TieBreakGame(firstPlayer, secondPlayer);

        for (int i = 0; i < 7; i++) {
            game.scorePointTo(firstPlayer);
        }

        assertTrue(game.isOver());
        assertEquals(firstPlayer, game.getWinner());

        assertThrows(
                GameSetAlreadyFinishedException.class,
                () -> game.scorePointTo(secondPlayer)
        );

        assertEquals(firstPlayer, game.getWinner());
    }

    @Test
    void firstPlayerShouldWinAtSevenThree() {
        for (int i = 0; i < 3; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(3, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(7, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void firstPlayerShouldWinAtSevenFour() {
        for (int i = 0; i < 4; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(4, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(7, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void firstPlayerShouldWinAtSevenFive() {
        for (int i = 0; i < 5; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(5, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(7, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),

                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void firstPlayerShouldWinAtEightSix() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }
        for (int i = 0; i < 8; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(6, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(8, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),

                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void firstPlayerShouldWinAtTenEight() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(firstPlayer);

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(8, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(10, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),

                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void secondPlayerShouldWinAtSevenZero() {
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(7, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(0, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void secondPlayerShouldWinAtSevenOne() {
        for (int i = 0; i < 1; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(7, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(1, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void secondPlayerShouldWinAtSevenTwo() {
        for (int i = 0; i < 2; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(7, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(2, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void secondPlayerShouldWinAtSevenThree() {
        for (int i = 0; i < 3; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(7, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(3, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void secondPlayerShouldWinAtSevenFour() {
        for (int i = 0; i < 4; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(7, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(4, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void secondPlayerShouldWinAtSevenFive() {
        for (int i = 0; i < 5; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(7, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(5, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void secondPlayerShouldWinAtEightSix() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        for (int i = 0; i < 8; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(8, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(6, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void secondPlayerShouldWinAtTenEight() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        tieBreakGame.scorePointTo(secondPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertEquals(10, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(8, tieBreakGame.getFirstPlayerPoints()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void shouldNotFinishAtSevenSix() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }

        tieBreakGame.scorePointTo(firstPlayer);

        assertAll(() -> assertEquals(7, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(6, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()),
                () -> assertEquals(null, tieBreakGame.getWinner()));
    }

    @Test
    void shouldNotFinishAtSixSeven() {
        for (int i = 0; i < 6; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }

        tieBreakGame.scorePointTo(secondPlayer);
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());
        assertAll(() -> assertEquals(6, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(7, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldNotFinishAtSevenSeven() {
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());
        assertAll(() -> assertEquals(7, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(7, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldNotFinishAtEightEight() {
        for (int i = 0; i < 8; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());
        assertAll(() -> assertEquals(8, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(8, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldNotFinishAtTenTen() {
        for (int i = 0; i < 10; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());
        assertAll(() -> assertEquals(10, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(10, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldNotFinishAtNineteenNineteen() {
        for (int i = 0; i < 90; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());
        assertAll(() -> assertEquals(90, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(90, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldNotFinishAtNinetyNineNinetyNine() {
        for (int i = 0; i < 99; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }
        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());
        assertAll(() -> assertEquals(99, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(99, tieBreakGame.getSecondPlayerPoints()),
                () -> assertFalse(tieBreakGame.isOver()));
    }

    @Test
    void shouldFirstPlayerWinAfterExtendedTiebreak() {
        for (int i = 0; i < 99; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }
        tieBreakGame.scorePointTo(firstPlayer);
        tieBreakGame.scorePointTo(firstPlayer);

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(firstPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void shouldSecondPlayerWinAfterExtendedTiebreak() {
        for (int i = 0; i < 99; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
            tieBreakGame.scorePointTo(secondPlayer);
        }
        tieBreakGame.scorePointTo(secondPlayer);
        tieBreakGame.scorePointTo(secondPlayer);

        System.out.println("Score: first player " + tieBreakGame.getFirstPlayerPoints() + " — second player " + tieBreakGame.getSecondPlayerPoints());

        assertAll(() -> assertTrue(tieBreakGame.isOver()),
                () -> assertEquals(secondPlayer, tieBreakGame.getWinner()));
    }

    @Test
    void shouldNotBeOverWhenGameJustStarted() {
        assertFalse(tieBreakGame.isOver());
    }

    @Test
    @DisplayName("Should not have winner when game is just started")
    void shouldNotHaveWinnerWhenGameJustStarted() {
        assertEquals(null, tieBreakGame.getWinner());
    }

    @Test
    void shouldThrowExceptionWhenScoringAfterFirstPlayerWinsAndGameIsOver() {
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }

        assertAll(() -> assertEquals(firstPlayer, tieBreakGame.getWinner()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertThrows(GameSetAlreadyFinishedException.class,
                        () -> tieBreakGame.scorePointTo(firstPlayer)));
    }

    @Test
    void shouldThrowExceptionWhenScoringAfterSecondPlayerWinsAndGameIsOver() {
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(secondPlayer);
        }

        assertAll(() -> assertEquals(secondPlayer, tieBreakGame.getWinner()),
                () -> assertTrue(tieBreakGame.isOver()),
                () -> assertThrows(GameSetAlreadyFinishedException.class,
                        () -> tieBreakGame.scorePointTo(secondPlayer)));
    }

    @Test
    void shouldThrowExceptionForUnknownPlayer() {
        Player unknown = new Player("Stranger");

        assertThrows(InvalidPlayerException.class, () -> tieBreakGame.scorePointTo(unknown));
    }

    @Test
    void shouldNotChangeGameStateWhenScoringAfterGameOver() {
        for (int i = 0; i < 7; i++) {
            tieBreakGame.scorePointTo(firstPlayer);
        }

        int firstPoints = tieBreakGame.getFirstPlayerPoints();
        int secondPoints = tieBreakGame.getSecondPlayerPoints();
        Player winner = tieBreakGame.getWinner();
        boolean isOver = tieBreakGame.isOver();

        assertThrows(
                GameSetAlreadyFinishedException.class,
                () -> tieBreakGame.scorePointTo(secondPlayer)
        );

        assertAll(
                () -> assertEquals(firstPoints, tieBreakGame.getFirstPlayerPoints()),
                () -> assertEquals(secondPoints, tieBreakGame.getSecondPlayerPoints()),
                () -> assertEquals(winner, tieBreakGame.getWinner()),
                () -> assertEquals(isOver, tieBreakGame.isOver())
        );
    }

}
