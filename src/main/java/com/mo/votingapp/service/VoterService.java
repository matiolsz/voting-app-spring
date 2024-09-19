package com.mo.votingapp.service;

import com.mo.votingapp.entity.Voter;
import com.mo.votingapp.exceptions.ValidationException;
import com.mo.votingapp.repository.VoterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class VoterService {

    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository){
        this.voterRepository = voterRepository;
    }

    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public Voter createVoter(Voter voter) {
        if( voter.getId()!=null ){
            throw new ValidationException();
        }
        return voterRepository.save(voter);
    }

    public Optional<Voter> updateVoter(String name) {
        return voterRepository.findByName(name)
                .map(voter -> {
                    voter.setVoted(true);
                    return voterRepository.save(voter);
                });
    }

}
