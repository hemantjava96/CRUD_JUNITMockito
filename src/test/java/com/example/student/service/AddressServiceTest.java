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

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.dao.AddressDao;
import com.example.student.entity.Address;

@SpringBootTest
public class AddressServiceTest {

    @Mock
    private AddressDao addressDao;

    @InjectMocks
    private AddressService addressService;

    @Test
    public void testSave() {
        Address address = new Address();
        when(addressDao.save(any(Address.class))).thenReturn(address);

        Address savedAddress = addressService.save(address);

        assertThat(savedAddress).isNotNull();
        verify(addressDao, times(1)).save(address);
    }

    @Test
    public void testGetAllAddresses() {
        Address address1 = new Address();
        Address address2 = new Address();
        List<Address> addresses = Arrays.asList(address1, address2);
        when(addressDao.findAll()).thenReturn(addresses);

        List<Address> result = addressService.getAllAddresses();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(addressDao, times(1)).findAll();
    }

    @Test
    public void testGetAddressById() {
        Long id = 1L;
        Address address = new Address();
        when(addressDao.findById(id)).thenReturn(Optional.of(address));

        Optional<Address> result = addressService.getAddressById(id);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(address);
        verify(addressDao, times(1)).findById(id);
    }

    @Test
    public void testCreateAddress() {
        Address address = new Address();
        when(addressDao.save(any(Address.class))).thenReturn(address);

        Address createdAddress = addressService.createAddress(address);

        assertThat(createdAddress).isNotNull();
        verify(addressDao, times(1)).save(address);
    }

    @Test
    public void testUpdateAddress() {
        Long id = 1L;
        Address existingAddress = new Address();
        existingAddress.setAddressId(id);
        existingAddress.setAddress("Old Address");
        existingAddress.setDistrict("Old District");
        existingAddress.setPincode("Old Pincode");
        existingAddress.setState("Old State");

        Address newAddress = new Address();
        newAddress.setAddress("New Address");
        newAddress.setDistrict("New District");
        newAddress.setPincode("New Pincode");
        newAddress.setState("New State");

        when(addressDao.findById(id)).thenReturn(Optional.of(existingAddress));
        when(addressDao.save(any(Address.class))).thenReturn(existingAddress);

        Address updatedAddress = addressService.updateAddress(id, newAddress);

        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.getAddress()).isEqualTo("New Address");
        assertThat(updatedAddress.getDistrict()).isEqualTo("New District");
        assertThat(updatedAddress.getPincode()).isEqualTo("New Pincode");
        assertThat(updatedAddress.getState()).isEqualTo("New State");
        verify(addressDao, times(1)).findById(id);
        verify(addressDao, times(1)).save(existingAddress);
    }

    @Test
    public void testUpdateAddress_NotFound() {
        Long id = 1L;
        Address newAddress = new Address();
        when(addressDao.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.updateAddress(id, newAddress))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Address not found with id: " + id);

        verify(addressDao, times(1)).findById(id);
        verify(addressDao, never()).save(any(Address.class));
    }

    @Test
    public void testDeleteAddress() {
        Long id = 1L;
        when(addressDao.existsById(id)).thenReturn(true);

        addressService.deleteAddress(id);

        verify(addressDao, times(1)).existsById(id);
        verify(addressDao, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteAddress_NotFound() {
        Long id = 1L;
        when(addressDao.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> addressService.deleteAddress(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Address not found with id: " + id);

        verify(addressDao, times(1)).existsById(id);
        verify(addressDao, never()).deleteById(id);
    }
}
