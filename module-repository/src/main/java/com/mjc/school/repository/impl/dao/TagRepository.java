package com.mjc.school.repository.impl.dao;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.util.HibernateUtil;
import com.mjc.school.repository.impl.model.TagModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {

    @Override
    public List<TagModel> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from TagModel order by id asc", TagModel.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<TagModel> readAll(Integer pageNumber, Integer pageSize, String sortBy) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "from TagModel";
            if (sortBy != null) {
                hql += " order by " + sortBy;
            }
            Query query = session.createQuery(hql);
            if (pageNumber != null) {
                int firstResult = (pageNumber - 1) * pageSize;
                query.setFirstResult(firstResult);
                query.setMaxResults(pageSize);
            }
            List<TagModel> result = query.getResultList();
            tx.commit();
            session.close();
            return result;
        } catch (HibernateException exception) {
            System.out.println(exception.getMessage());
        }
        throw new RuntimeException("Something wrong with News readAll with paging");
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(TagModel.class, id));
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public TagModel create(TagModel entity) {
        Transaction tx = null;
        Long generatedId = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            generatedId = (Long) session.save(entity);
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return readById(generatedId).get();
    }

    @Override
    public TagModel update(TagModel entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(entity);
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return readById(entity.getId()).get();
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            TagModel model = readById(id).get();
            session.delete(model);
            session.flush();
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<TagModel> models = session.createQuery("from TagModel where id = " + id, TagModel.class).list();
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getId() == id) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
