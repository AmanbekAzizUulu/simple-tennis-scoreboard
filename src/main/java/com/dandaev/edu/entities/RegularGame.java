package com.dandaev.edu.entities;

import java.util.HashMap;
import java.util.Map;

public class RegularGame implements Game {
    private final Player firstPlayer;
    private final Player secondPlayer;

    private Player winner;

    private boolean isOver;

    private final Map<Player, GamePoint> table;

    public RegularGame(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.winner = null;
        this.isOver = false;

        this.table = new HashMap<>();

        table.put(firstPlayer, GamePoint.LOVE);
        table.put(secondPlayer, GamePoint.LOVE);
    }

    public void scorePointTo(Player player) {
        if (isOver) {
            throw new IllegalStateException("Game`s already over");
        }

        // тут наверное не надо бросать исключение — такой ситуации вообще не должно происходить по идее
        if (!player.equals(firstPlayer) && !player.equals(secondPlayer)) {
            throw new IllegalArgumentException("Unknown player");
        }

        // обработка ничьи
        if (table.get(firstPlayer) == GamePoint.FORTY && table.get(secondPlayer) == GamePoint.FORTY) {
            if (player.equals(firstPlayer)) {
                table.put(firstPlayer, GamePoint.ADVANTAGE);
                return;
            } else if (player.equals(secondPlayer)) {
                table.put(secondPlayer, GamePoint.ADVANTAGE);
                return;
            }
        }

        // обработка победы или возврата в ничейную ситуацию
        if (table.get(firstPlayer) == GamePoint.ADVANTAGE && table.get(secondPlayer) == GamePoint.FORTY) {
            if (player.equals(firstPlayer)) {
                System.out.println("First Player Wins");

                winner = firstPlayer;
                isOver = true;
                return;
            } else if (player.equals(secondPlayer)) {
                // проставляется ничья
                table.put(firstPlayer, GamePoint.FORTY);
                table.put(secondPlayer, GamePoint.FORTY);
                return;
            }
        }
        if (table.get(firstPlayer) == GamePoint.FORTY && table.get(secondPlayer) == GamePoint.ADVANTAGE) {
            if (player.equals(secondPlayer)) {
                System.out.println("Second Player Wins");

                winner = secondPlayer;
                isOver = true;
                return;
            } else if (player.equals(firstPlayer)) {
                // проставляется ничья
                table.put(firstPlayer, GamePoint.FORTY);
                table.put(secondPlayer, GamePoint.FORTY);
                return;
            }
        }

        // обработка выигрыша с отрывом
        if ((table.get(firstPlayer) == GamePoint.FORTY && table.get(secondPlayer) == GamePoint.LOVE) || (table.get(firstPlayer) == GamePoint.FORTY && table.get(secondPlayer) == GamePoint.FIFTEEN) || (table.get(firstPlayer) == GamePoint.FORTY && table.get(secondPlayer) == GamePoint.THIRTY)) {
            if (player.equals(firstPlayer)) {
                System.out.println("First Player Wins");

                winner = firstPlayer;
                isOver = true;
                return;
            }
        }
        if ((table.get(secondPlayer) == GamePoint.FORTY && table.get(firstPlayer) == GamePoint.LOVE) || (table.get(secondPlayer) == GamePoint.FORTY && table.get(firstPlayer) == GamePoint.FIFTEEN) || (table.get(secondPlayer) == GamePoint.FORTY && table.get(firstPlayer) == GamePoint.THIRTY)) {
            if (player.equals(secondPlayer)) {
                System.out.println("Second Player Wins");

                winner = secondPlayer;
                isOver = true;
                return;
            }
        }

        // обработка получения очка
        if (table.get(player) == GamePoint.LOVE) {
            table.put(player, GamePoint.FIFTEEN);
            return;
        }
        if (table.get(player) == GamePoint.FIFTEEN) {
            table.put(player, GamePoint.THIRTY);
            return;
        }
        if (table.get(player) == GamePoint.THIRTY) {
            table.put(player, GamePoint.FORTY);
            return;
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public Player getWinner() {
        return winner;
    }

    public Map<Player, GamePoint> getTable() {
        return new HashMap<>(table);
    }

    public GamePoint [] getScore(){
        GamePoint gamePoints[] = new GamePoint[2];

        gamePoints[0] = table.get(firstPlayer);
        gamePoints[1] = table.get(secondPlayer);

        return gamePoints;
    }
}
