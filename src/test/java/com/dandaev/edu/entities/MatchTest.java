package com.dandaev.edu.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {
    private Player firstPlayer;
    private Player secondPlayer;

    private Match match;

    @BeforeEach
    void setup() {
        firstPlayer = new Player("Player I");
        secondPlayer = new Player("Player II");

        match = new Match(firstPlayer, secondPlayer);
    }

    @Test
    void shouldStartWithNoWinner() {
        assertNull(match.getWinner());
    }

    @Test
    void shouldStartWithMatchNotOver() {
        assertFalse(match.isOver());
    }

    @Test
    void shouldStartWithZeroSetsForBothPlayers() {
        assertAll(
                () -> assertEquals(0, match.getSetsWonBy(firstPlayer)),
                () -> assertEquals(0, match.getSetsWonBy(secondPlayer)));
    }

    @Test
    void shouldIncreaseFirstPlayerSetsAfterWinningSet() {
        increaseWonGameSetsByOne(firstPlayer);

        assertAll(
                () -> assertEquals(1, match.getSetsWonBy(firstPlayer)),
                () -> assertEquals(0, match.getSetsWonBy(secondPlayer)),
                () -> assertFalse(match.isOver())
        );
    }

    @Test
    void shouldIncreaseSecondPlayerSetsAfterWinningSet() {
        increaseWonGameSetsByOne(secondPlayer);

        assertAll(
                () -> assertEquals(1, match.getSetsWonBy(secondPlayer)),
                () -> assertEquals(0, match.getSetsWonBy(firstPlayer)),
                () -> assertFalse(match.isOver()));

    }

    @Test
    void shouldTrackSetsScoreCorrectlyAfterMultipleSets() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(secondPlayer);

        assertAll(
                () -> assertEquals(1, match.getSetsWonBy(firstPlayer)),
                () -> assertEquals(1, match.getSetsWonBy(secondPlayer)),
                () -> assertFalse(match.isOver())
        );
    }

    @Test
    void shouldNotBeOverAfterFirstPlayerWinsOneSet() {
        increaseWonGameSetsByOne(firstPlayer);

        assertFalse(match.isOver());
    }

    @Test
    void shouldNotBeOverAfterSecondPlayerWinsOneSet() {
        increaseWonGameSetsByOne(secondPlayer);

        assertFalse(match.isOver());
    }

    @Test
    void shouldFirstPlayerWinMatchAtTwoSetsToZero() {
        increaseWonGameSetsByOne(firstPlayer); // выиграл 1 сет
        increaseWonGameSetsByOne(firstPlayer); // выиграл 2 сет

        assertAll(
                () -> assertTrue(match.isOver()),
                () -> assertEquals(firstPlayer, match.getWinner())
        );
    }

    @Test
    void shouldSecondPlayerWinMatchAtTwoSetsToZero() {
        increaseWonGameSetsByOne(secondPlayer); // выиграл 1 сет
        increaseWonGameSetsByOne(secondPlayer); // выиграл 2 сет

        assertAll(
                () -> assertTrue(match.isOver()),
                () -> assertEquals(secondPlayer, match.getWinner())
        );
    }

    @Test
    void shouldFirstPlayerWinMatchAtTwoSetsToOne() {
        increaseWonGameSetsByOne(firstPlayer); // выиграл 1 сет
        increaseWonGameSetsByOne(secondPlayer); // выиграл 1 сет

        increaseWonGameSetsByOne(firstPlayer); // выиграл 1 сет

        assertAll(
                () -> assertTrue(match.isOver()),
                () -> assertEquals(firstPlayer, match.getWinner())
        );
    }

    @Test
    void shouldSecondPlayerWinMatchAtTwoSetsToOne() {
        increaseWonGameSetsByOne(firstPlayer); // выиграл 1 сет
        increaseWonGameSetsByOne(secondPlayer); // выиграл 1 сет

        increaseWonGameSetsByOne(secondPlayer); // выиграл 1 сет

        assertAll(
                () -> assertTrue(match.isOver()),
                () -> assertEquals(secondPlayer, match.getWinner())
        );
    }

    @Test
    void shouldReturnCorrectWinnerAfterFirstPlayerWinsMatch() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(firstPlayer);

        assertAll(
                () -> assertTrue(match.isOver()),
                () -> assertEquals(firstPlayer, match.getWinner())
        );
    }

    @Test
    void shouldReturnCorrectWinnerAfterSecondPlayerWinsMatch() {
        increaseWonGameSetsByOne(secondPlayer);
        increaseWonGameSetsByOne(secondPlayer);

        assertAll(
                () -> assertTrue(match.isOver()),
                () -> assertEquals(secondPlayer, match.getWinner())
        );
    }

    @Test
    void shouldReturnFalseIsOverBeforeMatchIsFinished() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(secondPlayer);

        assertFalse(match.isOver());
    }

    @Test
    void shouldReturnTrueIsOverAfterMatchFinished() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(firstPlayer);

        assertTrue(match.isOver());
    }

    @Test
    void shouldThrowExceptionWhenPointScoredAfterMatchIsOver() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(firstPlayer);

        assertEquals(firstPlayer, match.getWinner());
        assertTrue(match.isOver());


        assertThrows(
                IllegalStateException.class,
                () -> increaseWonGameSetsByOne(firstPlayer)
        );
    }

    @Test
    void shouldKeepStateUnchangedWhenPointScoredAfterMatchIsOver() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(firstPlayer);

        Player winnerBefore = match.getWinner();
        boolean isOverBefore = match.isOver();
        int setsBefore = match.getSetsWinByPlayers().get(firstPlayer);

        assertThrows(
                IllegalStateException.class,
                () -> match.scorePointTo(secondPlayer)
        );

        assertAll(
                () -> assertEquals(winnerBefore, match.getWinner()),
                () -> assertEquals(isOverBefore, match.isOver()),
                () -> assertEquals(setsBefore,
                        match.getSetsWinByPlayers().get(firstPlayer))
        );
    }

    @Test
    void shouldNotAllowWinnerToChangeAfterMatchIsOver() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(firstPlayer);

        assertEquals(firstPlayer, match.getWinner());

        assertThrows(
                IllegalStateException.class,
                () -> match.scorePointTo(secondPlayer)
        );

        assertEquals(firstPlayer, match.getWinner());
    }

    @Test
    void shouldThrowExceptionForUnknownPlayer() {
        Player stranger = new Player("Stranger");

        assertThrows(IllegalArgumentException.class, () -> increaseWonGameSetsByOne(stranger));
    }

    @Test
    void shouldStartNewSetAfterFirstSetCompleted() {
        increaseWonGameSetsByOne(firstPlayer);

        assertEquals(1, match.getSetsWonBy(firstPlayer));
        assertFalse(match.isOver());

        match.scorePointTo(firstPlayer);

        assertFalse(match.isOver());
        assertEquals(1, match.getSetsWonBy(firstPlayer));
    }

    @Test
    void shouldStartThirdSetAfterSecondSetCompleted() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(secondPlayer);

        assertFalse(match.isOver());

        match.scorePointTo(firstPlayer);

        assertEquals(1, match.getSetsWonBy(firstPlayer));
        assertEquals(1, match.getSetsWonBy(secondPlayer));
    }

    @Test
    void shouldNotFinishMatchBeforeSecondSetWon() {
        for (int i = 0; i < 23; i++) {
            match.scorePointTo(firstPlayer);
        }

        assertFalse(match.isOver());
        assertNull(match.getWinner());
    }

    @Test
    void shouldStoreCorrectFinalScoreWhenFirstPlayerWinsTwoToZero() {
        increaseWonGameSetsByOne(firstPlayer);
        increaseWonGameSetsByOne(firstPlayer);

        assertEquals(2, match.getSetsWonBy(firstPlayer));
        assertEquals(0, match.getSetsWonBy(secondPlayer));
    }



    @Test
    void shouldThrowExceptionWhenGettingSetsForUnknownPlayer() {
        Player stranger = new Player("Stranger");

        assertThrows(
                IllegalArgumentException.class,
                () -> match.getSetsWonBy(stranger)
        );
    }

    void increaseWonGameSetsByOne(Player player) {
        for (int i = 0; i < 24; i++) {
            match.scorePointTo(player);
        }
    }


}
