package com.pyhu.northernplanet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pyhu.northernplanet.api.request.PresentationPostRequest;
import com.pyhu.northernplanet.api.service.PresentationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api
@RestController
@CrossOrigin
@RequestMapping("/presentaion")
@RequiredArgsConstructor
public class PresentationController {
  private PresentationService presentationService;

  @PostMapping("/")
  public ResponseEntity<Integer> createPresentation(
      @ModelAttribute PresentationPostRequest presentationPostRequest) {
    int status = presentationService.createPresentation(presentationPostRequest);
    switch (status) {
      case 0:
        return new ResponseEntity<Integer>(HttpStatus.OK);
      default:
        return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
