package com.example.student.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.dao.NameDao;
import com.example.student.entity.Name;

@SpringBootTest
public class NameServiceTest {

    @Mock
    private NameDao nameDao;

    @InjectMocks
    private NameService nameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        Name name = new Name();
        when(nameDao.save(any(Name.class))).thenReturn(name);

        Name savedName = nameService.save(name);

        assertThat(savedName).isNotNull();
        verify(nameDao, times(1)).save(name);
    }

    @Test
    public void testGetAllNames() {
        Name name1 = new Name();
        Name name2 = new Name();
        List<Name> names = Arrays.asList(name1, name2);
        when(nameDao.findAll()).thenReturn(names);

        List<Name> result = nameService.getAllNames();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(nameDao, times(1)).findAll();
    }

    @Test
    public void testGetNameById() {
        Long id = 1L;
        Name name = new Name();
        when(nameDao.findById(id)).thenReturn(Optional.of(name));

        Optional<Name> result = nameService.getNameById(id);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(name);
        verify(nameDao, times(1)).findById(id);
    }

    @Test
    public void testCreateName() {
        Name name = new Name();
        when(nameDao.save(any(Name.class))).thenReturn(name);

        Name createdName = nameService.createName(name);

        assertThat(createdName).isNotNull();
        verify(nameDao, times(1)).save(name);
    }

    @Test
    public void testUpdateName() {
        Long id = 1L;
        Name existingName = new Name();
        existingName.setNameId(id);
        existingName.setFname("Old First Name");
        existingName.setLname("Old Last Name");
        existingName.setMname("Old Middle Name");

        Name newName = new Name();
        newName.setFname("New First Name");
        newName.setLname("New Last Name");
        newName.setMname("New Middle Name");

        when(nameDao.findById(id)).thenReturn(Optional.of(existingName));
        when(nameDao.save(any(Name.class))).thenReturn(existingName);

        Name updatedName = nameService.updateName(id, newName);

        assertThat(updatedName).isNotNull();
        assertThat(updatedName.getFname()).isEqualTo("New First Name");
        assertThat(updatedName.getLname()).isEqualTo("New Last Name");
        assertThat(updatedName.getMname()).isEqualTo("New Middle Name");
        verify(nameDao, times(1)).findById(id);
        verify(nameDao, times(1)).save(existingName);
    }

    @Test
    public void testUpdateName_NotFound() {
        Long id = 1L;
        Name newName = new Name();
        when(nameDao.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> nameService.updateName(id, newName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Name not found with id: " + id);

        verify(nameDao, times(1)).findById(id);
        verify(nameDao, never()).save(any(Name.class));
    }

    @Test
    public void testDeleteName() {
        Long id = 1L;
        when(nameDao.existsById(id)).thenReturn(true);

        nameService.deleteName(id);

        verify(nameDao, times(1)).existsById(id);
        verify(nameDao, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteName_NotFound() {
        Long id = 1L;
        when(nameDao.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> nameService.deleteName(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Name not found with id: " + id);

        verify(nameDao, times(1)).existsById(id);
        verify(nameDao, never()).deleteById(id);
    }
}
