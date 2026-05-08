package com.backend.apiJogos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.apiJogos.dtos.RatingDto;
import com.backend.apiJogos.services.interfaces.RatingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid RatingDto ratingDto, BindingResult bd) {
        if(bd.hasErrors()){
            return ResponseEntity.badRequest().body(bd.getAllErrors());
        }
        RatingDto ratingSalvo = ratingService.criar(ratingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingSalvo);
    }

}
