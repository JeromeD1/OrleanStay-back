package com.formation.formationAPI.service.impl;

import com.formation.formationAPI.exception.unknown.UnknownAppartmentIdException;
import com.formation.formationAPI.exception.unknown.UnknownInfoIdException;
import com.formation.formationAPI.mapper.InfoMapper;
import com.formation.formationAPI.models.DTO.InfoDTO;
import com.formation.formationAPI.models.entity.Appartment;
import com.formation.formationAPI.models.entity.Info;
import com.formation.formationAPI.models.entity.PersonalInformation;
import com.formation.formationAPI.models.request.InfoListSaveRequest;
import com.formation.formationAPI.models.request.InfoSaveRequest;
import com.formation.formationAPI.models.request.PersonalInformationSaveRequest;
import com.formation.formationAPI.repository.AppartmentRepository;
import com.formation.formationAPI.repository.InfoRepository;
import com.formation.formationAPI.service.InfoService;
import com.formation.formationAPI.utils.Findbyid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class InfoServiceImpl implements InfoService {

    final private InfoRepository infoRepository;
    final private InfoMapper infoMapper;
    final private Findbyid findbyid;

    public InfoServiceImpl(InfoRepository infoRepository, InfoMapper infoMapper, Findbyid findbyid){
        this.infoRepository = infoRepository;
        this.infoMapper = infoMapper;
        this.findbyid = findbyid;
    }

    @Override
    public List<InfoDTO> findAll() {
        final List<Info> infos = infoRepository.findAll();
        return infos.stream()
                .map(infoMapper::toInfoDTO)
                .toList();
    }

    @Override
    public InfoDTO findDTOById(Long id) {
        final Info info = findbyid.findInfoById(id);
        return infoMapper.toInfoDTO(info);
    }


//    @Override
//    public InfoDTO create(InfoSaveRequest infoSaveRequest) {
//        // Création de l'info sans l'appartement pour obtenir un ID
//        final Info infoToSave = infoMapper.fromInfoSaveRequest(infoSaveRequest);
//        final  Info savedInfo = infoRepository.save(infoToSave);
//        // Récupération de l'appartement et mise à jour de l'info avec la référence à l'appartement
//        final Appartment appartment = findbyid.findAppartmentById(infoSaveRequest.getAppartmentId());
//        savedInfo.setAppartment(appartment);
//        // Sauvegarde de l'info mise à jour
//        final Info updatedInfo = infoRepository.save(savedInfo);
//        return infoMapper.toInfoDTO(updatedInfo);
//    }

//    @Override
//    public InfoDTO update(Long id, InfoSaveRequest infoSaveRequest){
//        infoSaveRequest.setId(id);
//        final Appartment appartment = findbyid.findAppartmentById(infoSaveRequest.getAppartmentId());
//        final Info infoToUpdate = findbyid.findInfoById(id);
//        infoToUpdate.setAppartment(appartment);
//        infoMapper.overrideFromInfoSaveRequest(infoSaveRequest, infoToUpdate);
//        final Info savedInfo = infoRepository.save(infoToUpdate);
//        return infoMapper.toInfoDTO(savedInfo);
//    }

    @Override
    public List<InfoDTO> update(Long appartmentId, InfoListSaveRequest infosToRegister) {
        //Récupération de l'appartement
        final Appartment appartment = findbyid.findAppartmentById(appartmentId);

        //Récupération des infos actuelles de l'appartement
        final List<Info> actualAppartmentInfos = appartment.getInfos();

        //Suppression en BDD des infos qui ne sont plus présentes dans les infosTo register
        deleteInfosNonPresentInRequest(actualAppartmentInfos, infosToRegister);

        //pour toutes les infos de la requete
        // --> Si l'info n'a pas d'id : création d'une nouvelle info
        // --> sinon on compare positionOrder et le champ info entre la requete et l'info existante
            //--> si un des champs est différent : on update
        final List<InfoDTO> infosToReturn = new ArrayList<>();

        for(InfoSaveRequest info : infosToRegister.getInfos()) {
            if(info.getId() == null) {
                //create new info
                final Info infoToSave = infoMapper.fromInfoSaveRequest(info);
                infoToSave.setAppartment(appartment);
                Info savedInfo = infoRepository.save(infoToSave);
                infosToReturn.add(infoMapper.toInfoDTO(savedInfo));
            } else  {
                Optional<Info> matchingInfo = actualAppartmentInfos.stream().filter(x -> x.getId().equals(info.getId())).findFirst();
                Info actualInfo = matchingInfo.get();
                if(matchingInfo.isPresent()) {
                    if(!compareInfoDatas(info, actualInfo)){
                        //update actual info
                        infoMapper.overrideFromInfoSaveRequest(info, actualInfo);
                        Info savedInfo = infoRepository.save(actualInfo);
                        infosToReturn.add(infoMapper.toInfoDTO(savedInfo));
                    } else {
                        //just add actual info to returned list
                        infosToReturn.add(infoMapper.toInfoDTO(actualInfo));
                    }
                }
            }
        }

        return infosToReturn.stream().sorted(Comparator.comparing(InfoDTO::getPositionOrder)).toList();
    }
    
    @Override
    public void delete(Long id) {
        final Info infoToDelete = findbyid.findInfoById(id);
        infoRepository.delete(infoToDelete);
    }


    private Boolean compareInfoDatas(InfoSaveRequest request, Info information){
        if (request == null || information == null) {
            return false;
        }
        return Objects.equals(request.getInfo(), information.getInfo()) &&
                Objects.equals(request.getPositionOrder(), information.getPositionOrder());
    }

    private void deleteInfosNonPresentInRequest(List<Info> actualAppartmentInfos, InfoListSaveRequest infosToRegister){
        //Récupération de l'ensemble des id des infos actuelles et des infos de la requete
        final List<Long> actualInfoIds = actualAppartmentInfos.stream().map(Info::getId).toList();
        final List<Long> requestInfoIds = infosToRegister.getInfos().stream().map(InfoSaveRequest::getId).toList();

        //Suppression en BDD des infos qui ne sont plus présentes dans les infosTo register
        for(Long id : actualInfoIds) {
            if(!requestInfoIds.contains(id)){
                Info infoToDelete = findbyid.findInfoById(id);
                infoRepository.delete(infoToDelete);
            }
        }
    }
}
