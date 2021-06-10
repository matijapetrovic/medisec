package com.medisec.hospitalservice.logs.service_log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceLogService {
    private final ServiceLogRepository serviceLogRepository;

    public void save(ServiceLogRequest request) {
        ServiceLog log =
                new ServiceLog(
                        UUID.randomUUID().toString(),
                        request.getSourceIp(),
                        request.getDestIp(),
                        request.getPath(),
                        request.getProtocol(),
                        request.getStatus(),
                        request.getTime(),
                        request.getPacketSize(),
                        LogType.valueOf(request.getType())
                        );

        serviceLogRepository.save(log);
    }

    public List<ServiceLog> findAll() {
        return serviceLogRepository.findAll();
    }
}
