package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.*;
import util.DBHelper;

import java.util.List;

public class CarDao {
    private static CarDao carDao;
    private SessionFactory sessionFactory;

    private CarDao() {}
    private CarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public static CarDao getInstance(SessionFactory sessionFactory) {
        if (carDao == null) {
            carDao = new CarDao(sessionFactory);
        }
        return carDao;
    }

    public List<Car> getAllCar() throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("from Car").list();
        transaction.commit();
        session.close();
        return cars;
    }

    public Car getCar(String brand, String model, String licensePlate) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Car where brand = :paramBrand AND model = :paramModel AND licensePlate = :paramLicensePlate";
        Query query = session.createQuery(hql);
        query.setParameter("paramBrand", brand);
        query.setParameter("paramModel", model);
        query.setParameter("paramLicensePlate", licensePlate);
        query.setMaxResults(1);
        Car car = (Car) query.uniqueResult();
        transaction.commit();
        session.close();
        return car;
    }

    public void addCar(Car car) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
        session.close();
    }

    public void buyCar(Car car) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(car);
        transaction.commit();
        session.close();
    }

    public void deleteCar() throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from Car").executeUpdate();
        transaction.commit();
        session.close();
    }
}