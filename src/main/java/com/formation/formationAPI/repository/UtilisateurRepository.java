package com.formation.formationAPI.repository;

import com.formation.formationAPI.models.entity.Utilisateur;
import com.formation.formationAPI.models.enumeration.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    List<Utilisateur> findByRole(ERole role);

    Utilisateur findByLogin(String login);
}
