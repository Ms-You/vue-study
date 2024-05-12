package com.board.api.service;

import com.board.api.domain.dto.ItemDTO;
import com.board.api.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;


    @Transactional(readOnly = true)
    public List<ItemDTO.ItemResp> getItemList() {
        List<ItemDTO.ItemResp> itemRespList = new ArrayList<>();

        itemRepository.findAll()
                .stream()
                .map(item -> itemRespList.add(new ItemDTO.ItemResp(item.getName())))
                .collect(Collectors.toList());

        return itemRespList;
    }

}