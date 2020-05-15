package servlet;

import com.google.gson.Gson;
import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {
    private DailyReportService dailyReportService = DailyReportService.getInstance();
    private CarService carService = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DailyReport dailyReport = carService.newDay();
        if (dailyReportService.newDays(dailyReport)) {
            Gson gson = new Gson();
            String json = gson.toJson(dailyReport);
            resp.getWriter().println(json);
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
