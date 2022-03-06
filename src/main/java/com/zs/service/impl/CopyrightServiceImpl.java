package com.zs.service.impl;

import com.zs.mapper.CopyrightMapper;
import com.zs.pojo.Copyright;
import com.zs.service.CopyrightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created by zs on 2022/3/4.
 */
@Service
public class CopyrightServiceImpl implements CopyrightService {

    @Autowired
    private CopyrightMapper copyrightMapper;

    @Override
    public List<Copyright> listCopyright() {
        return copyrightMapper.listCopyright();
    }
}
