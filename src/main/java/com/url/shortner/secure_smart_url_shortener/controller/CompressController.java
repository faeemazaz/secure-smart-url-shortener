package com.url.shortner.secure_smart_url_shortener.controller;

import com.url.shortner.secure_smart_url_shortener.dto.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/logic")
public class CompressController {

    @GetMapping("/compress")
    public ResultResponse compress(@RequestParam String input) {
        StringBuilder sb = new StringBuilder();
        //ababcc
        char prev = input.charAt(0);
        int count = 1;

        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == prev) {
                count++;
            } else {
                // pre b
                // count 1
                sb.append(prev);
                sb.append(count);
                prev = input.charAt(i);
                count = 1;
            }
        }
        sb.append(prev).append(count);

        return new ResultResponse(sb.toString());
    }
}
