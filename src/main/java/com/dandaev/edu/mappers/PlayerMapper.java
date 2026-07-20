package com.dandaev.edu.mappers;

import com.dandaev.edu.entity.PlayerEntity;
import com.dandaev.edu.model.Player;

import java.util.Optional;

public class PlayerMapper {

    public static Optional<PlayerEntity> toEntity(Player player) {
        return Optional.ofNullable(player).map(p -> new PlayerEntity(p.getName()));
    }

    public static Optional<Player> toDomain(PlayerEntity entity) {
        return Optional.ofNullable(entity).map(e -> new Player(e.getName()));
    }
}