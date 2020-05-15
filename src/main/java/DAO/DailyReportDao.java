package DAO;

import model.DailyReport;
import org.hibernate.*;

import java.util.List;

public class DailyReportDao {
    private static DailyReportDao dailyReportDao;
    private SessionFactory sessionFactory;

    private DailyReportDao() {}
    private DailyReportDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportDao getInstance(SessionFactory sessionFactory) {
        if (dailyReportDao == null) {
            dailyReportDao = new DailyReportDao(sessionFactory);
        }
        return dailyReportDao;
    }

    public List<DailyReport> getAllDailyReport() throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("from DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastReport() throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from DailyReport order by id DESC");
        query.setMaxResults(1);
        DailyReport dailyReport = (DailyReport) query.uniqueResult();
        transaction.commit();
        session.close();
        return dailyReport;
    }

    public void newDay(DailyReport dailyReport) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dailyReport);
        transaction.commit();
        session.close();
    }

    public void deleteReport() throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }
}