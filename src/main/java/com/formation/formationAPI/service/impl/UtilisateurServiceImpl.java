package com.formation.formationAPI.service.impl;

import com.formation.formationAPI.exception.unknown.UnknownUtilisateurIdException;
import com.formation.formationAPI.mapper.UtilisateurMapper;
import com.formation.formationAPI.models.DTO.AppartmentDTO;
import com.formation.formationAPI.models.DTO.UtilisateurDTO;
import com.formation.formationAPI.models.entity.Appartment;
import com.formation.formationAPI.models.entity.Utilisateur;
import com.formation.formationAPI.models.enumeration.ERole;
import com.formation.formationAPI.repository.UtilisateurRepository;
import com.formation.formationAPI.service.UtilisateurService;
import com.formation.formationAPI.utils.Findbyid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {
    final private UtilisateurRepository utilisateurRepository;
    final private UtilisateurMapper utilisateurMapper;
    final private Findbyid findbyid;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper, Findbyid findbyid){
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.findbyid = findbyid;
    }

    @Override
    public List<UtilisateurDTO> findAll() {
        final List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream()
                .map(utilisateurMapper::toUtilisateurDTO)
                .toList();
    }

    @Override
    public List<UtilisateurDTO> findByRoleOwner() {
        final List<Utilisateur> utilisateurs = utilisateurRepository.findByRole(ERole.OWNER);
        return utilisateurs.stream()
                .map(utilisateurMapper::toUtilisateurDTO)
                .toList();
    }

    @Override
    public List<UtilisateurDTO> findByRoleUser() {
        final List<Utilisateur> utilisateurs = utilisateurRepository.findByRole(ERole.USER);
        return utilisateurs.stream()
                .map(utilisateurMapper::toUtilisateurDTO)
                .toList();
    }

    @Override
    public List<UtilisateurDTO> findByRoleAdmin() {
        final List<Utilisateur> utilisateurs = utilisateurRepository.findByRole(ERole.ADMIN);
        return utilisateurs.stream()
                .map(utilisateurMapper::toUtilisateurDTO)
                .toList();
    }

    @Override
    public UtilisateurDTO findDTOById(Long id){
        final Utilisateur utilisateur = findbyid.findUtilisateurById(id);
        return utilisateurMapper.toUtilisateurDTO(utilisateur);
    }

}
