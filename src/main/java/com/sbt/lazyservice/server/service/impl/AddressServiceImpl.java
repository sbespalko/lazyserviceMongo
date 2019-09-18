package com.sbt.lazyservice.server.service.impl;

import com.sbt.lazyservice.server.service.AddressService;
import com.sbt.lazyservice.server.domain.Address;
import com.sbt.lazyservice.server.repository.AddressRepository;
import com.sbt.lazyservice.server.service.dto.AddressDTO;
import com.sbt.lazyservice.server.service.mapper.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Address}.
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        Address address = addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.toDto(address);
    }

    /**
     * Get all the addresses.
     *
     * @return the list of entities.
     */
    @Override
    public List<AddressDTO> findAll() {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll().stream()
            .map(addressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one address by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<AddressDTO> findOne(String id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id)
            .map(addressMapper::toDto);
    }

    /**
     * Delete the address by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
    }
}
