package com.formation.orleanStay.mailjet;

import com.formation.orleanStay.models.entity.Reservation;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailService {
    private final MailjetClient mailjetClient;

//    @Value("${mailjet.resa.accepte.template.id}")
//    private String templateResaAccepteId;

    @Value("${frontend.url}")
    private String lienDuSite;

    @Value("${domain.email}")
    private String domainEmail;

    public EmailService(MailjetClient mailjetClient) {
        this.mailjetClient = mailjetClient;
    }

    public void sendEmail(String recipientEmail,
                                              String recipientName,
                                              String subject,
                                              String templateId,
                                              Map<String, String> variables
    ) {

        try {
            MailjetRequest request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", domainEmail)
                                            .put("Name", "Orleans Stay"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", recipientEmail)
                                                    .put("Name", recipientName)))
                                    .put(Emailv31.Message.TEMPLATEID, Integer.parseInt(templateId))
                                    .put(Emailv31.Message.SUBJECT, subject)
                                    .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                    .put(Emailv31.Message.VARIABLES, new JSONObject(variables))));

            MailjetResponse response = mailjetClient.post(request);
            if (response.getStatus() == 200) {
                log.info("Email sent successfully to {}", recipientEmail);
            } else {
                log.error("Failed to send email. Status: {}, Error: {}",
                        response.getStatus(), response.getData());
            }
        } catch (MailjetException e) {
            log.error("Error sending email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }


    public Map<String, String> makeEmailResaAccepteeData(Reservation reservation) {
        Map<String, String> variables = new HashMap<>();
        variables.put("appartment", reservation.getAppartment().getDescription());
        variables.put("address", reservation.getAppartment().getAddress() + ", " + reservation.getAppartment().getZipcode() + " " + reservation.getAppartment().getCity());
        variables.put("checkinDate", reservation.getCheckinDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        variables.put("checkoutDate", reservation.getCheckoutDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        variables.put("nbAdult", String.valueOf(reservation.getNbAdult()));
        variables.put("nbChild", String.valueOf(reservation.getNbChild()));
        variables.put("nbBaby", String.valueOf(reservation.getNbBaby()));
        variables.put("ownerPhone", reservation.getAppartment().getOwner().getPersonalInformations().getPhone());
        variables.put("lienInfoResa", lienDuSite + "/infoReservation/" + reservation.getId() + "/" + reservation.getTraveller().getId());
        variables.put("lienDuSite", lienDuSite);

        return variables;
    }

    public Map<String, String> makeEmailDemandeResaData(Reservation reservation, Boolean isForOwner) {
        Map<String, String> variables = new HashMap<>();
        if(isForOwner) {
            variables.put("phraseAccroche", "Je serais intéressé pour réserver un de vos appartements.");
        } else {
            variables.put("phraseAccroche", "Votre demande de réservation de logement a bien été transmise.");
        }
        variables.put("appartment", reservation.getAppartment().getDescription());
        variables.put("checkinDate", reservation.getCheckinDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        variables.put("checkoutDate", reservation.getCheckoutDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        variables.put("nbAdult", String.valueOf(reservation.getNbAdult()));
        variables.put("nbChild", String.valueOf(reservation.getNbChild()));
        variables.put("nbBaby", String.valueOf(reservation.getNbBaby()));
        variables.put("nbNights", reservation.getNumberOfNights().toString());
        variables.put("reservationPrice", reservation.getReservationPrice() + " €");
        variables.put("lastname", reservation.getTraveller().getPersonalInformations().getLastname());
        variables.put("firstname", reservation.getTraveller().getPersonalInformations().getFirstname());
        variables.put("address", reservation.getTraveller().getPersonalInformations().getAddress() + ", " + reservation.getTraveller().getPersonalInformations().getZipcode() + " " + reservation.getTraveller().getPersonalInformations().getCity() + ", " + reservation.getTraveller().getPersonalInformations().getCountry());
        variables.put("email", reservation.getTraveller().getPersonalInformations().getEmail());
        variables.put("phone", reservation.getTraveller().getPersonalInformations().getPhone());
        variables.put("travellerMessage", reservation.getTravellerMessage());
        variables.put("lienDuSite", lienDuSite);

        return variables;
    }

}


