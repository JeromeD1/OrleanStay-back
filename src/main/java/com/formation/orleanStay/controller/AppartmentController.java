package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.AppartmentDTO;
import com.formation.orleanStay.models.DTO.AppartmentNameAndOwnerDTO;
import com.formation.orleanStay.models.request.AppartmentSaveRequest;
import com.formation.orleanStay.service.AppartmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/appartment")
@Slf4j
public class AppartmentController {
    private final AppartmentService appartmentService;

    public AppartmentController(AppartmentService appartmentService){
        this.appartmentService = appartmentService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AppartmentDTO> findAll() {
        log.info("Fetching all appartments");
        return appartmentService.findAll();
    }

    @GetMapping("/names")
    @ResponseStatus(HttpStatus.OK)
    public List<AppartmentNameAndOwnerDTO> findAllNamesAndOwners() {
        log.info("Fetching all appartment names and owners");
        return appartmentService.findAllNamesAndOwners();
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<AppartmentDTO> findAllActive() {
        log.info("Fetching all active appartments");
        printUserRole();
        return appartmentService.findAllActive();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppartmentDTO findById(@PathVariable Long id) {
        log.info("Fetching appartment of id {}", id);
        return appartmentService.findDTOById(id);
    }

    @GetMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppartmentDTO> findByOwnerId(@PathVariable Long id) {
        log.info("Fetching all appartments from ownerId = {}", id);
        return appartmentService.findByOwnerId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppartmentDTO create(@Valid @RequestBody AppartmentSaveRequest appartmentSaveRequest) {
        log.info("Creating a new appartment : {}", appartmentSaveRequest);
        return appartmentService.create(appartmentSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppartmentDTO update(@PathVariable Long id, @Valid @RequestBody AppartmentSaveRequest appartmentSaveRequest) {
        log.info("Updating appartment with id {} and values : {}", id, appartmentSaveRequest);
        return appartmentService.update(id, appartmentSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Deleting appartment with id {}", id);
        appartmentService.delete(id);
    }


    public void printUserRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            Collection<? extends GrantedAuthority> authorities = ((UserDetails)principal).getAuthorities();
            System.out.println("L'utilisateur actuel est " + username);
            System.out.println("Son rôle est " + authorities.toString());
        } else {
            System.out.println("L'utilisateur actuel est " + principal.toString());
        }
    }
}
