package com.gaulab.weshoot.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dexxczp8p",
                "api_key", "165133278369372",
                "api_secret", "GwSc7zegY8PhhJdzKWF8UEXSlyA"
        ));
    }

}
