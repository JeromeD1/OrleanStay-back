package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mapper.ReservationChatMapper;
import com.formation.orleanStay.models.DTO.ReservationChatDTO;
import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.entity.ReservationChat;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.request.ReservationChatSaveRequest;
import com.formation.orleanStay.repository.ReservationChatRepository;
import com.formation.orleanStay.utils.Findbyid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ReservationChatServiceImplTest {

    @Mock
    private ReservationChatRepository reservationChatRepository;

    @Mock
    private ReservationChatMapper reservationChatMapper;

    @Mock
    private Findbyid findbyid;

    @InjectMocks
    private ReservationChatServiceImpl reservationChatService;

    @Test
    void testFindByReservationId() {
        ReservationChat reservationChat = new ReservationChat();
        ReservationChatDTO reservationChatDTO = new ReservationChatDTO();
        List<ReservationChat> chatComments = List.of(reservationChat);
        when(reservationChatRepository.findByReservation_Id(anyLong())).thenReturn(chatComments);
        when(reservationChatMapper.toReservationChatDTO(reservationChat)).thenReturn(reservationChatDTO);

        var result = reservationChatService.findByReservationId(anyLong());

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(reservationChatDTO);
        verify(reservationChatRepository, times(1)).findByReservation_Id(anyLong());
        verify(reservationChatMapper, times(1)).toReservationChatDTO(reservationChat);

    }

    @Test
    void testCreate() {
        ReservationChat reservationChat = new ReservationChat();
        Reservation reservation = new Reservation();
        Utilisateur utilisateur = new Utilisateur();
        ReservationChatDTO reservationChatDTO = new ReservationChatDTO();
        ReservationChatSaveRequest reservationChatSaveRequest = new ReservationChatSaveRequest();

        reservationChatSaveRequest.setReservationId(1L);
        reservationChatSaveRequest.setUtilisateurId(1L);

        when(reservationChatMapper.fromReservationChatSaveRequest(any(ReservationChatSaveRequest.class))).thenReturn(reservationChat);
        when(findbyid.findReservationById(1L)).thenReturn(reservation);
        when(findbyid.findUtilisateurById(1L)).thenReturn(utilisateur);
        when(reservationChatRepository.save(any(ReservationChat.class))).thenReturn(reservationChat);
        when(reservationChatMapper.toReservationChatDTO(any(ReservationChat.class))).thenReturn(reservationChatDTO);

        var result = reservationChatService.create(reservationChatSaveRequest);
        assertThat(result.getClass()).isEqualTo(ReservationChatDTO.class);
    }

    @Test
    void testDelete() {
        ReservationChat reservationChat = new ReservationChat();
        reservationChat.setId(1L);
        when(findbyid.findReservationChatById(anyLong())).thenReturn(reservationChat);

        reservationChatService.delete(reservationChat.getId());

        verify(findbyid, times(1)).findReservationChatById(anyLong());
        verify(reservationChatRepository, times(1)).delete(reservationChat);
    }

}