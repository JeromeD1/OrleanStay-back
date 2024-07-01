package com.formation.orleanStay.mapper;

import com.formation.orleanStay.models.DTO.FeedbackAnswerDTO;
import com.formation.orleanStay.models.entity.FeedbackAnswer;
import com.formation.orleanStay.models.request.FeedbackAnswerSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FeedbackAnswerMapper {
    /**
     * Mapping commentId to the id of {@link FeedbackAnswer} entity
     *
     * @param commentId to map
     * @return the {@link FeedbackAnswer} with the id of the feedbackAnswerId value
     */
    default FeedbackAnswer fromFeedbackAnswerId(Long commentId) {
        if(commentId == null) {
            return null;
        }

        final FeedbackAnswer comment = new FeedbackAnswer();
        comment.setId(commentId);
        return comment;
    }


    /**
     * Mapping {@link FeedbackAnswer} to {@link FeedbackAnswerDTO}
     *
     * @param comment to map
     * @return the {@link FeedbackAnswerDTO} mapped from {@link FeedbackAnswer}
     */
    FeedbackAnswerDTO toFeedbackAnswerDTO(FeedbackAnswer comment);



    /**
     * Mapping {@link FeedbackAnswerSaveRequest} to {@link FeedbackAnswer}
     *
     * @param feedbackAnswerSaveRequest to map
     * @return the {@link FeedbackAnswer} mapped from {@link FeedbackAnswerSaveRequest}
     */
    FeedbackAnswer fromFeedbackAnswerSaveRequest(FeedbackAnswerSaveRequest feedbackAnswerSaveRequest);


    /**
     * Override {@link FeedbackAnswer} with {@link FeedbackAnswerSaveRequest}
     *
     * @param saveRequest the {@link FeedbackAnswerSaveRequest} used to override {@link FeedbackAnswer}
     * @param comment {@link FeedbackAnswer} to be overwritten
     */
    @Mapping(source = "saveRequest.commentAnswer", target = "commentAnswer")
    void overrideFromFeedbackAnswerSaveRequest(FeedbackAnswerSaveRequest saveRequest, @MappingTarget FeedbackAnswer comment);

}
