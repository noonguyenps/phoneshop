package project.phoneshop.mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.phoneshop.model.entity.ShipEntity;
import project.phoneshop.model.payload.request.ship.AddShipRequest;
import project.phoneshop.service.ShipService;

@Component
public class ShipMapping {
    @Autowired
    ShipService shipService;

    public ShipEntity modelToEntity(AddShipRequest addShipRequest){
        ShipEntity newShip = new ShipEntity();
        newShip.setShipType(addShipRequest.getShipType());
        newShip.setShipPrice(addShipRequest.getShipPrice());
        return newShip;
    }

    public ShipEntity updateToEntity(AddShipRequest addShipRequest,int id){
        ShipEntity ship = shipService.findShipById(id);
        ship.setShipType(addShipRequest.getShipType());
        ship.setShipPrice(addShipRequest.getShipPrice());
        return ship;
    }
}
