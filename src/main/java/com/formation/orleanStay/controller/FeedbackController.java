package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.FeedbackDTO;
import com.formation.orleanStay.models.request.FeedbackSaveRequest;
import com.formation.orleanStay.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("feedback")
public class FeedbackController {
    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FeedbackDTO> findAll(){
        log.debug("Fetching all feedbacks");
        return feedbackService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FeedbackDTO findById(@PathVariable Long id) {
        log.debug("Fetching feedback with id = {}", id);
        return feedbackService.findDTOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackDTO create(@RequestBody FeedbackSaveRequest feedbackSaveRequest) {
        log.debug("Creating new feedback from feedbackSaveRequest :  {}", feedbackSaveRequest);
        return feedbackService.create(feedbackSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FeedbackDTO update(@PathVariable Long id, @RequestBody FeedbackSaveRequest feedbackSaveRequest) {
        log.debug("Updating feedback of id {} with value {}", id, feedbackSaveRequest);
        return feedbackService.update(id, feedbackSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.debug("Deleting feedback of id = {}", id);
        feedbackService.delete(id);
    }
}
