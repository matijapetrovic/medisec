package com.medisec.hospitalservice.logs.service_log;

import com.medisec.hospitalservice.alarms.service_log_alarm.LogType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public List<ServiceLog> findAll() { return serviceLogRepository.findAll(); }

    public List<ServiceLog> search(
                                   String sourceIp,
                                   String destId,
                                   String path,
                                   String protocol,
                                   int status,
                                   Date time
                                   ) {
        return serviceLogRepository.search(
                sourceIp,
                destId,
                path,
                protocol,
                status,
                time
        );
    }
}
