package com.formation.orleanStay.mapper;

import com.formation.orleanStay.models.DTO.ReservationChatDTO;
import com.formation.orleanStay.models.entity.ReservationChat;
import com.formation.orleanStay.models.request.ReservationChatSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationChatMapper {
    /**
     * Mapping commentId to the id of {@link ReservationChat} entity
     *
     * @param commentId to map
     * @return the {@link ReservationChat} with the id of the reservationChatId value
     */
    default ReservationChat fromReservationChatId(Long commentId) {
        if(commentId == null) {
            return null;
        }

        final ReservationChat comment = new ReservationChat();
        comment.setId(commentId);
        return comment;
    }


    /**
     * Mapping {@link ReservationChat} to {@link ReservationChatDTO}
     *
     * @param comment to map
     * @return the {@link ReservationChatDTO} mapped from {@link ReservationChat}
     */
    ReservationChatDTO toReservationChatDTO(ReservationChat comment);



    /**
     * Mapping {@link ReservationChatSaveRequest} to {@link ReservationChat}
     *
     * @param reservationChatSaveRequest to map
     * @return the {@link ReservationChat} mapped from {@link ReservationChatSaveRequest}
     */
    ReservationChat fromReservationChatSaveRequest(ReservationChatSaveRequest reservationChatSaveRequest);


    /**
     * Override {@link ReservationChat} with {@link ReservationChatSaveRequest}
     *
     * @param saveRequest the {@link ReservationChatSaveRequest} used to override {@link ReservationChat}
     * @param comment {@link ReservationChat} to be overwritten
     */
    @Mapping(source = "saveRequest.comment", target = "comment")
    void overrideFromReservationChatSaveRequest(ReservationChatSaveRequest saveRequest, @MappingTarget ReservationChat comment);
}
