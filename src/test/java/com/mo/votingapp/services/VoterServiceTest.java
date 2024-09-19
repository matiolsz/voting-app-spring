package com.mo.votingapp.services;

import com.mo.votingapp.entity.Voter;
import com.mo.votingapp.exceptions.ValidationException;
import com.mo.votingapp.repository.VoterRepository;
import com.mo.votingapp.service.VoterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
public class VoterServiceTest {
    @Mock
    private VoterRepository voterRepository;

    @InjectMocks
    private VoterService voterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createVoter_withValidVoter_shouldSaveAndReturnVoter() {
        Voter voter = new Voter(null, "Alice", false);
        Voter savedVoter = new Voter(1L, "Alice", false);
        when(voterRepository.save(voter)).thenReturn(savedVoter);

        Voter result = voterService.createVoter(voter);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Alice", result.getName());
        assertFalse(result.isVoted());
    }

    @Test
    void createVoter_withExistingId_shouldThrowValidationException() {
        Voter voter = new Voter(1L, "Bob", false);

        assertThrows(ValidationException.class, () -> voterService.createVoter(voter));
    }

    @Test
    void updateVoter_withExistingVoter_shouldUpdateAndReturnVoter() {
        Voter existingVoter = new Voter(1L, "Charlie", false);
        Voter updatedVoter = new Voter(1L, "Charlie", true);
        when(voterRepository.findByName("Charlie")).thenReturn(Optional.of(existingVoter));
        when(voterRepository.save(any(Voter.class))).thenReturn(updatedVoter);

        Optional<Voter> result = voterService.updateVoter("Charlie");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Charlie", result.get().getName());
        assertTrue(result.get().isVoted());
    }

    @Test
    void updateVoter_withNonExistingVoter_shouldReturnEmptyOptional() {
        when(voterRepository.findByName("David")).thenReturn(Optional.empty());

        Optional<Voter> result = voterService.updateVoter("David");

        assertFalse(result.isPresent());
    }

}
