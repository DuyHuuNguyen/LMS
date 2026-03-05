package com.james.LMS.enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    IMAGE("image"),
    VIDEO("video");
    private final String type;

    public boolean isImage() {
        return this == IMAGE;
    }
}