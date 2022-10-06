package ge.bkapa.discovery.assigment.config;

import ge.bkapa.discovery.assigment.model.entity.Planet;
import ge.bkapa.discovery.assigment.model.entity.Route;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RunOnStartup {

    @PersistenceContext
    EntityManager em;

    @Value("${populate.db}")
    private boolean populateDatabase;

    private final static String DB_DATA_FILE_NAME = "data.xlsx";

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void populateDatabaseIfFlagIsTrue() {
        if (populateDatabase) {
            try {
                readDataAndPopulateDatabase(DB_DATA_FILE_NAME);
            } catch (IOException ex) {
                System.out.println("ERROR - Couldn't load data, some data might be absent!");
            }
        }
    }

    private void readDataAndPopulateDatabase(String fileName) throws IOException {
        ClassPathResource cpr = new ClassPathResource(fileName);
        Workbook workbook = new XSSFWorkbook(cpr.getInputStream());
        Map<String, Planet> planets = persistPlanets(workbook.getSheetAt(0));
        persistRoutes(workbook.getSheetAt(1), planets);
    }

    private Map<String, Planet> persistPlanets(Sheet planetsSheet) {
        List<Planet> planets = new ArrayList<>();
        for (Row row : planetsSheet) {
            if (row.getRowNum() == 0) continue;
            int cnt = 0;
            Planet planet = new Planet();
            for (Cell cell : row) {
                if (cnt == 0) {
                    planet.setId(cell.getStringCellValue());
                } else {
                    planet.setName(cell.getStringCellValue());
                }
                cnt++;
            }
            em.persist(planet);
            planets.add(planet);
        }
        return planets.stream().collect(Collectors.toMap(Planet::getId, p -> p));
    }

    private void persistRoutes(Sheet routeSheet, Map<String, Planet> planets) {
        for (Row row : routeSheet) {
            if (row.getRowNum() == 0) continue;
            int cnt = 0;
            Route route = new Route();
            for (Cell cell : row) {
                if (cnt == 1) {
                    route.setFrom(planets.get(cell.getStringCellValue()));
                } else if (cnt == 2) {
                    route.setTo(planets.get(cell.getStringCellValue()));
                } else if (cnt == 3) {
                    route.setDistance(BigDecimal.valueOf(cell.getNumericCellValue()));
                }
                cnt++;
            }
            if (route.getFrom() != null && route.getTo() != null) { // L' planet was listed in routes' sheet but not in planets' sheet
                em.persist(route);
            }
        }
    }
}
