package com.mjc.school.repository.impl.dao;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.util.HibernateUtil;
import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.repository.impl.model.NewsModel;
import com.mjc.school.repository.impl.model.TagModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    @Override
    public List<NewsModel> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from NewsModel order by id asc", NewsModel.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can't read data from DB");
    }

    @SuppressWarnings("unchecked")
    public List<NewsModel> readAll(Integer pageNumber, Integer pageSize, String sortBy) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "from NewsModel";
            if (sortBy != null) {
                hql += " order by " + sortBy;
            }
            Query query = session.createQuery(hql);
            if (pageNumber != null) {
                int firstResult = (pageNumber - 1) * pageSize;
                query.setFirstResult(firstResult);
                query.setMaxResults(pageSize);
            }
            List<NewsModel> result = query.getResultList();
            tx.commit();
            session.close();
            return result;
        } catch (HibernateException exception) {
            System.out.println(exception.getMessage());
        }
        throw new RuntimeException("Something wrong with News readAll with paging");
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(NewsModel.class, id));
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public NewsModel create(NewsModel entity) {
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
        if (readById(generatedId).isPresent()) {
            return readById(generatedId).get();
        }
        throw new RuntimeException("No entity");
    }

    @Override
    public NewsModel update(NewsModel entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            entity.setCreateDate(readById(entity.getId()).get().getCreateDate());
            session.update(entity);
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        if (readById(entity.getId()).isPresent()) {
            return readById(entity.getId()).get();
        }
        throw new RuntimeException("No entity");
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            NewsModel model = readById(id).get();
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
            List<NewsModel> models = session.createQuery("from NewsModel where id = " + id, NewsModel.class).list();
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

    @SuppressWarnings("unchecked")
    public List<NewsModel> getNewsByParams(String tagName, List<Long> tagIds, String authorName, String title, String content) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<NewsModel> criteriaQuery = criteriaBuilder.createQuery(NewsModel.class);
        Root<NewsModel> root = criteriaQuery.from(NewsModel.class);

        List<Predicate> predicates = new ArrayList<>();

        if (tagName != null || tagIds != null) {
            Join<TagModel, NewsModel> newsTags = root.join("tagsId");
            if (tagName != null) {
                predicates.add(newsTags.get("name").in(tagName));
            }
            if (tagIds != null) {
                predicates.add(newsTags.get("id").in(tagIds));
            }
        }
        if (authorName != null) {
            Join<AuthorModel, NewsModel> newsAuthor = root.join("author");
            predicates.add(criteriaBuilder.like(newsAuthor.get("name"), "%" + authorName + "%"));
        }
        if (title != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }
        if (content != null) {
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + content + "%"));
        }
        criteriaQuery.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));

        Query employeeQuery = session.createQuery(criteriaQuery);
        List<NewsModel> result = employeeQuery.getResultList();
        session.close();

        return result;
    }
}
