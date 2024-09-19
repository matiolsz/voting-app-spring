package com.mo.votingapp.service;

import com.mo.votingapp.entity.Candidate;
import com.mo.votingapp.exceptions.ValidationException;
import com.mo.votingapp.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;

    CandidateService(CandidateRepository candidateRepository){
        this.candidateRepository = candidateRepository;
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate createCandidate(Candidate candidate) {
        if(candidate.getId() != null) {
            throw new ValidationException();
        }
        return candidateRepository.save(candidate);
    }

    public Optional<Candidate> updateCollectedVotes(String name) {
        return candidateRepository.findByName(name)
                .map(candidate -> {
                    candidate.setCollectedVotes(candidate.getCollectedVotes() + 1);
                    Candidate updatedCandidate = candidateRepository.save(candidate);
                    return updatedCandidate;
                });
        }

    }
