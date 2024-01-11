package com.mjc.school.repository.impl.dao;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.util.HibernateUtil;
import com.mjc.school.repository.impl.model.CommentModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository implements BaseRepository<CommentModel, Long> {

    @Override
    public List<CommentModel> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CommentModel order by id asc", CommentModel.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<CommentModel> readAll(Integer pageNumber, Integer pageSize, String sortBy) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "from CommentModel";
            if (sortBy != null) {
                hql += " order by " + sortBy;
            }
            Query query = session.createQuery(hql);
            if (pageNumber != null) {
                int firstResult = (pageNumber - 1) * pageSize;
                query.setFirstResult(firstResult);
                query.setMaxResults(pageSize);
            }
            List<CommentModel> result = query.getResultList();
            tx.commit();
            session.close();
            return result;
        } catch (HibernateException exception) {
            System.out.println(exception.getMessage());
        }
        throw new RuntimeException("Something wrong with Comment readAll with paging");
    }

    @Override
    public Optional<CommentModel> readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(CommentModel.class, id));
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public CommentModel create(CommentModel entity) {
        Long generatedId = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            generatedId = (Long) session.save(entity);
            tx.commit();
            session.close();
        } catch (HibernateException exception) {
            System.out.println(exception.getMessage());
        }
        return readById(generatedId).get();
    }

    @Override
    public CommentModel update(CommentModel entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            entity.setCreateDate(readById(entity.getId()).get().getCreateDate());
            session.update(entity);
            tx.commit();
            session.close();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
        return readById(entity.getId()).get();
    }

    @Override
    public boolean deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            CommentModel model = readById(id).get();
            session.delete(model);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {

            List<CommentModel> models = session.createQuery("from CommentModel where id = " + id, CommentModel.class).list();
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getId() == id) {
                    return true;
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }
}
