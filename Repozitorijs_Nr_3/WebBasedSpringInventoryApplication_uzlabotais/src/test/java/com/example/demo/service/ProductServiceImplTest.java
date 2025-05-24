package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
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
 * Unit tests for {@link ProductServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImpl(productRepository);
    }

    @Test
    void findAll_returnsRepositoryResults() {
        Product p1 = new Product();
        Product p2 = new Product();
        List<Product> expected = Arrays.asList(p1, p2);
        when(productRepository.findAll()).thenReturn(expected);

        List<Product> actual = service.findAll();

        assertSame(expected, actual);
        verify(productRepository).findAll();
    }

    @Test
    void listAll_withNullKeyword_returnsAll() {
        List<Product> expected = List.of(new Product());
        when(productRepository.findAll()).thenReturn(expected);

        List<Product> actual = service.listAll(null);

        assertSame(expected, actual);
        verify(productRepository).findAll();
        verify(productRepository, never()).search(any());
    }

    @Test
    void listAll_withKeyword_callsSearch() {
        String keyword = "set";
        List<Product> expected = List.of(new Product());
        when(productRepository.search(keyword)).thenReturn(expected);

        List<Product> actual = service.listAll(keyword);

        assertSame(expected, actual);
        verify(productRepository).search(keyword);
        verify(productRepository, never()).findAll();
    }

    @Test
    void findById_whenPresent_returnsProduct() {
        int id = 1;
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product actual = service.findById(id);
        assertSame(product, actual);
    }

    @Test
    void findById_whenMissing_throwsException() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> service.findById(2));
        assertTrue(ex.getMessage().contains("2"));
    }

    @Test
    void save_delegatesToRepository() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);  // <- FIX

        service.save(product);

        verify(productRepository).save(product);
    }

    @Test
    void deleteById_delegatesToRepository() {
        int id = 5;
        doNothing().when(productRepository).deleteById(5L);

        service.deleteById(id);

        verify(productRepository).deleteById(5L);
    }
}
