package com.filmrental.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class FeeCalculator {

    private FeeCalculator() {
    }

    public static BigDecimal calculate(BigDecimal rentalRate, LocalDateTime rentalDate, LocalDateTime returnDate) {
        if (rentalRate == null) {
            return BigDecimal.ZERO;
        }
        long days = ChronoUnit.DAYS.between(rentalDate.toLocalDate(), returnDate.toLocalDate());
        if (days < 1) {
            days = 1;
        }
        return rentalRate.multiply(BigDecimal.valueOf(days)).setScale(2, RoundingMode.HALF_UP);
    }
}
