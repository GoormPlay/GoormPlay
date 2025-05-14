package com.goormplay.bffservice.bff.service;

import com.goormplay.bffservice.bff.client.AuthClient;
import com.goormplay.bffservice.bff.client.MemberClient;
import com.goormplay.bffservice.bff.client.SubscribeClient;
import com.goormplay.bffservice.bff.client.UIClient;
import com.goormplay.bffservice.bff.dto.Member.MemberProfileDto;
import com.goormplay.bffservice.bff.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BFFService {
    private final AuthClient authClient;
    private final MemberClient memberClient;
    private final SubscribeClient subscribelient;
    private final UIClient uiClient;




    public void join(SignUpRequestDto dto) {
        String memberId = memberClient.signUpMember(dto);
        authClient.signUpAuth(dto, memberId);
        subscribelient.signUpSubscribe(memberId);
        //실패하면 보상 트랜잭션이 필요함
    }


    public MemberProfileDto getMemberProfile(String memberId) {
        memberClient.getMemberProfile(memberId);
        subscribelient.getMemberProfile(memberId);
        uiClient.getMemberProfile(memberId);//이 세 client에서 데이터 각각 받아서
        
        return MemberProfileDto.builder().build();//빌더로 만들어서
    }
}
