package com.crypto.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("userCryptoPrices")
public class UserCryptoPrice {
    @Id
    private ObjectId id;
    @Indexed
    private long userId;
    private List<CryptoPrice> cryptoPrices;
    private LocalDateTime lastAccessed;
}
