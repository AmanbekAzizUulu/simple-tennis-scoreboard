package com.dandaev.edu.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "players")
public class PlayerEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    @Column(
            nullable = false, unique = true, length = 100
    )
    private String name;

    public PlayerEntity() {
    }

    public PlayerEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "player1")
    private Collection<MatchEntity> matchEntity;

    public Collection<MatchEntity> getMatchEntity() {
        return matchEntity;
    }

    public void setMatchEntity(Collection<MatchEntity> matchEntity) {
        this.matchEntity = matchEntity;
    }
}