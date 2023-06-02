package com.tuleninov.web.config.mvc.captcha;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Configuration
@ConfigurationProperties(prefix = "recaptcha")
public class CaptchaProperties {

    @NotBlank(message = "url must not be empty")
    private String url;

    @NotBlank(message = "secret must not be empty")
    private String secret;

    @NotBlank(message = "sitekey must not be empty")
    @JsonAlias("sitekey;")
    private String siteKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }
}
