package com.example.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.dao.AddressDao;
import com.example.student.entity.Address;

@Service
public class AddressService {

	@Autowired
    private AddressDao addressDao;
	
	public Address save(Address address) {
		return addressDao.save(address);
	}

    public List<Address> getAllAddresses() {
        return addressDao.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return addressDao.findById(id);
    }

    public Address createAddress(Address address) {
        return addressDao.save(address);
    }

    public Address updateAddress(Long id, Address newAddress) {
        return addressDao.findById(id)
                .map(address -> {
                    address.setAddress(newAddress.getAddress());
                    address.setDistrict(newAddress.getDistrict());
                    address.setPincode(newAddress.getPincode());
                    address.setState(newAddress.getState());
                    return addressDao.save(address);
                })
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
    }

    public void deleteAddress(Long id) {
        if (addressDao.existsById(id)) {
            addressDao.deleteById(id);
        } else {
            throw new RuntimeException("Address not found with id: " + id);
        }
    }
}
