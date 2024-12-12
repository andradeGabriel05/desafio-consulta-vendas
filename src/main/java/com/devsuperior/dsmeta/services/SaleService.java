package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.web.servlet.HandlerMapping;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
    @Qualifier("resourceHandlerMapping")
    @Autowired
    private HandlerMapping resourceHandlerMapping;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleMinDTO> getReport(LocalDate minDate, LocalDate maxDate, String name) {
		if (maxDate == null) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			maxDate = today;
		}

		if (minDate == null) {
			LocalDate result = maxDate.minusYears(1L);
			minDate = result;
		}

		if (name == null) {
			name = "";
		}

		return repository.findSalesReport(minDate, maxDate, name);

	}

	public List<SaleSummaryDTO> getSummary(LocalDate minDate, LocalDate maxDate) {
		if (maxDate == null) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			maxDate = today;
		}

		if (minDate == null) {
			LocalDate result = maxDate.minusYears(1L);
			minDate = result;
		}

		return repository.findSummary(minDate, maxDate);
	}

}
