package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.FeedbackDTO;
import com.formation.orleanStay.models.request.FeedbackAnswerSaveRequest;
import com.formation.orleanStay.service.FeedbackAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/feedbackAnswer")
public class FeedbackAnswerController {

    final private FeedbackAnswerService feedbackAnswerService;

    public FeedbackAnswerController(FeedbackAnswerService feedbackAnswerService) {
        this.feedbackAnswerService = feedbackAnswerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackDTO create(@RequestBody FeedbackAnswerSaveRequest feedbackAnswerSaveRequest) {
        log.info("Creating new feedbackAnswer from feedbackAnswerSaveRequest :  {}", feedbackAnswerSaveRequest);
        return feedbackAnswerService.create(feedbackAnswerSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FeedbackDTO update(@PathVariable Long id, @RequestBody FeedbackAnswerSaveRequest feedbackAnswerSaveRequest) {
        log.info("Updating feedbackAnswer of id {} with value {}", id, feedbackAnswerSaveRequest);
        return feedbackAnswerService.update(id, feedbackAnswerSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Deleting feedbackAnswer of id = {}", id);
        feedbackAnswerService.delete(id);
    }
}
