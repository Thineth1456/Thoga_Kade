package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    private String id;
    private String customerId;
    private String orderId;
    private Double discountPercentage;
}
