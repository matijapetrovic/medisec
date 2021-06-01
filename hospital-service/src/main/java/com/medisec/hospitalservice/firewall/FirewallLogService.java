package com.medisec.hospitalservice.firewall;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirewallLogService {
    private final FirewallLogRepository fireWallLogRepository;

    public void save(FirewallLogRequest request) {
        FirewallLog log =
                new FirewallLog(
                        null,
                         request.getMessage()
                        );

        fireWallLogRepository.save(log);
    }
}
