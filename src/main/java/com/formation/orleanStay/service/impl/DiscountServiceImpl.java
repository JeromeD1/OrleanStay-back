package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mapper.DiscountMapper;
import com.formation.orleanStay.models.DTO.DiscountDTO;
import com.formation.orleanStay.models.entity.Discount;
import com.formation.orleanStay.models.request.DiscountSaveRequest;
import com.formation.orleanStay.repository.DiscountRepository;
import com.formation.orleanStay.service.DiscountService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    final private DiscountRepository discountRepository;
    final private DiscountMapper discountMapper;
    final private Findbyid findbyid;

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountMapper discountMapper, Findbyid findbyid){
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
        this.findbyid = findbyid;
    }

    @Override
    public List<DiscountDTO> findAll() {
        final List<Discount> allDiscounts = discountRepository.findAll();

        return allDiscounts.stream()
                .map(discountMapper::toDiscountDTO)
                .toList();
    }

    @Override
    public DiscountDTO findDTOById(Long id){
        final Discount discounts = findbyid.findDiscountById(id);
        return discountMapper.toDiscountDTO(discounts);
    }

    @Override
    public DiscountDTO create(DiscountSaveRequest discountSaveRequest) {
        final Discount discountToSave = discountMapper.fromDiscountSaveRequest(discountSaveRequest);

        final Discount savedDiscount = discountRepository.save(discountToSave);

        return discountMapper.toDiscountDTO(savedDiscount);
    }

    @Override
    public DiscountDTO update(Long id, DiscountSaveRequest discountSaveRequest) {
        if(discountSaveRequest.getId() == null){
            discountSaveRequest.setId(id);
        }

        final Discount discountToUpdate = findbyid.findDiscountById(id);

        discountMapper.overrideFromDiscountSaveRequest(discountSaveRequest, discountToUpdate);

        Discount savedDiscount = discountRepository.save(discountToUpdate);

        return discountMapper.toDiscountDTO(savedDiscount);
    }

    @Override
    public void delete(Long id) {
        final Discount discountToDelete = findbyid.findDiscountById(id);
        discountRepository.delete(discountToDelete);
    }

}
