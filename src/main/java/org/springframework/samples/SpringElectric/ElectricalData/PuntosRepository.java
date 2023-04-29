package org.springframework.samples.SpringElectric.ElectricalData;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PuntosRepository extends MongoRepository<PuntosElectricos, BigInteger> {

	Optional<PuntosElectricos> findByCups(String cups);

	Page<PuntosElectricos> findByCupsRegex(String regex, Pageable pageable);

}
