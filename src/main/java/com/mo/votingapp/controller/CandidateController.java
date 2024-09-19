package com.mo.votingapp.controller;


import com.mo.votingapp.entity.Candidate;
import com.mo.votingapp.exceptions.ValidationException;
import com.mo.votingapp.service.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CandidateController {

    private final CandidateService candidateService;

    CandidateController(CandidateService candidateService){
        this.candidateService = candidateService;
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        return ResponseEntity.ok(candidateService.getAllCandidates());
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.createCandidate(candidate));
    }

    @PutMapping("/{name}/vote")
    public ResponseEntity<Candidate> updateCollectedVotes(@PathVariable String name) {
        return ResponseEntity.of(candidateService.updateCollectedVotes(name));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity invalidCandidate(){
        return ResponseEntity.badRequest().build();
    }

}