package com.tuleninov.web.model.captcha;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

/**
 * Record for captcha data.
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponse {

    private boolean success;

    @JsonAlias("error-code")
    private Set<String> errorCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Set<String> errorCode) {
        this.errorCode = errorCode;
    }
}
