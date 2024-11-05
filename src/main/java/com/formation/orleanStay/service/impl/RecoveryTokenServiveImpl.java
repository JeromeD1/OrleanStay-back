package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.models.entity.RecoveryToken;
import com.formation.orleanStay.models.request.RecoveryTokenRequest;
import com.formation.orleanStay.repository.RecoveryTokenRepository;
import com.formation.orleanStay.service.RecoveryTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecoveryTokenServiveImpl implements RecoveryTokenService {
    private final RecoveryTokenRepository recoveryTokenRepository;

    @Override
    public RecoveryToken findByToken(String token) {
        return recoveryTokenRepository.findByToken(token);
    }

    @Override
    public void create(RecoveryTokenRequest recoveryTokenRequest) {
        RecoveryToken recoveryToken = new RecoveryToken(recoveryTokenRequest);
        recoveryTokenRepository.save(recoveryToken);
    }

    @Override
    public void delete(Long id) {
        recoveryTokenRepository.deleteById(id);
    }
}
