package de.patst.serviceprovider.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AssignDeliveryOrderController.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AssignDeliveryOrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AssignDeliveryOrderController assignDeliveryOrderController;

  @Test
  public synchronized void testAssignDeliveryOrder() throws Exception {
    String respondToUrl = "http://response.url";

    MockRestServiceServer mock = MockRestServiceServer.bindTo(assignDeliveryOrderController.getRestTemplate()).build();
    mock.expect(MockRestRequestMatchers.requestTo(respondToUrl))
        .andRespond(MockRestResponseCreators.withSuccess());

    mockMvc.perform(
        put(
            "/delivery-service/{deliveryServiceId}/order?processInstanceId=123&respondTo=" + respondToUrl,
            1)
            .content("{}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    // Wait for the response to arrive
    this.wait(2000);
    // check that the mock was really called
    mock.verify();
  }

}
