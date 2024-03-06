package org.farm.server.service;

import org.farm.server.model.entities.FarmerEntity;
import org.farm.server.model.entities.UserEntity;
import org.farm.server.model.requests.SaveFarmerRequest;
import org.farm.server.repository.FarmerRepository;
import org.farm.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FarmerService {
    private final FarmerRepository farmerRepository;

    private final UserRepository userRepository;

    public FarmerService(FarmerRepository farmerRepository, UserRepository userRepository) {
        this.farmerRepository = farmerRepository;
        this.userRepository = userRepository;
    }

    public FarmerEntity saveFarmer(SaveFarmerRequest saveFarmerRequest) {
        Optional<UserEntity> user = userRepository.findById(saveFarmerRequest.getUserId());
        if (user.isEmpty()) {
            return null;
        }
        FarmerEntity farmerEntity = new FarmerEntity();
        farmerEntity.setName(saveFarmerRequest.getName());
        farmerEntity.setSurname(saveFarmerRequest.getSurname());
        farmerEntity.setPatronymic(saveFarmerRequest.getPatronymic());
        farmerEntity.setUser(user.get());
        return farmerRepository.save(farmerEntity);
    }
}
