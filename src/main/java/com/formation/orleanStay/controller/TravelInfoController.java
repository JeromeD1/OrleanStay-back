package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.PhotoDTO;
import com.formation.orleanStay.models.DTO.TravelInfoDTO;
import com.formation.orleanStay.models.request.TravelInfoListSaveRequest;
import com.formation.orleanStay.models.request.TravelInfoSaveRequest;
import com.formation.orleanStay.service.TravelInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/travelInfo")
@Slf4j
@AllArgsConstructor
public class TravelInfoController {

    private final TravelInfoService travelInfoService;

    @GetMapping("/appartment/{appartmentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TravelInfoDTO> findByAppartmentId(@PathVariable Long appartmentId){
        log.info("Fetching all travelInfos from appartment of id {}", appartmentId);
        return travelInfoService.findByAppartmentId(appartmentId);
    }

    @GetMapping("/reservation/{reservationId}/traveller/{travellerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TravelInfoDTO> findByReservationAndTravellerIds(@PathVariable Long reservationId, @PathVariable Long travellerId) {
        log.info("Fetching all travelInfos from appartment of reservation of id {}", reservationId);
        System.out.println("Dans controleur 1111111111111");
        return travelInfoService.findByReservationAndTravellerIds(reservationId, travellerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TravelInfoDTO create(@RequestBody TravelInfoSaveRequest saveRequest){
        log.info("Creating new travelInfo {}", saveRequest);
        return travelInfoService.create(saveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TravelInfoDTO update(@PathVariable Long id, @RequestBody TravelInfoSaveRequest saveRequest, @RequestParam(required = false) String oldImgId) throws IOException {
        log.info("Updating travelInfo {}", saveRequest);
        return travelInfoService.update(id, saveRequest, oldImgId);
    }

    @PutMapping("/appartment/{appartmentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TravelInfoDTO> updateOrder(@PathVariable Long appartmentId ,@RequestBody TravelInfoListSaveRequest travelInfoDTOList){
        log.info("Updating travelInfo position orders from {}", travelInfoDTOList);
        return travelInfoService.updateOrder(appartmentId, travelInfoDTOList);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<List<TravelInfoDTO>> delete(@PathVariable Long id, @RequestParam(required = false) String oldImgId) throws IOException {
        log.info("Deleting travelInfo with id {}", id);
        List<TravelInfoDTO> travelInfos = travelInfoService.delete(id, oldImgId);
        return ResponseEntity.ok(travelInfos);
    }
}
