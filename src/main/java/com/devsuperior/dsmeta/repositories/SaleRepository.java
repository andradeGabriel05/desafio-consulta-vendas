package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, SUM(obj.amount), obj.date, seller.name) " +
            "FROM Sale obj " +
            "JOIN obj.seller seller " +
            "WHERE obj.date >= :minDate AND obj.date <= :maxDate AND LOWER(seller.name) LIKE LOWER(CONCAT('%', :sellerName, '%'))" +
            "GROUP BY obj.id, obj.date, seller.name")
    List<SaleMinDTO> findSalesReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("sellerName") String sellerName);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(seller.name, SUM(obj.amount))" +
            "FROM Sale obj " +
            "JOIN obj.seller seller " +
            "WHERE obj.date >= :minDate AND obj.date <= :maxDate " +
            "GROUP BY seller.name")
    List<SaleSummaryDTO> findSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

}
