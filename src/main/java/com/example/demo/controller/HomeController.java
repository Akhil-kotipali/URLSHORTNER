package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Url;
import com.example.demo.redis.RedisRateLimiter;
import com.example.demo.service.UrlService;

import jakarta.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/urlShortner/home")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private UrlService urlService;

    @Autowired
    private RedisRateLimiter rateLimiter;

    @GetMapping("")
    public String home() {
        return "index";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String originalUrl, Model model,
                            HttpServletRequest request) {

        String clientId = request.getRemoteAddr();

        // ✅ Rate limiting
        if (!rateLimiter.isAllowed(clientId)) {
            model.addAttribute("error", "Too many requests. Try later.");
            return "index";
        }

        // ✅ Check if already exists
        Url existing = urlService.findLongUrl(originalUrl);

        Url url;
        if (existing != null) {
            url = existing;
        } else {
            Url newUrl = new Url();
            newUrl.setLongUrl(originalUrl);
            url = urlService.addUrl(newUrl);
        }

        // ✅ Only send short code
        model.addAttribute("shortUrl", url.getShortUrl());

        return "index";
    }
}