package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {
    private static DailyReportService dailyReportService;

    private DailyReportService() {

    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService();
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        try {
            return getDailyReportDao().getAllDailyReport();
        } catch (HibernateException he) {
            return null;
        }
    }

    public DailyReport getLastReport() {
        try {
            return getDailyReportDao().getLastReport();
        } catch (HibernateException he) {
            return null;
        }
    }

    public boolean newDays(DailyReport dailyReport) {
        try {
            getDailyReportDao().newDay(dailyReport);
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    public boolean deleteReports() {
        try {
            getDailyReportDao().deleteReport();
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    private DailyReportDao getDailyReportDao() {
        return DailyReportDao.getInstance(DBHelper.getSessionFactory());
    }
}
