package com.dandaev.edu.entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RegularGameTest {
    private Player firstPlayer;
    private Player secondPlayer;

    private RegularGame game;

    @BeforeEach
    void initiatePlayers() {
        firstPlayer = new Player("Player I");
        secondPlayer = new Player("Player II");

        game = new RegularGame(firstPlayer, secondPlayer);
    }

    // тесты связанные с первым игроком
    @Test
    void shouldScoreFifteenToFirstPlayer() {
        game.scorePointTo(firstPlayer);

        assertEquals(GamePoint.FIFTEEN, game.getTable().get(firstPlayer));
    }

    @Test
    void shouldScoreThirtyToFirstPlayer() {
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30

        assertEquals(GamePoint.THIRTY, game.getTable().get(firstPlayer));
    }

    @Test
    void shouldScoreFortyToFirstPlayer() {
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40

        assertEquals(GamePoint.FORTY, game.getTable().get(firstPlayer));
    }

    // тесты связанные со вторым игроком
    @Test
    void shouldScoreFifteenToSecondPlayer() {
        game.scorePointTo(secondPlayer); // 15

        assertEquals(GamePoint.FIFTEEN, game.getTable().get(secondPlayer));
    }

    @Test
    void shouldScoreThirtyToSecondPlayer() {
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30

        assertEquals(GamePoint.THIRTY, game.getTable().get(secondPlayer));
    }

    @Test
    void shouldScoreFortyToSecondPlayer() {
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40

        assertEquals(GamePoint.FORTY, game.getTable().get(secondPlayer));
    }

    // тесты с первым игроком связанные с логикой advantage
    @Test
    void shouldGiveAdvantageToFirstPlayerAfterDeuce() {
        // доводим первого игрока до 40 очков
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40

        // доводим второго игрока до 40 очков
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40

        // -- сейчас ничья

        // первый игрок получает преимущество
        game.scorePointTo(firstPlayer); // GamePoint.ADVANTAGE

        assertEquals(GamePoint.ADVANTAGE, game.getTable().get(firstPlayer));
    }

    // тесты со вторым игроком связанные с логикой advantage
    @Test
    void shouldGiveAdvantageToSecondPlayerAfterDeuce() {
        // доводим первого игрока до 40 очков
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40

        // доводим второго игрока до 40 очков
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40

        // -- сейчас ничья

        // второй игрок получает преимущество
        game.scorePointTo(secondPlayer); // GamePoint.ADVANTAGE

        assertEquals(GamePoint.ADVANTAGE, game.getTable().get(secondPlayer));
    }

    // тесты связанные с механикой ситуации которая обратно приводит к ничейной ситуации
    @Test
    void shouldReturnToDeuceWhenSecondPlayerScoresAgainstFirstPlayerAdvantage() {
        giveFirstPlayerAdvantage();

        game.scorePointTo(secondPlayer); // 40 - 40

        GamePoint expectedScore[] = {GamePoint.FORTY, GamePoint.FORTY};
        GamePoint actualScore[] = new GamePoint[2];

        actualScore[0] = game.getTable().get(firstPlayer);
        actualScore[1] = game.getTable().get(secondPlayer);

        assertArrayEquals(expectedScore, actualScore);
    }

    @Test
    void shouldReturnToDeuceWhenFirstPlayerScoresAgainstSecondPlayerAdvantage() {
        giveSecondPlayerAdvantage();

        game.scorePointTo(firstPlayer); // 40 - 40

        GamePoint expectedScore[] = {GamePoint.FORTY, GamePoint.FORTY};
        GamePoint actualScore[] = new GamePoint[2];

        actualScore[0] = game.getTable().get(firstPlayer);
        actualScore[1] = game.getTable().get(secondPlayer);

        assertArrayEquals(expectedScore, actualScore);
    }

    @Test
    void firstPlayerShouldWinGameAtFortyLove() {
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40
        game.scorePointTo(firstPlayer); // win

        assertTrue(game.isOver());
        assertEquals(firstPlayer, game.getWinner());
    }

    @Test
    void firstPlayerShouldWinGameAtFortyFifteen() {
        // первый игрок зарабатывает очки
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40

        // второй игрок зарабатывает очко
        game.scorePointTo(secondPlayer); // 15

        // -- счет становится 15-40

        // первый игрок побеждает
        game.scorePointTo(firstPlayer); // win

        assertTrue(game.isOver());
        assertEquals(firstPlayer, game.getWinner());
    }

    @Test
    void firstPlayerShouldWinGameAtFortyThirty() {
        // первый игрок зарабатывает очки
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40

        // второй игрок зарабатывает очки
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30

        // -- счет становится 30-40

        // первый игрок побеждает
        game.scorePointTo(firstPlayer); // win

        assertTrue(game.isOver());
        assertEquals(firstPlayer, game.getWinner());
    }

    @Test
    void secondPlayerShouldWinGameAtFortyLove() {
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40
        game.scorePointTo(secondPlayer); // win

        assertTrue(game.isOver());
        assertEquals(secondPlayer, game.getWinner());
    }

    @Test
    void secondPlayerShouldWinGameAtFortyFifteen() {
        // второй игрок зарабатывает очки
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40

        // первый игрок зарабатывает очко
        game.scorePointTo(firstPlayer); // 15

        // -- счет становится 15-40

        // второй игрок побеждает
        game.scorePointTo(secondPlayer); // win

        assertTrue(game.isOver());
        assertEquals(secondPlayer, game.getWinner());
    }

    @Test
    void secondPlayerShouldWinGameAtFortyThirty() {
        // второй игрок зарабатывает очки
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40

        // первый игрок зарабатывает очки
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30

        // -- счет становится 30-40

        // второй игрок побеждает
        game.scorePointTo(secondPlayer); // win

        assertTrue(game.isOver());
        assertEquals(secondPlayer, game.getWinner());
    }

    @Test
    void firstPlayerShouldWinGameWhenScoresAfterAdvantage() {
        giveFirstPlayerAdvantage();

        game.scorePointTo(firstPlayer);

        assertTrue(game.isOver());
        assertEquals(firstPlayer, game.getWinner());
    }

    @Test
    void secondPlayerShouldWinGameWhenScoresAfterAdvantage() {
        giveSecondPlayerAdvantage();

        game.scorePointTo(secondPlayer);

        assertTrue(game.isOver());
        assertEquals(secondPlayer, game.getWinner());
    }

    @Test
    void shouldMarkGameAsOverAfterFirstPlayerWins() {
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40
        game.scorePointTo(firstPlayer); // win

        assertTrue(game.isOver());
    }

    @Test
    void shouldMarkGameAsOverAfterSecondPlayerWins() {
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40
        game.scorePointTo(secondPlayer); // win

        assertTrue(game.isOver());
    }

    @Test
    void shouldThrowExceptionWhenScoringAfterFirstPlayerWinsAndGameIsOver() {
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40
        game.scorePointTo(firstPlayer); // win

        assertThrows(IllegalStateException.class, () -> game.scorePointTo(secondPlayer));
    }

    @Test
    void shouldThrowExceptionWhenScoringAfterSecondPlayerWinsAndGameIsOver() {
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40
        game.scorePointTo(secondPlayer); // win

        assertThrows(IllegalStateException.class, () -> game.scorePointTo(secondPlayer));
    }

    @Test
    void shouldThrowExceptionForUnknownPlayer() {
        Player stranger = new Player("Stranger");

        assertThrows(IllegalArgumentException.class, () -> game.scorePointTo(stranger));
    }

    @Test
    void shouldStartWithLoveLoveScore() {
        GamePoint firstPlayerInitialGamePoint = game.getTable().get(firstPlayer);
        GamePoint secondPlayerInitialGamePoint = game.getTable().get(secondPlayer);

        assertEquals(GamePoint.LOVE, firstPlayerInitialGamePoint);
        assertEquals(GamePoint.LOVE, secondPlayerInitialGamePoint);
    }

    @Test
    void shouldNotHaveWinnerWhenGameStarts() {
        assertNull(game.getWinner());
    }

    @Test
    void shouldNotBeOverWhenGameStarts() {
        assertFalse(game.isOver());
    }


    @Test
    void shouldReturnCopyOfScoreTable() {
        Map<Player, GamePoint> table = game.getTable(); // получаем копию таблицы

        table.put(firstPlayer, GamePoint.ADVANTAGE);    // в копию таблицы проставляем рандомное состояние очка

        assertEquals(GamePoint.LOVE, game.getTable().get(firstPlayer)); // проверяем что таблицы в игре не изменилась
    }

    void giveFirstPlayerAdvantage() {
        // доводим первого игрока до 40 очков
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40

        // доводим второго игрока до 40 очков
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40

        // -- сейчас ничья

        // первый игрок получает преимущество
        game.scorePointTo(firstPlayer); // GamePoint.ADVANTAGE
    }

    void giveSecondPlayerAdvantage() {
        // доводим первого игрока до 40 очков
        game.scorePointTo(firstPlayer); // 15
        game.scorePointTo(firstPlayer); // 30
        game.scorePointTo(firstPlayer); // 40

        // доводим второго игрока до 40 очков
        game.scorePointTo(secondPlayer); // 15
        game.scorePointTo(secondPlayer); // 30
        game.scorePointTo(secondPlayer); // 40

        // -- сейчас ничья

        // второй игрок получает преимущество
        game.scorePointTo(secondPlayer); // GamePoint.ADVANTAGE
    }
}
