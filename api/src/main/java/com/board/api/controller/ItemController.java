package com.board.api.controller;

import com.board.api.domain.dto.ItemDTO;
import com.board.api.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/api/items")
    public List<ItemDTO.ItemResp> getItemList() {

        return itemService.getItemList();
    }
}
