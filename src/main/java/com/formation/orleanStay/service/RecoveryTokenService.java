package com.formation.orleanStay.service;


import com.formation.orleanStay.models.entity.RecoveryToken;
import com.formation.orleanStay.models.request.RecoveryTokenRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public interface RecoveryTokenService {
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    RecoveryToken findByToken(String token);

    @Transactional(propagation = Propagation.REQUIRED)
    void create(RecoveryTokenRequest recoveryTokenRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);
}
