package io.github.shamrice.timeclock.core.manager;

import io.github.shamrice.timeclock.core.model.Model;
import io.github.shamrice.timeclock.core.model.models.SessionModel;
import io.github.shamrice.timeclock.core.model.models.UserModel;
import io.github.shamrice.timeclock.core.model.models.UserTimeModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static io.github.shamrice.timeclock.core.manager.DatabaseAction.*;

public class DataManager<T extends Model> {

    private Session session;
    private SessionFactory sessionFactory;


    public void create(T item) {
        doAction(CREATE, item);
    }

    public void update(T item) {
        doAction(UPDATE, item);
    }

    public void delete(T item) {
        doAction(DELETE, item);
    }

    public T get(Class<T> modelType, int itemId) {
        T item = null;

        try {
            session = buildSessionFactory().openSession();
            item = session.get(modelType, itemId);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return item;
    }

    public T getByColumn(Class<T> modelType, String columnName, Object value) {
        T item = null;

        session = buildSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(modelType);
        Root<T> from = criteriaQuery.from(modelType);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get(columnName), value));

        TypedQuery<T> typedQuery = session.createQuery(criteriaQuery);

        try {
            session = buildSessionFactory().openSession();
            return typedQuery.getSingleResult();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return item;
    }

    private SessionFactory buildSessionFactory() {

        if (sessionFactory == null) {
            Configuration config = new Configuration();

            config.addAnnotatedClass(UserModel.class);
            config.addAnnotatedClass(UserTimeModel.class);
            config.addAnnotatedClass(SessionModel.class);

            config.configure("hibernate.cfg.xml");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

            sessionFactory = config.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }

    private void doAction(DatabaseAction action, T item) {
        try {
            session = buildSessionFactory().openSession();
            session.beginTransaction();

            switch(action) {
                case CREATE:
                    session.save(item);
                    break;
                case UPDATE:
                    session.update(item);
                    break;
                case DELETE:
                    session.delete(item);
                    break;
            }

            session.getTransaction().commit();
        } catch (Exception ex) {
            if (null != session.getTransaction()) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
