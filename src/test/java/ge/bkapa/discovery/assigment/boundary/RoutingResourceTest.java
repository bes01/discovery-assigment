package ge.bkapa.discovery.assigment.boundary;

import ge.bkapa.discovery.assigment.model.repository.RouteRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class RoutingResourceTest {

    @MockBean
    private RouteRepository routeRepository;

    
}
