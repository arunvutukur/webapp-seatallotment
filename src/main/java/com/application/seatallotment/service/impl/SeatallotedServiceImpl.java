package com.application.seatallotment.service.impl;

import com.application.seatallotment.service.SeatallotedService;
import com.application.seatallotment.domain.Seatalloted;
import com.application.seatallotment.repository.SeatallotedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Service Implementation for managing Seatalloted.
 */
@Service
public class SeatallotedServiceImpl implements SeatallotedService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final SeatallotedRepository seatallotedRepository;

    public SeatallotedServiceImpl(SeatallotedRepository seatallotedRepository) {
        this.seatallotedRepository = seatallotedRepository;
       }


    @Override
    public Seatalloted save(Seatalloted seatalloted) {
        return seatallotedRepository.save(seatalloted);
    }

    @Override
    public Seatalloted update(Seatalloted seatalloted) {
        Optional<Seatalloted> sOptional=seatallotedRepository.findById(seatalloted.getId());
        sOptional.get().setAlloted(seatalloted.isAlloted());
        sOptional.get().setFloor(seatalloted.getFloor());
        sOptional.get().setLocation(seatalloted.getLocation());
        sOptional.get().setRequestForApproval(seatalloted.isRequestForApproval());
        sOptional.get().setPendingForApproval(seatalloted.isPendingForApproval());
        Seatalloted seatAllotedEntity=seatallotedRepository.save(sOptional.get());
        return seatAllotedEntity;
	}

    @Override
    public Page<Seatalloted> findAll(Pageable pageable) {
        return seatallotedRepository.findAll(pageable);
    }

    @Override
    public Optional<Seatalloted> findOne(String id) {
        return seatallotedRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete seat: {}", id);
    seatallotedRepository.deleteById(id);
    }
}
