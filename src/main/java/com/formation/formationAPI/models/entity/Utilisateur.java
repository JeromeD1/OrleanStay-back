package com.formation.formationAPI.models.entity;

import com.formation.formationAPI.models.enumeration.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utilisateur",
        uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private ERole role = ERole.USER;

    @Column(nullable = false)
    @Email
    private String login;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDateTime creationDate;

    @OneToOne // pas de cascade = CascadeType.ALL car personalInformation est aussi lié à la table Traveller. Il faut une méthode personnalisée dans le service
    @JoinColumn(name = "personal_information_id", foreignKey = @ForeignKey(name = "fk_user_PersonalInfo"))
    private PersonalInformation personalInformations;

    /**
     * Set creation date to now when creating User
     */
    public void setDateForCreation() {
        final LocalDateTime now = LocalDateTime.now();
        this.creationDate = now;
    }

    /**
     * Set role to USER
     */
    public void setRoleToUser(){
        this.role = ERole.USER;
    }

    /**
     * Auto call of setDateForCreation when creating a new User and Auto creation of role when signup
     */
    @PrePersist
    protected void onCreate(){
        setDateForCreation();
        setRoleToUser();
    }


}
