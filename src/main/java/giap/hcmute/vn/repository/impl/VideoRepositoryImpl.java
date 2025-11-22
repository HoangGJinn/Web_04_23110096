package giap.hcmute.vn.repository.impl;

import giap.hcmute.vn.config.JPAConfig;
import giap.hcmute.vn.model.VideoEntity;
import giap.hcmute.vn.repository.VideoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class VideoRepositoryImpl implements VideoRepository {

    @Override
    public Optional<VideoEntity> findById(int videold) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<VideoEntity> query = em.createQuery(
                "SELECT DISTINCT v FROM VideoEntity v " +
                "LEFT JOIN FETCH v.category " +
                "WHERE v.videold = :videold", VideoEntity.class);
            query.setParameter("videold", videold);
            VideoEntity video = query.getSingleResult();
            return Optional.ofNullable(video);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<VideoEntity> findByTitle(String title) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<VideoEntity> query = em.createQuery(
                "SELECT v FROM VideoEntity v WHERE v.title = :title", VideoEntity.class);
            query.setParameter("title", title);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public List<VideoEntity> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<VideoEntity> query = em.createQuery(
                "SELECT DISTINCT v FROM VideoEntity v " +
                "LEFT JOIN FETCH v.category " +
                "ORDER BY v.videold DESC", VideoEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<VideoEntity> search(String keyword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<VideoEntity> query = em.createQuery(
                "SELECT v FROM VideoEntity v " +
                "WHERE v.title LIKE :keyword OR v.description LIKE :keyword", VideoEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<VideoEntity> findByCategoryld(int categoryld) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<VideoEntity> query = em.createQuery(
                "SELECT v FROM VideoEntity v " +
                "WHERE v.category.categoryld = :categoryld " +
                "ORDER BY v.videold DESC", VideoEntity.class);
            query.setParameter("categoryld", categoryld);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<VideoEntity> findActiveVideos() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<VideoEntity> query = em.createQuery(
                "SELECT v FROM VideoEntity v " +
                "WHERE v.active = true " +
                "ORDER BY v.videold DESC", VideoEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void save(VideoEntity video) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(video);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(VideoEntity video) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(video);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int videold) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            VideoEntity video = em.find(VideoEntity.class, videold);
            if (video != null) {
                em.remove(video);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM VideoEntity v", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    @Override
    public void incrementViews(int videold) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            VideoEntity video = em.find(VideoEntity.class, videold);
            if (video != null) {
                int currentViews = video.getViews() != null ? video.getViews() : 0;
                video.setViews(currentViews + 1);
                em.merge(video);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}


