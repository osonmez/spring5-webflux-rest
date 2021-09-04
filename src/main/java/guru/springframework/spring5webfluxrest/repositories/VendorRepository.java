package guru.springframework.spring5webfluxrest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.spring5webfluxrest.domain.Vendor;

@Repository
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String>{

}
