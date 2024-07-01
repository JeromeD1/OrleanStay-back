package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.DiscountDTO;
import com.formation.orleanStay.models.request.DiscountSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface DiscountService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<DiscountDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    DiscountDTO findDTOById(Long id);

    @Transactional(propagation = Propagation.REQUIRED)
    DiscountDTO create(DiscountSaveRequest discountSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    DiscountDTO update(Long id, DiscountSaveRequest discountSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);



}
