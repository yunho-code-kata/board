package com.example.yunhoborad.common.auth;

import com.example.yunhoborad.Member.domain.Member;
import com.example.yunhoborad.Member.repository.MemberRepository;
import com.example.yunhoborad.common.exception.AuthorizationException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;
    private final AuthContext authContext;

    public MemberArgumentResolver(MemberRepository memberRepository, AuthContext authContext) {
        this.memberRepository = memberRepository;
        this.authContext = authContext;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Member.class)
                && parameter.hasParameterAnnotation(Authorization.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Long possibleMemberId = authContext.getMemberId();
        return memberRepository.findById(possibleMemberId)
                .orElseThrow(() -> new AuthorizationException("인증된 사용자가 아닙니다.", String.valueOf(possibleMemberId)));
    }
}
