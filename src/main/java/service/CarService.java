package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import DAO.DailySalesDao;
import model.Car;
import model.DailyReport;
import model.DailySales;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {
    private static CarService carService;
    private DailySalesService dailySalesService = DailySalesService.getInstance();

    private CarService() {}

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService();
        }
        return carService;
    }

    public List<Car> getAllCars() {
        try {
            return getCarDao().getAllCar();
        } catch (HibernateException he) {
            return null;
        }
    }

    public Car getCars(String brand, String model, String licensePlate) {
        try {
            return getCarDao().getCar(brand, model, licensePlate);
        } catch (HibernateException he) {
            return null;
        }
    }

    public boolean addCars(Car car) {
        int maxCar = 0;
        try {
            for (Car eachCar: getAllCars()) {
                if (eachCar.getBrand().equals(car.getBrand())) {
                    maxCar = maxCar + 1;
                }
            }
            if (maxCar >= 10) {
                return false;
            }
            getCarDao().addCar(car);
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    public boolean buyCars(Car car) {
        try {
            getCarDao().buyCar(car);
            dailySalesService.createOrUpdateSales(new DailySales(car.getPrice(), 1L));
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    public DailyReport newDay() {
        DailySales dailySales = dailySalesService.getLastSales();
        DailyReport dailyReport;
        if (dailySales == null) {
            dailyReport = new DailyReport(0L, 0L);
        } else {
            dailyReport = new DailyReport(dailySales.getEarnings(), dailySales.getSoldCars());
        }
        dailySalesService.deleteDailySales();
        return dailyReport;
    }

    public boolean deleteCars() {
        try {
            getCarDao().deleteCar();
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    private static CarDao getCarDao() {
        return CarDao.getInstance(DBHelper.getSessionFactory());
    }
}
