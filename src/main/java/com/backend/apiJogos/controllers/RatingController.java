package com.backend.apiJogos.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.apiJogos.dtos.RatingDto;
import com.backend.apiJogos.services.interfaces.RatingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public void criar(@RequestBody RatingDto ratingDto) {
        ratingService.criar(ratingDto);
    }

}
