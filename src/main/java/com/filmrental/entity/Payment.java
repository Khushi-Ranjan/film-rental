package com.filmrental.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payment")
@Data
@NoArgsConstructor
@ToString(exclude="staff")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="payment_id")
    private Integer paymentId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="staff_id", nullable=false)
    @JsonIgnore
    private Staff staff;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rental_id")
    private Rental rental;

    @Column(name="amount", nullable=false)
    private BigDecimal amount;

    @Column(name="payment_date", nullable=false)
    private LocalDateTime paymentDate;

    @Column(name="last_update", insertable=false, updatable=false)
    private LocalDateTime lastUpdate;
}