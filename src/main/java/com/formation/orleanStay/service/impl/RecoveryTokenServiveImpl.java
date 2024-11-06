package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.exception.unknown.UnknownRecoveryTokenException;
import com.formation.orleanStay.models.entity.RecoveryToken;
import com.formation.orleanStay.models.request.RecoveryTokenRequest;
import com.formation.orleanStay.repository.RecoveryTokenRepository;
import com.formation.orleanStay.service.RecoveryTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RecoveryTokenServiveImpl implements RecoveryTokenService {
    private final RecoveryTokenRepository recoveryTokenRepository;

    @Override
    public RecoveryToken findByToken(String token) {
        return recoveryTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.warn("Aucun Token trouvé avec {}", token);
                    return new UnknownRecoveryTokenException();
                });
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
