package com.bookdream.sbb.freeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookdream.sbb.DataNotFoundException;
import com.bookdream.sbb.event.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FreeboardService {
	
	private final FreeboardRepository freeboardRepository;
	
    @Autowired
    public FreeboardService(FreeboardRepository freeboardRepository) {
        this.freeboardRepository = freeboardRepository;
    }

    
    public List<Freeboard> findAll() {
        return freeboardRepository.findAll();
    }

    public Freeboard findById(Long id) {
        return freeboardRepository.findById(id).orElse(null);
    }

    public Freeboard save(Freeboard freeboard) {
        freeboard.setCreatedAt(LocalDateTime.now());
        freeboard.setUpdatedAt(LocalDateTime.now());
        return freeboardRepository.save(freeboard);
    }

    public void deleteById(Long id) {
        freeboardRepository.deleteById(id);
    }
}