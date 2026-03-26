package com.james.LMS.facade;

import com.james.LMS.request.UpsertNoteRequest;
import com.james.LMS.response.BaseResponse;

public interface NoteFacade {
  BaseResponse<Void> createNote(UpsertNoteRequest upsertNoteRequest);
}
