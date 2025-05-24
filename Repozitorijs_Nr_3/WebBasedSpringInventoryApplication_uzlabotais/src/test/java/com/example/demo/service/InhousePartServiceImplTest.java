package com.example.demo.service;

import com.example.demo.domain.InhousePart;
import com.example.demo.repositories.InhousePartRepository;
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
 * Unit tests for {@link InhousePartServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InhousePartServiceImplTest {

    @Mock
    InhousePartRepository repository;

    InhousePartServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new InhousePartServiceImpl(repository);
    }

    // ---------- findAll ----------
    @Test
    void findAll_returnsRepositoryResults() {
        InhousePart p1 = mock(InhousePart.class);
        InhousePart p2 = mock(InhousePart.class);
        List<InhousePart> expected = Arrays.asList(p1, p2);
        when(repository.findAll()).thenReturn(expected);

        List<InhousePart> actual = service.findAll();

        assertSame(expected, actual);
        verify(repository).findAll();
    }

    // ---------- findById ----------
    @Test
    void findById_whenPresent_returnsPart() {
        InhousePart part = mock(InhousePart.class);
        when(repository.findById(1L)).thenReturn(Optional.of(part));

        InhousePart actual = service.findById(1);

        assertSame(part, actual);
    }

    @Test
    void findById_whenMissing_returnsNull() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        InhousePart actual = service.findById(2);

        assertNull(actual, "Service should return null when part not found");
    }

    // ---------- save ----------
    @Test
    void save_delegatesToRepository() {
        InhousePart part = mock(InhousePart.class);
        when(repository.save(part)).thenReturn(part);   // repository returns entity

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
