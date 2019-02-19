package com.rongshun.util;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/12 15:08
 * Modified By：
 */
public class EmptyStringToNullRequestDataBinder extends ExtendedServletRequestDataBinder {
    public EmptyStringToNullRequestDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);
        for (PropertyValue propertyValue : mpvs.getPropertyValueList()) {
            if (propertyValue.getValue().equals(""))
                propertyValue.setConvertedValue(null);
        }
    }
}
