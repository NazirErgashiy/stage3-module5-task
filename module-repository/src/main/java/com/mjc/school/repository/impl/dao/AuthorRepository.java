package com.mjc.school.repository.impl.dao;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.repository.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {


    public AuthorRepository() {
        //Init Hibernate on startup
        HibernateUtil.getSessionFactory();
    }

    @Override
    public List<AuthorModel> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AuthorModel order by id asc", AuthorModel.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<AuthorModel> readAll(Integer pageNumber, Integer pageSize, String sortBy) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "from AuthorModel";
            if (sortBy != null) {
                hql += " order by " + sortBy;
            }
            Query query = session.createQuery(hql);
            if (pageNumber != null) {
                int firstResult = (pageNumber - 1) * pageSize;
                query.setFirstResult(firstResult);
                query.setMaxResults(pageSize);
            }
            List<AuthorModel> result = query.getResultList();
            tx.commit();
            session.close();
            return result;
        } catch (HibernateException exception) {
            System.out.println(exception.getMessage());
        }
        throw new RuntimeException("Something wrong with Author readAll with paging");
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(AuthorModel.class, id));
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
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
    public AuthorModel update(AuthorModel entity) {
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
            AuthorModel model = readById(id).get();
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

            List<AuthorModel> models = session.createQuery("from AuthorModel where id = " + id, AuthorModel.class).list();
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
