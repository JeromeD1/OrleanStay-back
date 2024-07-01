package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mapper.UtilisateurMapper;
import com.formation.orleanStay.models.DTO.UtilisateurDTO;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.enumeration.ERole;
import com.formation.orleanStay.repository.UtilisateurRepository;
import com.formation.orleanStay.service.UtilisateurService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
