package de.patst.serviceprovider;

import de.patst.serviceprovider.model.DeliveryServiceDTO;
import java.util.ArrayList;
import java.util.List;

public class DeliveryServices {

  public static final List<DeliveryServiceDTO> DELIVERY_SERVICES = new ArrayList<>();

  static {
    DELIVERY_SERVICES.add(new DeliveryServiceDTO(1L, "Transport Trödel"));
    DELIVERY_SERVICES.add(new DeliveryServiceDTO(2L, "Lightning Transports"));
    DELIVERY_SERVICES.add(new DeliveryServiceDTO(3L, "Crash Crew"));
  }
}
