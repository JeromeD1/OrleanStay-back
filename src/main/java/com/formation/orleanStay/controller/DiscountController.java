package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.DiscountDTO;
import com.formation.orleanStay.models.request.DiscountSaveRequest;
import com.formation.orleanStay.service.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discount")
@Slf4j
public class DiscountController {
    private DiscountService discountService;

    public DiscountController(DiscountService discountService){
        this.discountService = discountService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DiscountDTO> findAll(){
        log.debug("Fetching all discounts");
        return discountService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DiscountDTO findById(@PathVariable Long id) {
        log.debug("Fetching discount with id = {}", id);
        return discountService.findDTOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountDTO create(@RequestBody DiscountSaveRequest discountSaveRequest) {
        log.debug("Creating new discount from discountSaveRequest :  {}", discountSaveRequest);
        return discountService.create(discountSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DiscountDTO update(@PathVariable Long id, @RequestBody DiscountSaveRequest discountSaveRequest) {
        log.debug("Updating discount of id {} with value {}", id, discountSaveRequest);
        return discountService.update(id, discountSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.debug("Deleting discount of id = {}", id);
        discountService.delete(id);
    }
}
