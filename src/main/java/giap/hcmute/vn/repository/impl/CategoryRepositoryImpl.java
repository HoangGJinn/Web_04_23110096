package giap.hcmute.vn.repository.impl;

import giap.hcmute.vn.config.JPAConfig;
import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public Optional<CategoryEntity> findById(int categoryld) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT DISTINCT c FROM CategoryEntity c " +
                "LEFT JOIN FETCH c.videos " +
                "WHERE c.categoryld = :categoryld", CategoryEntity.class);
            query.setParameter("categoryld", categoryld);
            CategoryEntity category = query.getSingleResult();
            return Optional.ofNullable(category);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<CategoryEntity> findByName(String categoryname) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT c FROM CategoryEntity c WHERE c.categoryname = :categoryname", CategoryEntity.class);
            query.setParameter("categoryname", categoryname);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CategoryEntity> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT DISTINCT c FROM CategoryEntity c " +
                "LEFT JOIN FETCH c.videos " +
                "ORDER BY c.categoryname", CategoryEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CategoryEntity> search(String keyword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT c FROM CategoryEntity c WHERE c.categoryname LIKE :keyword", CategoryEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void save(CategoryEntity CategoryDTO) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(CategoryDTO);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(CategoryEntity CategoryDTO) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(CategoryDTO);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int categoryld) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            CategoryEntity category = em.find(CategoryEntity.class, categoryld);
            if (category != null) {
                em.remove(category);
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
                "SELECT COUNT(c) FROM CategoryEntity c", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<CategoryEntity> findByUsername(String username) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<CategoryEntity> query = em.createQuery(
                "SELECT DISTINCT c FROM CategoryEntity c " +
                "LEFT JOIN FETCH c.videos " +
                "WHERE c.user.username = :username ORDER BY c.categoryname", CategoryEntity.class);
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
