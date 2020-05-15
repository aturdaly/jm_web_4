package service;

import DAO.DailyReportDao;
import DAO.DailySalesDao;
import model.DailySales;
import org.hibernate.HibernateException;
import util.DBHelper;

import java.util.List;

public class DailySalesService {
    private static DailySalesService dailySalesService;

    private DailySalesService() {
    }

    public static DailySalesService getInstance() {
        if (dailySalesService == null) {
            dailySalesService = new DailySalesService();
        }
        return dailySalesService;
    }

    public DailySales getLastSales() {
        try {
            return getDailySalesDao().getLastSale();
        } catch (HibernateException he) {
            return null;
        }
    }

    public boolean createOrUpdateSales(DailySales dailySales) {
        DailySales lastSale = getLastSales();
       try {
           if (lastSale == null) {
               getDailySalesDao().createSale(dailySales);
           } else {
               lastSale.setEarnings(dailySales.getEarnings() + lastSale.getEarnings());
               lastSale.setSoldCars(dailySales.getSoldCars() + lastSale.getSoldCars());
               getDailySalesDao().updateSale(lastSale);
           }
           return true;
       } catch (HibernateException he) {
           return false;
       }
    }

    public boolean deleteDailySales() {
        try {
            getDailySalesDao().deleteDailySale();
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    private DailySalesDao getDailySalesDao() {
        return DailySalesDao.getInstance(DBHelper.getSessionFactory());
    }
}
