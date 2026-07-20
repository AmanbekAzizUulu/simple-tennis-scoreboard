package com.dandaev.edu.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class MatchEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "player1_id",
            nullable = false
    )
    private PlayerEntity player1;

    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "player2_id",
            nullable = false
    )
    private PlayerEntity player2;

    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "winner_id",
            nullable = false
    )
    private PlayerEntity winner;

    @Column(
            name = "played_at",
            nullable = false
    )
    private LocalDateTime playedAt;

    public MatchEntity() {
    }

    public MatchEntity(PlayerEntity player1, PlayerEntity player2, PlayerEntity winner, LocalDateTime playedAt) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.playedAt = playedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayerEntity getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerEntity player1) {
        this.player1 = player1;
    }

    public PlayerEntity getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerEntity player2) {
        this.player2 = player2;
    }

    public PlayerEntity getWinner() {
        return winner;
    }

    public void setWinner(PlayerEntity winner) {
        this.winner = winner;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }
}