package guru.springframework.spring5webfluxrest.controllers;

import java.util.Objects;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

	private final  VendorRepository vendorRepository;

	public VendorController(VendorRepository vendorRepository) {
		super();
		this.vendorRepository = vendorRepository;
	}
	
	@GetMapping("/api/v1/vendors")
	Flux<Vendor> list(){
		return vendorRepository.findAll();
	}
	
	@GetMapping("/api/v1/vendors/{id}")
	Mono<Vendor> getById(@PathVariable String id){
		return vendorRepository.findById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/vendors")
	Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream){
		return vendorRepository.saveAll(vendorStream).then();
	}	
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/api/v1/vendors/{id}")
	Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
		return vendorRepository.save(vendor);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/api/v1/vendors/{id}")
	Mono<Vendor> pathc(@PathVariable String id, @RequestBody Vendor vendor){
		Mono<Vendor> foundVendor = vendorRepository.findById(id);
		return foundVendor.flatMap(f -> {
			boolean flag = false;
			if(!Objects.equals(f.getFirstName(), vendor.getFirstName())) {
				f.setFirstName(vendor.getFirstName());
				flag = true;
			}
			if(!Objects.equals(f.getLastName(), vendor.getLastName())) {
				f.setLastName(vendor.getLastName());
				flag = true;
			}
			if(flag) {
				return vendorRepository.save(f);
			}			
			return foundVendor;
		});
	}
	
}
