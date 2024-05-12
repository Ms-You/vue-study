package com.board.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/api/items")
    public List<String> test() {
        List<String> items = new ArrayList<>();
        items.add("alpa");
        items.add("beta");
        items.add("gamma");

        return items;
    }
}
