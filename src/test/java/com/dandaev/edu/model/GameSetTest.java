package com.dandaev.edu.model;

import com.dandaev.edu.exceptions.domain.GameSetAlreadyFinishedException;
import com.dandaev.edu.exceptions.domain.InvalidPlayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


class GameSetTest {
    private Player firstPlayer;
    private Player secondPlayer;

    private GameSet gameSet;

    @BeforeEach
    void setUp() {
        firstPlayer = new Player("Player I");
        secondPlayer = new Player("Player II");

        gameSet = new GameSet(firstPlayer, secondPlayer);
    }

    @Test
    void shouldStartWithNoWinner() {
        assertNull(gameSet.getWinner());
    }

    @Test
    void shouldStartWithSetNotOver() {
        assertFalse(gameSet.isOver());
    }

    @Test
    void shouldStartWithBothPlayersHavingZeroGames() {
        assertAll(() -> assertEquals(0, gameSet.getGamesWonByPlayer().get(firstPlayer)),
                () -> assertEquals(0, gameSet.getGamesWonByPlayer().get(secondPlayer)));
    }

    @Test
    void shouldIncreaseFirstPlayerGamesSetWhenFirstPlayerWinsGame() {
        winRegularGame(firstPlayer);

        assertEquals(1, gameSet.getGamesWonByPlayer().get(firstPlayer));
    }

    @Test
    void shouldIncreaseSecondPlayerGamesWhenSecondPlayerWinsGame() {
        winRegularGame(secondPlayer);

        assertEquals(1, gameSet.getGamesWonByPlayer().get(secondPlayer));
    }

    @Test
    void shouldTrackGamesScoreCorrectlyAfterMultipleGames() {
        int firstPLayerGameWinsCount = 3;
        int secondPlayerGameWinsCount = 2;

        winMultipleGames(firstPlayer, firstPLayerGameWinsCount);
        winMultipleGames(secondPlayer, secondPlayerGameWinsCount);

        assertAll(() -> assertEquals(firstPLayerGameWinsCount, gameSet.getGamesWonByPlayer().get(firstPlayer)),
                () -> assertEquals(secondPlayerGameWinsCount, gameSet.getGamesWonByPlayer().get(secondPlayer)));

    }

    @Test
    void gameSetShouldNotBeOverWhenScoreIsFiveFive() {
        winMultipleGames(firstPlayer, 5);
        winMultipleGames(secondPlayer, 5);

        assertFalse(gameSet.isOver());
    }

    @Test
    void shouldNotBeOverWhenScoreIsSixFive() {
        winMultipleGames(firstPlayer, 5);
        winMultipleGames(secondPlayer, 6);

        assertFalse(gameSet.isOver());
    }

