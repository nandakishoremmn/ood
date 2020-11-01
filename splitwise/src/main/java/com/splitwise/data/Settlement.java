package com.splitwise.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Settlement {
    Integer source;
    Integer target;
    Integer amount;

    @Override
    public String toString() {
        if(amount > 0){
            return String.format("%s owes Rs. %s to %s", source, amount, target);
        } else {
            return String.format("%s gets Rs. %s from %s", source, -amount, target);
        }
    }
}
