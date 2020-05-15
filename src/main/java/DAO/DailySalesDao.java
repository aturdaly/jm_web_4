package DAO;

import model.DailySales;
import org.hibernate.*;
import java.util.List;

public class DailySalesDao {
    private static DailySalesDao dailySalesDao;
    private SessionFactory sessionFactory;

    private DailySalesDao() {}
    private DailySalesDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailySalesDao getInstance(SessionFactory sessionFactory) {
        if (dailySalesDao == null) {
            dailySalesDao = new DailySalesDao(sessionFactory);
        }
        return dailySalesDao;
    }

    public DailySales getLastSale() throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from DailySales order by id DESC");
        query.setMaxResults(1);
        DailySales dailySales = (DailySales) query.uniqueResult();
        transaction.commit();
        session.close();
        return dailySales;
    }

    public void createSale(DailySales dailySales) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dailySales);
        transaction.commit();
        session.close();
    }

    public DailySales findSaleById(Long id) throws HibernateException {
        Session session = sessionFactory.openSession();
        DailySales dailySales = (DailySales) session.get(DailySales.class, id);
        session.close();
        return dailySales;
    }

    public void updateSale(DailySales dailySales) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(dailySales);
        transaction.commit();
        session.close();
    }

    public void deleteDailySale() throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from DailySales").executeUpdate();
        transaction.commit();
        session.close();
    }
}