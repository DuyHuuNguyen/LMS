package com.james.LMS.util;

import com.james.LMS.config.SecurityUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUserDetailsUtil {
    public static  SecurityUserDetails PRINCIPAL = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
}
