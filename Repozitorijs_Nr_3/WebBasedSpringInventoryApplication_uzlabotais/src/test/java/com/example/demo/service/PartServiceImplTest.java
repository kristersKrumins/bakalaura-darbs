package com.example.demo.service;

import com.example.demo.domain.Part;
import com.example.demo.repositories.PartRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PartServiceImpl}. Uses Mockito mocks because
 * {@code Part} is abstract.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PartServiceImplUnitTest {

    @Mock
    PartRepository partRepository;

    PartServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PartServiceImpl(partRepository);
    }

    @Test
    void findAll_returnsRepositoryResults() {
        Part p1 = mock(Part.class);
        Part p2 = mock(Part.class);
        List<Part> expected = Arrays.asList(p1, p2);
        when(partRepository.findAll()).thenReturn(expected);

        List<Part> actual = service.findAll();

        assertSame(expected, actual);
        verify(partRepository).findAll();
    }

    @Test
    void listAll_withNullKeyword_returnsAll() {
        List<Part> expected = List.of(mock(Part.class));
        when(partRepository.findAll()).thenReturn(expected);

        List<Part> actual = service.listAll(null);

        assertSame(expected, actual);
        verify(partRepository).findAll();
        verify(partRepository, never()).search(any());
    }

    @Test
    void listAll_withKeyword_callsSearch() {
        String keyword = "gear";
        List<Part> expected = List.of(mock(Part.class));
        when(partRepository.search(keyword)).thenReturn(expected);

        List<Part> actual = service.listAll(keyword);

        assertSame(expected, actual);
        verify(partRepository).search(keyword);
        verify(partRepository, never()).findAll();
    }

    @Test
    void findById_whenPresent_returnsPart() {
        int id = 1;
        Part part = mock(Part.class);
        when(partRepository.findById(1L)).thenReturn(Optional.of(part));

        Part actual = service.findById(id);
        assertSame(part, actual);
    }

    @Test
    void findById_whenMissing_throwsException() {
        when(partRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> service.findById(2));
        assertTrue(ex.getMessage().contains("2"));
    }

    @Test
    void save_delegatesToRepository() {
        Part part = mock(Part.class);
        when(partRepository.save(part)).thenReturn(part);   // <- FIX

        service.save(part);

        verify(partRepository).save(part);
    }

    @Test
    void deleteById_delegatesToRepository() {
        int id = 5;
        doNothing().when(partRepository).deleteById(5L);

        service.deleteById(id);

        verify(partRepository).deleteById(5L);
    }
}
