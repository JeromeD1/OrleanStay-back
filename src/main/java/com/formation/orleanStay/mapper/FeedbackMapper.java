package com.formation.orleanStay.mapper;

import com.formation.orleanStay.models.DTO.FeedbackDTO;
import com.formation.orleanStay.models.entity.Feedback;
import com.formation.orleanStay.models.request.FeedbackSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FeedbackMapper {
    /**
     * Mapping commentId to the id of {@link Feedback} entity
     *
     * @param commentId to map
     * @return the {@link Feedback} with the id of the feedbackId value
     */
    default Feedback fromFeedbackId(Long commentId) {
        if(commentId == null) {
            return null;
        }

        final Feedback comment = new Feedback();
        comment.setId(commentId);
        return comment;
    }


    /**
     * Mapping {@link Feedback} to {@link FeedbackDTO}
     *
     * @param comment to map
     * @return the {@link FeedbackDTO} mapped from {@link Feedback}
     */
    FeedbackDTO toFeedbackDTO(Feedback comment);



    /**
     * Mapping {@link FeedbackSaveRequest} to {@link Feedback}
     *
     * @param feedbackSaveRequest to map
     * @return the {@link Feedback} mapped from {@link FeedbackSaveRequest}
     */
    Feedback fromFeedbackSaveRequest(FeedbackSaveRequest feedbackSaveRequest);


    /**
     * Override {@link Feedback} with {@link FeedbackSaveRequest}
     *
     * @param saveRequest the {@link FeedbackSaveRequest} used to override {@link Feedback}
     * @param comment {@link Feedback} to be overwritten
     */
    @Mapping(source = "saveRequest.comment", target = "comment")
    void overrideFromFeedbackSaveRequest(FeedbackSaveRequest saveRequest, @MappingTarget Feedback comment);

}
