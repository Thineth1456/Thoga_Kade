package model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    String code;
    String description;
    Double unitPrice;
    Integer qtyOnHand;

}
