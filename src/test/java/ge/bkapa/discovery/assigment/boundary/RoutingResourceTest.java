package ge.bkapa.discovery.assigment.boundary;

import ge.bkapa.discovery.assigment.model.repository.RouteRepository;
import ge.bkapa.discovery.assigment.utils.RouteFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoutingResourceTest {

    @MockBean
    private RouteRepository routeRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenDisconnectedGraphsShouldReturnEmptyPath() throws Exception {
        Mockito.when(routeRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(RouteFactory.getRoute("A", "B", new BigDecimal("1")));
            add(RouteFactory.getRoute("C", "D", new BigDecimal("2")));
        }});

        mvc.perform(MockMvcRequestBuilders.get("/routing")
                        .param("from", "A")
                        .param("to", "D"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length").value(0.00))
                .andExpect(jsonPath("$.path.length()").value(0));
    }

    @Test
    public void givenInvalidNodeShouldReturnEmptyPath() throws Exception {
        Mockito.when(routeRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(RouteFactory.getRoute("A", "B", new BigDecimal("1")));
            add(RouteFactory.getRoute("C", "D", new BigDecimal("2")));
        }});

        mvc.perform(MockMvcRequestBuilders.get("/routing")
                        .param("from", "A")
                        .param("to", "E"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length").value(0.00))
                .andExpect(jsonPath("$.path.length()").value(0));
    }

    @Test
    public void givenOneCorrectPathShouldFindPath() throws Exception {
        Mockito.when(routeRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(RouteFactory.getRoute("A", "B", new BigDecimal("1")));
            add(RouteFactory.getRoute("B", "G", new BigDecimal("2")));
            add(RouteFactory.getRoute("A", "C", new BigDecimal("1")));
            add(RouteFactory.getRoute("C", "D", new BigDecimal("0.24")));
            add(RouteFactory.getRoute("D", "E", new BigDecimal("14")));
        }});

        mvc.perform(MockMvcRequestBuilders.get("/routing")
                        .param("from", "A")
                        .param("to", "E"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length").value(15.24))
                .andExpect(jsonPath("$.path.length()").value(3));
    }

    @Test
    public void givenTwoCorrectPathsShouldFindShortestPath() throws Exception {
        Mockito.when(routeRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(RouteFactory.getRoute("A", "B", new BigDecimal("1")));
            add(RouteFactory.getRoute("B", "E", new BigDecimal("2")));
            add(RouteFactory.getRoute("A", "C", new BigDecimal("1")));
            add(RouteFactory.getRoute("C", "D", new BigDecimal("0.24")));
            add(RouteFactory.getRoute("D", "E", new BigDecimal("14")));
        }});

        mvc.perform(MockMvcRequestBuilders.get("/routing")
                        .param("from", "A")
                        .param("to", "E"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length").value(3))
                .andExpect(jsonPath("$.path.length()").value(2));
    }
}
