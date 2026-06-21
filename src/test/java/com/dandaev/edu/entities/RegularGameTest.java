package com.dandaev.edu.entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegularGameTest {
    private Player firstPlayer;
    private Player secondPlayer;

    private RegularGame regularGame;

    @BeforeEach
    void initiatePlayers() {
        firstPlayer = new Player("Player I");
        secondPlayer = new Player("Player II");

        regularGame = new RegularGame(firstPlayer, secondPlayer);
    }

    // тесты связанные с первым игроком
    @Test
    void shouldScoreFifteenToFirstPlayer() {
        regularGame.scorePointTo(firstPlayer);

        assertEquals(GamePoint.FIFTEEN, regularGame.getPointsWinByPlayer().get(firstPlayer));
    }

    @Test
    void shouldScoreThirtyToFirstPlayer() {
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30

        assertEquals(GamePoint.THIRTY, regularGame.getPointsWinByPlayer().get(firstPlayer));
    }

    @Test
    void shouldScoreFortyToFirstPlayer() {
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40

        assertEquals(GamePoint.FORTY, regularGame.getPointsWinByPlayer().get(firstPlayer));
    }

    // тесты связанные со вторым игроком
    @Test
    void shouldScoreFifteenToSecondPlayer() {
        regularGame.scorePointTo(secondPlayer); // 15

        assertEquals(GamePoint.FIFTEEN, regularGame.getPointsWinByPlayer().get(secondPlayer));
    }

    @Test
    void shouldScoreThirtyToSecondPlayer() {
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30

        assertEquals(GamePoint.THIRTY, regularGame.getPointsWinByPlayer().get(secondPlayer));
    }

    @Test
    void shouldScoreFortyToSecondPlayer() {
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40

        assertEquals(GamePoint.FORTY, regularGame.getPointsWinByPlayer().get(secondPlayer));
    }

    // тесты с первым игроком связанные с логикой advantage
    @Test
    void shouldGiveAdvantageToFirstPlayerAfterDeuce() {
        // доводим первого игрока до 40 очков
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40

        // доводим второго игрока до 40 очков
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40

        // -- сейчас ничья

        // первый игрок получает преимущество
        regularGame.scorePointTo(firstPlayer); // GamePoint.ADVANTAGE

        assertEquals(GamePoint.ADVANTAGE, regularGame.getPointsWinByPlayer().get(firstPlayer));
    }

    // тесты со вторым игроком связанные с логикой advantage
    @Test
    void shouldGiveAdvantageToSecondPlayerAfterDeuce() {
        // доводим первого игрока до 40 очков
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40

        // доводим второго игрока до 40 очков
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40

        // -- сейчас ничья

        // второй игрок получает преимущество
        regularGame.scorePointTo(secondPlayer); // GamePoint.ADVANTAGE

        assertEquals(GamePoint.ADVANTAGE, regularGame.getPointsWinByPlayer().get(secondPlayer));
    }

    // тесты связанные с механикой ситуации которая обратно приводит к ничейной ситуации
    @Test
    void shouldReturnToDeuceWhenSecondPlayerScoresAgainstFirstPlayerAdvantage() {
        giveFirstPlayerAdvantage();

        regularGame.scorePointTo(secondPlayer); // 40 - 40

        GamePoint expectedScore[] = {GamePoint.FORTY, GamePoint.FORTY};
        GamePoint actualScore[] = new GamePoint[2];

        actualScore[0] = regularGame.getPointsWinByPlayer().get(firstPlayer);
        actualScore[1] = regularGame.getPointsWinByPlayer().get(secondPlayer);

        assertArrayEquals(expectedScore, actualScore);
    }

    @Test
    void shouldReturnToDeuceWhenFirstPlayerScoresAgainstSecondPlayerAdvantage() {
        giveSecondPlayerAdvantage();

        regularGame.scorePointTo(firstPlayer); // 40 - 40

        GamePoint expectedScore[] = {GamePoint.FORTY, GamePoint.FORTY};
        GamePoint actualScore[] = new GamePoint[2];

        actualScore[0] = regularGame.getPointsWinByPlayer().get(firstPlayer);
        actualScore[1] = regularGame.getPointsWinByPlayer().get(secondPlayer);

        assertArrayEquals(expectedScore, actualScore);
    }

    @Test
    void firstPlayerShouldWinGameAtFortyLove() {
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40
        regularGame.scorePointTo(firstPlayer); // win

        assertTrue(regularGame.isOver());
        assertEquals(firstPlayer, regularGame.getWinner());
    }

    @Test
    void firstPlayerShouldWinGameAtFortyFifteen() {
        // первый игрок зарабатывает очки
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40

        // второй игрок зарабатывает очко
        regularGame.scorePointTo(secondPlayer); // 15

        // -- счет становится 15-40

        // первый игрок побеждает
        regularGame.scorePointTo(firstPlayer); // win

        assertTrue(regularGame.isOver());
        assertEquals(firstPlayer, regularGame.getWinner());
    }

    @Test
    void firstPlayerShouldWinGameAtFortyThirty() {
        // первый игрок зарабатывает очки
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40

        // второй игрок зарабатывает очки
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30

        // -- счет становится 30-40

        // первый игрок побеждает
        regularGame.scorePointTo(firstPlayer); // win

        assertTrue(regularGame.isOver());
        assertEquals(firstPlayer, regularGame.getWinner());
    }

    @Test
    void secondPlayerShouldWinGameAtFortyLove() {
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40
        regularGame.scorePointTo(secondPlayer); // win

        assertTrue(regularGame.isOver());
        assertEquals(secondPlayer, regularGame.getWinner());
    }

    @Test
    void secondPlayerShouldWinGameAtFortyFifteen() {
        // второй игрок зарабатывает очки
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40

        // первый игрок зарабатывает очко
        regularGame.scorePointTo(firstPlayer); // 15

        // -- счет становится 15-40

        // второй игрок побеждает
        regularGame.scorePointTo(secondPlayer); // win

        assertTrue(regularGame.isOver());
        assertEquals(secondPlayer, regularGame.getWinner());
    }

    @Test
    void secondPlayerShouldWinGameAtFortyThirty() {
        // второй игрок зарабатывает очки
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40

        // первый игрок зарабатывает очки
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30

        // -- счет становится 30-40

        // второй игрок побеждает
        regularGame.scorePointTo(secondPlayer); // win

        assertTrue(regularGame.isOver());
        assertEquals(secondPlayer, regularGame.getWinner());
    }

    @Test
    void firstPlayerShouldWinGameWhenScoresAfterAdvantage() {
        giveFirstPlayerAdvantage();

        regularGame.scorePointTo(firstPlayer);

        assertTrue(regularGame.isOver());
        assertEquals(firstPlayer, regularGame.getWinner());
    }

    @Test
    void secondPlayerShouldWinGameWhenScoresAfterAdvantage() {
        giveSecondPlayerAdvantage();

        regularGame.scorePointTo(secondPlayer);

        assertTrue(regularGame.isOver());
        assertEquals(secondPlayer, regularGame.getWinner());
    }

    @Test
    void shouldMarkGameAsOverAfterFirstPlayerWins() {
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40
        regularGame.scorePointTo(firstPlayer); // win

        assertTrue(regularGame.isOver());
    }

    @Test
    void shouldMarkGameAsOverAfterSecondPlayerWins() {
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40
        regularGame.scorePointTo(secondPlayer); // win

        assertTrue(regularGame.isOver());
    }

    @Test
    void shouldThrowExceptionWhenScoringAfterFirstPlayerWinsAndGameIsOver() {
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40
        regularGame.scorePointTo(firstPlayer); // win

        assertThrows(IllegalStateException.class, () -> regularGame.scorePointTo(secondPlayer));
    }

    @Test
    void shouldThrowExceptionWhenScoringAfterSecondPlayerWinsAndGameIsOver() {
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40
        regularGame.scorePointTo(secondPlayer); // win

        assertThrows(IllegalStateException.class, () -> regularGame.scorePointTo(secondPlayer));
    }

    @Test
    void shouldThrowExceptionForUnknownPlayer() {
        Player stranger = new Player("Stranger");

        assertThrows(IllegalArgumentException.class, () -> regularGame.scorePointTo(stranger));
    }

    @Test
    void shouldStartWithLoveLoveScore() {
        GamePoint firstPlayerInitialGamePoint = regularGame.getPointsWinByPlayer().get(firstPlayer);
        GamePoint secondPlayerInitialGamePoint = regularGame.getPointsWinByPlayer().get(secondPlayer);

        assertEquals(GamePoint.LOVE, firstPlayerInitialGamePoint);
        assertEquals(GamePoint.LOVE, secondPlayerInitialGamePoint);
    }

    @Test
    void shouldNotHaveWinnerWhenGameStarts() {
        assertNull(regularGame.getWinner());
    }

    @Test
    void shouldNotBeOverWhenGameStarts() {
        assertFalse(regularGame.isOver());
    }

    @Test
    void shouldNotAllowWinnerToChangeAfterRegularGameOver() {
        RegularGame game = new RegularGame(firstPlayer, secondPlayer);

        for (int i = 0; i < 4; i++) {
            game.scorePointTo(firstPlayer);
        }

        assertTrue(game.isOver());
        assertEquals(firstPlayer, game.getWinner());

        assertThrows(
                IllegalStateException.class,
                () -> game.scorePointTo(secondPlayer)
        );

        assertEquals(firstPlayer, game.getWinner());
    }

    @Test
    void shouldReturnCopyOfScoreTable() {
        Map<Player, GamePoint> table = regularGame.getPointsWinByPlayer(); // получаем копию таблицы

        table.put(firstPlayer, GamePoint.ADVANTAGE);    // в копию таблицы проставляем рандомное состояние очка

        assertEquals(GamePoint.LOVE, regularGame.getPointsWinByPlayer().get(firstPlayer)); // проверяем что таблицы в игре не изменилась
    }

    void giveFirstPlayerAdvantage() {
        // доводим первого игрока до 40 очков
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40

        // доводим второго игрока до 40 очков
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40

        // -- сейчас ничья

        // первый игрок получает преимущество
        regularGame.scorePointTo(firstPlayer); // GamePoint.ADVANTAGE
    }

    void giveSecondPlayerAdvantage() {
        // доводим первого игрока до 40 очков
        regularGame.scorePointTo(firstPlayer); // 15
        regularGame.scorePointTo(firstPlayer); // 30
        regularGame.scorePointTo(firstPlayer); // 40

        // доводим второго игрока до 40 очков
        regularGame.scorePointTo(secondPlayer); // 15
        regularGame.scorePointTo(secondPlayer); // 30
        regularGame.scorePointTo(secondPlayer); // 40

        // -- сейчас ничья

        // второй игрок получает преимущество
        regularGame.scorePointTo(secondPlayer); // GamePoint.ADVANTAGE
    }
}
