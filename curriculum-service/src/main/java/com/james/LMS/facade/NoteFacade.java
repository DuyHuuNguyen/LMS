package com.james.LMS.facade;

import com.james.LMS.request.NoteCriteria;
import com.james.LMS.request.UpsertNoteRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.NoteResponse;
import com.james.LMS.response.PaginationResponse;

public interface NoteFacade {
  BaseResponse<Void> createNote(UpsertNoteRequest upsertNoteRequest);

    BaseResponse<PaginationResponse<NoteResponse>> findAllNote(NoteCriteria noteCriteria);
}
