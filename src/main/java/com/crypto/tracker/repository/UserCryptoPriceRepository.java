package com.crypto.tracker.repository;

import com.crypto.tracker.model.UserCryptoPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCryptoPriceRepository extends MongoRepository<UserCryptoPrice, String> {
    Optional<UserCryptoPrice> findByUserId(long userId);

    void deleteByUserId(long userId);
}
