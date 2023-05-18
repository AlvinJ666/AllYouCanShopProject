package com.allyoucanshop.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionCodes {
    ACTION_PROHIBITED(400),
    ACTION_ALLOWED(200),
    SKIPPED_ACTION(500);
    private final int code;

    public static int getActionCode(Boolean actionAllowed) {
        if (actionAllowed == null) {
            return SKIPPED_ACTION.getCode();
        }
        return actionAllowed ? ACTION_ALLOWED.getCode() : ACTION_PROHIBITED.getCode();
    }
}
