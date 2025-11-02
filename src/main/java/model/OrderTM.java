package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderTM {
    String code;
    String description;
    Integer qty;
    Double unitPrice;
    Double total;
}