    @Test
    void shouldNotBeOverWhenScoreIsZeroFive() {
        winMultipleGames(firstPlayer, 5);

        assertFalse(gameSet.isOver());
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void shouldFirstPlayerWinSetAtSixToX(int secondPlayerGames) {
        winMultipleGames(secondPlayer, secondPlayerGames);
        winMultipleGames(firstPlayer, 6);

        assertTrue(gameSet.isOver());
        assertEquals(firstPlayer, gameSet.getWinner());
    }

    @Test
    void shouldFirstPlayerWinSetAtSevenFive() {
        winMultipleGames(secondPlayer, 5);
        winMultipleGames(firstPlayer, 7);

        assertTrue(gameSet.isOver());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void shouldSecondPlayerWinSetAtSixToX(int firstPlayerGames) {
        winMultipleGames(firstPlayer, firstPlayerGames);
        winMultipleGames(secondPlayer, 6);

        assertAll(() -> assertTrue(gameSet.isOver()), () -> assertEquals(secondPlayer, gameSet.getWinner()));
    }

    @Test
    void tieBreakShouldNotBeOverAtSixSixPoints() {
        reachTieBreak();

        for (int i = 0; i < 6; i++) {
            gameSet.scorePointTo(firstPlayer);
            gameSet.scorePointTo(secondPlayer);
        }

        assertFalse(gameSet.isOver());
    }

    @Test
    void shouldSecondPlayerWinSetAtFiveSeven() {
        winMultipleGames(firstPlayer, 5);
        winMultipleGames(secondPlayer, 7);

        assertAll(() -> assertTrue(gameSet.isOver()), () -> assertEquals(secondPlayer, gameSet.getWinner()));
    }

    @Test
    void shouldCreateTieBreakGameWhenScoreReachesSixSix() {
        reachTieBreak();

        assertTrue(gameSet.isTieBreakGameActive());
    }

    @Test
    void shouldFirstPlayerWinSetAfterWinningTieBreak() {
        reachTieBreak();

        assertTrue(gameSet.isTieBreakGameActive());

        winTieBreakGame(firstPlayer);

        assertAll(() -> assertEquals(7, gameSet.getGamesWonByPlayer().get(firstPlayer)),
                () -> assertEquals(6, gameSet.getGamesWonByPlayer().get(secondPlayer)),
                () -> assertTrue(gameSet.isOver()),
                () -> assertEquals(firstPlayer, gameSet.getWinner()));
    }

    @Test
    void shouldSecondPlayerWinSetAfterWinningTieBreak() {
        reachTieBreak();

        assertTrue(gameSet.isTieBreakGameActive());

        winTieBreakGame(secondPlayer);

        assertAll(() -> assertEquals(7, gameSet.getGamesWonByPlayer().get(secondPlayer)),
                () -> assertEquals(6, gameSet.getGamesWonByPlayer().get(firstPlayer)),
                () -> assertEquals(secondPlayer, gameSet.getWinner()),
                () -> assertTrue(gameSet.isOver()));

    }

    @Test
    void shouldReturnCorrectWinnerAfterFirstPlayerWinsSet() {
        winSet(firstPlayer);

        assertEquals(firstPlayer, gameSet.getWinner());
    }

    @Test
    void shouldReturnCorrectWinnerAfterSecondPlayerWinsSet() {
        winSet(secondPlayer);

        assertAll(() -> assertTrue(gameSet.isOver()), () -> assertEquals(secondPlayer, gameSet.getWinner()));
    }

    @Test
    void shouldReturnNullWinnerBeforeSetIsOver() {
        winRegularGame(firstPlayer);

        assertAll(() -> assertFalse(gameSet.isOver()), () -> assertNull(gameSet.getWinner()));
    }

    @Test
    void shouldReturnFalseIsOverBeforeSetIsOver() {
        winMultipleGames(firstPlayer, 5);

        assertFalse(gameSet.isOver());
    }

    @Test
    void shouldEnterTieBreakAfterFirstPlayerWinsGame() {
        reachTieBreak();

        gameSet.scorePointTo(firstPlayer); // начинается tie-break

        assertTrue(gameSet.isTieBreakGameActive());
    }

    @Test
    void shouldEnterTieBreakAfterSecondPlayerWinsGame() {
        reachTieBreak();

        gameSet.scorePointTo(secondPlayer); // начинается tie-break

        assertTrue(gameSet.isTieBreakGameActive());
    }

    @Test
    void shouldKeepStateUnchangedWhenPointWonAfterSetIsOver() {
        winSet(firstPlayer);

        assertThrows(
                GameSetAlreadyFinishedException.class,
                () -> gameSet.scorePointTo(secondPlayer)
        );

        assertAll(
                () -> assertTrue(gameSet.isOver()),
                () -> assertEquals(firstPlayer, gameSet.getWinner()),
                () -> assertEquals(6, gameSet.getGamesWonByPlayer().get(firstPlayer)),
                () -> assertEquals(0, gameSet.getGamesWonByPlayer().get(secondPlayer))
        );
    }

    @Test
    void shouldThrowExceptionWhenPointWonAfterSetIsOver() {
        winSet(firstPlayer);

        assertThrows(
                GameSetAlreadyFinishedException.class,
                () -> gameSet.scorePointTo(secondPlayer)
        );
    }

    @Test
    void shouldNotAllowWinnerToChangeAfterSetOver() {
        winSet(firstPlayer);

        assertTrue(gameSet.isOver());
        assertEquals(firstPlayer, gameSet.getWinner());

        assertThrows(GameSetAlreadyFinishedException.class, () -> gameSet.scorePointTo(secondPlayer));

        assertEquals(firstPlayer, gameSet.getWinner());
    }

    @Test
    void shouldThrowExceptionForUnknownPlayer() {
        Player stranger = new Player("Stranger");

        assertThrows(InvalidPlayerException.class, () -> gameSet.scorePointTo(stranger));
    }

    @Test
    void tieBreakShouldBeOverAtEightSixPoints(){
        reachTieBreak();

        for (int i = 0; i < 6; i++) {
            gameSet.scorePointTo(firstPlayer);
            gameSet.scorePointTo(secondPlayer);
        }

        gameSet.scorePointTo(firstPlayer); // 7:6

        assertFalse(gameSet.isOver());

        gameSet.scorePointTo(firstPlayer); // 8:6

        assertAll(
                () -> assertTrue(gameSet.isOver()),
                () -> assertEquals(firstPlayer, gameSet.getWinner())
        );
    }

    void winRegularGame(Player player) {
        for (int i = 0; i < 4; i++) {
            gameSet.scorePointTo(player);
        }
    }

    void winSet(Player player) {
        for (int i = 0; i < 6; i++) {
            winRegularGame(player);
        }
    }

    void reachTieBreak() {
        for (int i = 0; i < 6; i++) {
            winRegularGame(firstPlayer);
            winRegularGame(secondPlayer);
        }
    }

    void winMultipleGames(Player player, int gameCount) {
        for (int i = 0; i < gameCount; i++) {
            winRegularGame(player);
        }
    }

    void winTieBreakGame(Player player) {
        for (int i = 0; i < 7; i++) {
            gameSet.scorePointTo(player);
        }
    }

}