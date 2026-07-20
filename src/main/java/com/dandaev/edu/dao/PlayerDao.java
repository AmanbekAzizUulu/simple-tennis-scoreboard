package com.dandaev.edu.dao;

import com.dandaev.edu.entity.PlayerEntity;
import com.dandaev.edu.exceptions.dao.DataAccessException;
import com.dandaev.edu.exceptions.domain.InvalidPlayerException;
import com.dandaev.edu.mappers.PlayerMapper;
import com.dandaev.edu.model.Player;
import com.dandaev.edu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PlayerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDao.class);

    private static final String FIND_BY_NAME =
                    """
                        FROM PlayerEntity WHERE name = :name
                    """;

    public Optional<PlayerEntity> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<PlayerEntity> query = session.createQuery(FIND_BY_NAME, PlayerEntity.class);
            query.setParameter("name", name);
            Optional<PlayerEntity> player = query.uniqueResultOptional();
            if (player.isPresent()) {
                LOGGER.debug("Player found: {}", name);
            } else {
                LOGGER.debug("Player not found: {}", name);
            }
            return player;
        } catch (Exception e) {
            LOGGER.error("Error finding player by name '{}': {}", name, e.getMessage(), e);
            throw new DataAccessException("Failed to find player", e);
        }
    }

    public PlayerEntity save(PlayerEntity player) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(player);
            tx.commit();
            LOGGER.info("Player saved: {} (id={})", player.getName(), player.getId());
            return player;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.error("Failed to save player '{}': {}", player.getName(), e.getMessage(), e);
            throw new DataAccessException("Failed to save player", e);
        }
    }

    public PlayerEntity findOrCreate(Player player) {
        String name = player.getName();
        return findByName(name)
                .orElseGet(() -> {
                    LOGGER.info("Creating new player: {}", name);
                    return PlayerMapper.toEntity(player).map(this::save).orElseThrow(() -> {
                        LOGGER.error("Cannot map player to entity for {}", name);
                        return new InvalidPlayerException("Cannot map a null player");
                    });
                });
    }

    public PlayerEntity findOrCreate(String name) {
        return findByName(name)
                .orElseGet(() -> {
                    LOGGER.info("Creating new player by name: {}", name);
                    return save(new PlayerEntity(name));
                });
    }
}