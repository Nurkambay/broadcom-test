package broadcom.storage;

import broadcom.core.model.MachineProperties;
import broadcom.core.repository.MachinePropertiesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class MachinePropertiesRepositoryImpl implements MachinePropertiesRepository {

    @Value("${machine.capacity}")
    private int maxCapacity;

    @Override
    public MachineProperties get() {
        return new MachineProperties(maxCapacity);
    }
}
