package org.springframework.samples.petclinic.owner;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ElectricalPannelRepository extends MongoRepository<ElectricalPanel, BigInteger> {

}
