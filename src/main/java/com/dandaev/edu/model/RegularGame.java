package com.dandaev.edu.model;

import com.dandaev.edu.exceptions.domain.GameSetAlreadyFinishedException;
import com.dandaev.edu.exceptions.domain.InvalidPlayerException;

import java.util.HashMap;
import java.util.Map;

public class RegularGame implements Game {
    private final Player firstPlayer;
    private final Player secondPlayer;

    private Player winner;

    private boolean isOver;

    private final Map<Player, GamePoint> pointsWinByPlayer;

    public RegularGame(Player firstPlayer, Player secondPlayer) {
        if (firstPlayer == null || secondPlayer == null) {
            throw new InvalidPlayerException("Player cannot be null");
        }
        if (firstPlayer.equals(secondPlayer)) {
            throw new InvalidPlayerException("Players must be different");
        }

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.winner = null;
        this.isOver = false;

        this.pointsWinByPlayer = new HashMap<>();

        pointsWinByPlayer.put(firstPlayer, GamePoint.LOVE);
        pointsWinByPlayer.put(secondPlayer, GamePoint.LOVE);
    }

    @Override
    public void scorePointTo(Player player) {
        if (isOver) {
            throw new GameSetAlreadyFinishedException("Game`s already over");
        }

        // бросаем исключение если по какой-либо причине в аргументы попал посторонний объект
        if (!player.equals(firstPlayer) && !player.equals(secondPlayer)) {
            throw new InvalidPlayerException("Unknown player");
        }

        // обработка ничьи
        if (pointsWinByPlayer.get(firstPlayer) == GamePoint.FORTY && pointsWinByPlayer.get(secondPlayer) == GamePoint.FORTY) {
            if (player.equals(firstPlayer)) {
                pointsWinByPlayer.put(firstPlayer, GamePoint.ADVANTAGE);
                return;
            } else if (player.equals(secondPlayer)) {
                pointsWinByPlayer.put(secondPlayer, GamePoint.ADVANTAGE);
                return;
            }
        }

        // обработка победы или возврата в ничейную ситуацию
        if (pointsWinByPlayer.get(firstPlayer) == GamePoint.ADVANTAGE && pointsWinByPlayer.get(secondPlayer) == GamePoint.FORTY) {
            if (player.equals(firstPlayer)) {
                winner = firstPlayer;
                isOver = true;
                return;
            } else if (player.equals(secondPlayer)) {
                // проставляется ничья
                pointsWinByPlayer.put(firstPlayer, GamePoint.FORTY);
                pointsWinByPlayer.put(secondPlayer, GamePoint.FORTY);
                return;
            }
        }
        if (pointsWinByPlayer.get(firstPlayer) == GamePoint.FORTY && pointsWinByPlayer.get(secondPlayer) == GamePoint.ADVANTAGE) {
            if (player.equals(secondPlayer)) {
                winner = secondPlayer;
                isOver = true;
                return;
            } else if (player.equals(firstPlayer)) {
                // проставляется ничья
                pointsWinByPlayer.put(firstPlayer, GamePoint.FORTY);
                pointsWinByPlayer.put(secondPlayer, GamePoint.FORTY);
                return;
            }
        }

        // обработка выигрыша с отрывом
        if ((pointsWinByPlayer.get(firstPlayer) == GamePoint.FORTY && pointsWinByPlayer.get(secondPlayer) == GamePoint.LOVE) || (pointsWinByPlayer.get(firstPlayer) == GamePoint.FORTY && pointsWinByPlayer.get(secondPlayer) == GamePoint.FIFTEEN) || (pointsWinByPlayer.get(firstPlayer) == GamePoint.FORTY && pointsWinByPlayer.get(secondPlayer) == GamePoint.THIRTY)) {
            if (player.equals(firstPlayer)) {
                winner = firstPlayer;
                isOver = true;
                return;
            }
        }
        if ((pointsWinByPlayer.get(secondPlayer) == GamePoint.FORTY && pointsWinByPlayer.get(firstPlayer) == GamePoint.LOVE) || (pointsWinByPlayer.get(secondPlayer) == GamePoint.FORTY && pointsWinByPlayer.get(firstPlayer) == GamePoint.FIFTEEN) || (pointsWinByPlayer.get(secondPlayer) == GamePoint.FORTY && pointsWinByPlayer.get(firstPlayer) == GamePoint.THIRTY)) {
            if (player.equals(secondPlayer)) {
                winner = secondPlayer;
                isOver = true;
                return;
            }
        }

        // обработка получения очка
        if (pointsWinByPlayer.get(player) == GamePoint.LOVE) {
            pointsWinByPlayer.put(player, GamePoint.FIFTEEN);
            return;
        }
        if (pointsWinByPlayer.get(player) == GamePoint.FIFTEEN) {
            pointsWinByPlayer.put(player, GamePoint.THIRTY);
            return;
        }
        if (pointsWinByPlayer.get(player) == GamePoint.THIRTY) {
            pointsWinByPlayer.put(player, GamePoint.FORTY);
            return;
        }
    }

    @Override
    public boolean isOver() {
        return isOver;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    public Map<Player, GamePoint> getPointsWinByPlayer() {
        return new HashMap<>(pointsWinByPlayer);
    }

    GamePoint[] getScore() {
        GamePoint gamePoints[] = new GamePoint[2];

        gamePoints[0] = pointsWinByPlayer.get(firstPlayer);
        gamePoints[1] = pointsWinByPlayer.get(secondPlayer);

        return gamePoints;
    }
}
