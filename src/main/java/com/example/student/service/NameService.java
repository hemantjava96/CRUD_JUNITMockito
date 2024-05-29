package com.example.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.dao.NameDao;
import com.example.student.entity.Name;

@Service
public class NameService {

	@Autowired
    private NameDao nameDao;

	public Name save(Name name) {
		return nameDao.save(name);
	}
	
    public List<Name> getAllNames() {
        return nameDao.findAll();
    }

    public Optional<Name> getNameById(Long id) {
        return nameDao.findById(id);
    }

    public Name createName(Name name) {
        return nameDao.save(name);
    }

    public Name updateName(Long id, Name newName) {
        return nameDao.findById(id)
                .map(name -> {
                    name.setFname(newName.getFname());
                    name.setLname(newName.getLname());
                    name.setMname(newName.getMname());
                    return nameDao.save(name);
                })
                .orElseThrow(() -> new RuntimeException("Name not found with id: " + id));
    }

    public void deleteName(Long id) {
        if (nameDao.existsById(id)) {
            nameDao.deleteById(id);
        } else {
            throw new RuntimeException("Name not found with id: " + id);
        }
    }
}
