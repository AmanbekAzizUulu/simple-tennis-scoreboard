package com.dandaev.edu.dao;

import com.dandaev.edu.entity.MatchEntity;
import com.dandaev.edu.exceptions.dao.DataAccessException;
import com.dandaev.edu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MatchDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDao.class);

    private static final String FIND_BY_PLAYER_NAME =
            """
                SELECT m FROM MatchEntity m
                JOIN FETCH m.player1
                JOIN FETCH m.player2
                JOIN FETCH m.winner
                WHERE m.player1.name LIKE :name OR m.player2.name LIKE :name
                ORDER BY m.playedAt DESC
            """;

    private static final String COUNT_BY_PLAYER_NAME =
            """
                SELECT COUNT(m) FROM MatchEntity m
                WHERE m.player1.name LIKE :name OR m.player2.name LIKE :name
            """;

    private static final String FIND_ALL =
            """
                SELECT m FROM MatchEntity m
                JOIN FETCH m.player1
                JOIN FETCH m.player2
                JOIN FETCH m.winner
                ORDER BY m.playedAt DESC
            """;

    private static final String COUNT_ALL =
            """
                SELECT COUNT(m) FROM MatchEntity m
            """;

    public void save(MatchEntity match) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(match);
            tx.commit();
            LOGGER.info("Match saved with id: {}", match.getId());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.error("Failed to save match: {}", e.getMessage(), e);
            throw new DataAccessException("Failed to save match", e);
        }
    }

    public List<MatchEntity> findByPlayerName(String playerName, int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MatchEntity> query = session.createQuery(FIND_BY_PLAYER_NAME, MatchEntity.class);
            query.setParameter("name", "%" + playerName + "%");
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            List<MatchEntity> result = query.getResultList();
            LOGGER.debug("Found {} matches for player name filter '{}' (offset={}, limit={})",
                    result.size(), playerName, offset, limit);
            return result;
        } catch (Exception e) {
            LOGGER.error("Error finding matches by player name '{}': {}", playerName, e.getMessage(), e);
            throw new DataAccessException("Failed to find matches by player name", e);
        }
    }

    public long countByPlayerName(String playerName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(COUNT_BY_PLAYER_NAME, Long.class);
            query.setParameter("name", "%" + playerName + "%");
            long count = query.uniqueResult();
            LOGGER.debug("Total matches for player name '{}': {}", playerName, count);
            return count;
        } catch (Exception e) {
            LOGGER.error("Error counting matches by player name '{}': {}", playerName, e.getMessage(), e);
            throw new DataAccessException("Failed to count matches by player name", e);
        }
    }

    public List<MatchEntity> findAll(int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MatchEntity> query = session.createQuery(FIND_ALL, MatchEntity.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            List<MatchEntity> result = query.getResultList();
            LOGGER.debug("Retrieved {} matches (offset={}, limit={})", result.size(), offset, limit);
            return result;
        } catch (Exception e) {
            LOGGER.error("Error fetching all matches: {}", e.getMessage(), e);
            throw new DataAccessException("Failed to fetch matches", e);
        }
    }

    public long countAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long count = session.createQuery(COUNT_ALL, Long.class).uniqueResult();
            LOGGER.debug("Total matches in database: {}", count);
            return count;
        } catch (Exception e) {
            LOGGER.error("Error counting all matches: {}", e.getMessage(), e);
            throw new DataAccessException("Failed to count matches", e);
        }
    }
}