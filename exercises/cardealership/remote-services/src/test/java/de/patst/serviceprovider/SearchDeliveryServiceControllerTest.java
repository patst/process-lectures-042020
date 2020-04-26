package de.patst.serviceprovider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.patst.serviceprovider.controller.SearchDeliveryServiceController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SearchDeliveryServiceController.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SearchDeliveryServiceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testSearchDeliveryServices() throws Exception {
    mockMvc.perform(get("/delivery-service")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(3));
  }

}
