package com.mo.votingapp.controller;

import com.mo.votingapp.entity.Voter;
import com.mo.votingapp.exceptions.ValidationException;
import com.mo.votingapp.service.VoterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VoterController {

    private VoterService voterService;

    public VoterController(VoterService voterService){
        this.voterService = voterService;
    }

    @GetMapping
    public ResponseEntity<List<Voter>> getAllVoters() {
        return ResponseEntity.ok(voterService.getAllVoters());
    }

    @PostMapping
    public ResponseEntity<Voter> createVoter(@RequestBody Voter voter) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(voterService.createVoter(voter));
    }

    @PutMapping("/{name}/voter")
    public ResponseEntity<Voter> updateVoter(@PathVariable String name) {
        return ResponseEntity.of(voterService.updateVoter(name));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity invalidVoter(){
        return ResponseEntity.badRequest().build();
    }
}
