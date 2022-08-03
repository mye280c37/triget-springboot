package com.triget.application.web;

import com.triget.application.service.ProductListService;
import com.triget.application.web.dto.ProductListRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductListController {

    private ProductListService productListService;

    @GetMapping("/product/list")
    public String returnProductList(@RequestBody ProductListRequestDto dto) {
        return productListService.save(dto);
    }

}
