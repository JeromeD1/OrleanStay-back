package com.formation.orleanStay.mapper;

import com.formation.orleanStay.models.DTO.ReservationDTO;
import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.request.ReservationSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper {
    /**
     * Mapping reservationId to the id of {@link Reservation} entity
     *
     * @param reservationId to map
     * @return the {@link Reservation} with the id of the reservationId value
     */
    default Reservation fromReservationId(Long reservationId) {
        if(reservationId == null) {
            return null;
        }

        final Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        return reservation;

    }


    /**
     * Mapping {@link Reservation} to {@link ReservationDTO}
     *
     * @param reservation to map
     * @return the {@link ReservationDTO} mapped from {@link Reservation}
     */
    ReservationDTO toReservationDTO(Reservation reservation);



    /**
     * Mapping {@link ReservationSaveRequest} to {@link Reservation}
     *
     * @param reservationSaveRequest to map
     * @return the {@link Reservation} mapped from {@link ReservationSaveRequest}
     */
    Reservation fromReservationSaveRequest(ReservationSaveRequest reservationSaveRequest);


    /**
     * Override {@link Reservation} with {@link ReservationSaveRequest}
     *
     * @param saveRequest the {@link ReservationSaveRequest} used to override {@link Reservation}
     * @param reservation {@link Reservation} to be overwritten
     */
    @Mapping(source = "saveRequest.checkinDate", target = "checkinDate")
    @Mapping(source = "saveRequest.checkoutDate", target = "checkoutDate")
    @Mapping(source = "saveRequest.nbAdult", target = "nbAdult")
    @Mapping(source = "saveRequest.nbChild", target = "nbChild")
    @Mapping(source = "saveRequest.nbBaby", target = "nbBaby")
    @Mapping(source = "saveRequest.reservationPrice", target = "reservationPrice")
    @Mapping(source = "saveRequest.accepted", target = "accepted")
    @Mapping(source = "saveRequest.cancelled", target = "cancelled")
    @Mapping(source = "saveRequest.depositAsked", target = "depositAsked")
    @Mapping(source = "saveRequest.depositReceived", target = "depositReceived")
    @Mapping(source = "saveRequest.depositValue", target = "depositValue")
    @Mapping(source = "saveRequest.travellerMessage", target = "travellerMessage")
    @Mapping(source = "saveRequest.traveller.personalInformations.id", target = "traveller.personalInformations.id")
    void overrideFromReservationSaveRequest(ReservationSaveRequest saveRequest, @MappingTarget Reservation reservation);
}
