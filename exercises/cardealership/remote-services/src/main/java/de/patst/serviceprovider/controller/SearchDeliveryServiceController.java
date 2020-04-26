package de.patst.serviceprovider.controller;

import de.patst.serviceprovider.DeliveryServices;
import de.patst.serviceprovider.model.DeliveryServiceDTO;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchDeliveryServiceController {

  @ResponseBody
  @GetMapping(value = "/delivery-service", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<DeliveryServiceDTO> listDeliveryServices() {
    return DeliveryServices.DELIVERY_SERVICES;
  }
}
