package com.gec.web.service.impl;

import com.gec.web.mapper.ItemsMapper;
import com.gec.web.pojo.Items;
import com.gec.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Override
    public List<Items> queryItems() {
        return itemsMapper.selectByExample(null);
    }

    @Override
    public void addItems(Items items) {
        itemsMapper.insert(items);
    }
}
