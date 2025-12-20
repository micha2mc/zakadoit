package com.uah.tfm.zakado.zkd.service.impl;

import com.uah.tfm.zakado.zkd.data.entity.Area;
import com.uah.tfm.zakado.zkd.data.repository.AreaRepository;
import com.uah.tfm.zakado.zkd.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;


    public List<Area> findAllArea() {
        return areaRepository.findAll();
    }
}
