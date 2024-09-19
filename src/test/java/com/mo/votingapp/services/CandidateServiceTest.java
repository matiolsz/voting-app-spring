package com.mo.votingapp.services;


import com.mo.votingapp.entity.Candidate;
import com.mo.votingapp.exceptions.ValidationException;
import com.mo.votingapp.repository.CandidateRepository;
import com.mo.votingapp.service.CandidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class CandidateServiceTest {
    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateService candidateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCandidate_withExistingId_shouldThrowValidationException() {
        // Arrange
        Candidate invalidCandidate = new Candidate(1L, "Invalid Candidate", 0);

        // Act & Assert
        assertThrows(ValidationException.class, () -> candidateService.createCandidate(invalidCandidate));
        verify(candidateRepository, never()).save(any(Candidate.class));
    }

    @Test
    void updateCollectedVotes_withExistingCandidate_shouldIncrementVotesAndReturnUpdatedCandidate() {
        // Arrange
        String candidateName = "John Doe";
        Candidate existingCandidate = new Candidate(1L, candidateName, 10);
        Candidate updatedCandidate = new Candidate(1L, candidateName, 11);
        when(candidateRepository.findByName(candidateName)).thenReturn(Optional.of(existingCandidate));
        when(candidateRepository.save(any(Candidate.class))).thenReturn(updatedCandidate);

        // Act
        Optional<Candidate> result = candidateService.updateCollectedVotes(candidateName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedCandidate, result.get());
        verify(candidateRepository, times(1)).findByName(candidateName);
        verify(candidateRepository, times(1)).save(any(Candidate.class));
    }

    @Test
    void updateCollectedVotes_withNonExistingCandidate_shouldReturnEmptyOptional() {
        // Arrange
        String nonExistingCandidateName = "Non Existing Candidate";
        when(candidateRepository.findByName(nonExistingCandidateName)).thenReturn(Optional.empty());

        // Act
        Optional<Candidate> result = candidateService.updateCollectedVotes(nonExistingCandidateName);

        // Assert
        assertFalse(result.isPresent());
        verify(candidateRepository, times(1)).findByName(nonExistingCandidateName);
        verify(candidateRepository, never()).save(any(Candidate.class));
    }
}
