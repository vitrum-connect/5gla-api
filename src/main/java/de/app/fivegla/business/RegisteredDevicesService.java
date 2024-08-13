package de.app.fivegla.business;

import de.app.fivegla.persistence.RegisteredDeviceRepository;
import de.app.fivegla.persistence.entity.RegisteredDevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisteredDevicesService {

    private final GroupService groupService;
    private final RegisteredDeviceRepository registeredDeviceRepository;

    /**
     * Registers a device.
     *
     * @param prefilledEntity The prefilled device entity to register.
     * @return The registered device entity.
     */
    public RegisteredDevice registerDevice(RegisteredDevice prefilledEntity, String groupId) {
        log.info("Registering device: {}", prefilledEntity);
        prefilledEntity.setOid(UUID.randomUUID().toString());
        groupService.getOrDefault(prefilledEntity.getTenant(), groupId);
        return registeredDeviceRepository.save(prefilledEntity);
    }

}
