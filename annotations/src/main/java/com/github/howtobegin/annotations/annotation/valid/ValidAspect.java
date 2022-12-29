package com.github.howtobegin.annotations.annotation.valid;

import com.github.howtobegin.annotations.common.ParameterTools;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hello
 * @date 2022/12/29 17:56
 */
@Slf4j
@Aspect
@Component
public class ValidAspect {
    @Autowired
    private LocalValidatorFactoryBean validator;
    @Autowired
    private ParameterTools parameterTools;

    @Around(value = "@annotation(valid)")
    public Object lock(ProceedingJoinPoint pjp, Valid valid) throws Throwable {
        Object[] args = pjp.getArgs();
        String[] parameterNames = parameterTools.getParameterNames(pjp);
        List<String> errorMessages = Lists.newArrayList();
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            Errors errors = new BeanPropertyBindingResult(o, parameterNames[i]);
            validator.validate(o, errors);
            if (!CollectionUtils.isEmpty(errors.getAllErrors())) {
                StringBuilder builder = new StringBuilder();
                builder.append(errors.getObjectName());
                builder.append(":");
                builder.append(errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")));

                errorMessages.add(builder.toString());
            }
        }
        if (!CollectionUtils.isEmpty(errorMessages)) {
            Class<? extends Throwable> exception = valid.exception();
            Constructor<? extends Throwable> constructor = exception.getConstructor(String.class);
            throw constructor.newInstance(errorMessages.stream().collect(Collectors.joining(";")));
        }
        return pjp.proceed();
    }
}
