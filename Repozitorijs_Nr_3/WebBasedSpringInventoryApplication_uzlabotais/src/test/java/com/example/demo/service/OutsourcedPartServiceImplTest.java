package com.example.demo.service;

import com.example.demo.domain.OutsourcedPart;
import com.example.demo.repositories.OutsourcedPartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link OutsourcedPartServiceImpl}.
 *
 * NOTE: This version matches your actual implementation —
 * there is no listAll/search branch, and findById returns null
 * when the entity isn’t found (it does not throw).
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OutsourcedPartServiceImplTest {

    @Mock OutsourcedPartRepository repository;

    OutsourcedPartServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OutsourcedPartServiceImpl(repository);
    }

    // ---------- findAll ----------
    @Test
    void findAll_returnsRepositoryResults() {
        OutsourcedPart p1 = mock(OutsourcedPart.class);
        OutsourcedPart p2 = mock(OutsourcedPart.class);
        List<OutsourcedPart> expected = Arrays.asList(p1, p2);
        when(repository.findAll()).thenReturn(expected);

        List<OutsourcedPart> actual = service.findAll();

        assertSame(expected, actual);
        verify(repository).findAll();
    }

    // ---------- findById ----------
    @Test
    void findById_whenPresent_returnsPart() {
        OutsourcedPart part = mock(OutsourcedPart.class);
        when(repository.findById(1L)).thenReturn(Optional.of(part));

        OutsourcedPart actual = service.findById(1);

        assertSame(part, actual);
    }

    @Test
    void findById_whenMissing_returnsNull() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        OutsourcedPart actual = service.findById(2);

        assertNull(actual, "Service should return null when part not found");
    }

    // ---------- save ----------
    @Test
    void save_delegatesToRepository() {
        OutsourcedPart part = mock(OutsourcedPart.class);
        when(repository.save(part)).thenReturn(part);

        service.save(part);

        verify(repository).save(part);
    }

    // ---------- deleteById ----------
    @Test
    void deleteById_delegatesToRepository() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5);

        verify(repository).deleteById(5L);
    }
}
