package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.exception.forbidden.ForbiddenRoleChangeException;
import com.formation.orleanStay.mapper.UtilisateurMapper;
import com.formation.orleanStay.models.DTO.AppartmentDTO;
import com.formation.orleanStay.models.DTO.UtilisateurDTO;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.enumeration.ERole;
import com.formation.orleanStay.repository.UtilisateurRepository;
import com.formation.orleanStay.service.AppartmentService;
import com.formation.orleanStay.service.UtilisateurService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {
    final private UtilisateurRepository utilisateurRepository;
    final private UtilisateurMapper utilisateurMapper;
    final private Findbyid findbyid;
    final private AppartmentService appartmentService;


    @Override
    public List<UtilisateurDTO> findAll() {
        final List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream()
                .map(utilisateurMapper::toUtilisateurDTO)
                .toList();
    }

    @Override
    public List<UtilisateurDTO> findByRoleOwner() {
        List<ERole> roleList = new ArrayList<>();
        roleList.add(ERole.OWNER);
        roleList.add(ERole.ADMIN);
//        final List<Utilisateur> utilisateurs = utilisateurRepository.findByRole(ERole.OWNER);
        final List<Utilisateur> utilisateurs = utilisateurRepository.findByRoleIn(roleList);
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

    @Override
    public UtilisateurDTO updateRole(Long id, ERole newRole) {
        final List<AppartmentDTO> userAppartments = appartmentService.findByOwnerId(id);
        if(!userAppartments.isEmpty() && newRole == ERole.USER){
            throw new ForbiddenRoleChangeException(id);
        }

        final Utilisateur utilisateur = findbyid.findUtilisateurById(id);
        utilisateur.setRole(newRole);
        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toUtilisateurDTO(updatedUtilisateur);
    }

}
