package com.board.api.controller;

import com.board.api.domain.dto.ItemDTO;
import com.board.api.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {
    private final ItemService itemService;

    /**
     * 상품 목록 조회
     * @return
     */
    @GetMapping("/api/items")
    public List<ItemDTO.ItemResp> getItemList() {

        return itemService.getItemList();
    }

    /**
     * 이름으로 상품 검색
     * @param name
     * @return
     */
    @GetMapping("/api/items/search")
    public List<ItemDTO.ItemResp> searchItem(@RequestParam(name = "name") String name) {
        return itemService.searchItemList(name);
    }
}
