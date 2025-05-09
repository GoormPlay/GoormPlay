package com.goormplay.memberservice.member.service;

import com.goormplay.memberservice.member.client.SubscribeClient;
import com.goormplay.memberservice.member.dto.Member.MemberProfileDto;
import com.goormplay.memberservice.member.dto.Member.SubScribeStatusDto;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import com.goormplay.memberservice.member.entity.Member;
import com.goormplay.memberservice.member.exception.Member.MemberException;
import com.goormplay.memberservice.member.repository.MemberRepository;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.goormplay.memberservice.member.exception.Member.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.goormplay.memberservice.member.exception.Member.MemberExceptionType.SIGN_UP_FAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SubscribeClient subscribeClient;

    @Override
    @Transactional
    public String joinMember(SignUpRequestDto dto) {
        log.info("Member Service 회원가입 시작");
        try {
            Member member = memberRepository.save(Member.builder().
                    id(UUID.randomUUID().toString()).
                    username(dto.getUsername()).
                    gender(dto.getGender()).
                    age(dto.getAge()).
                    build());
            log.info("member 등록 완료");
            subscribeClient.joinSubscribe(member.getId());
            log.info("subscribe 등록 완료");
            return member.getId();
        } catch (Exception e) {
            // 실패 시 보상 트랜잭션 실행
            subscribeClient.deleteSubscribe(dto.getUsername());
            throw new MemberException(SIGN_UP_FAIL);
        }

    }

    public void deleteMember(String username) {
        log.info("유저 삭제 시작");
        memberRepository.findByUsername(username)
                .ifPresent(memberRepository::delete);
    }


    //현재는 두 서비스가 직접 연결
    //비동기로 처리해서 응답 시간을 줄이면 좋음
    @Override
    @Transactional(readOnly = true)
    public MemberProfileDto findMemberProfile(String memberId) {
        SubScribeStatusDto subScribeStatusDto = subscribeClient.getSubScribeStatus(memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
        return MemberProfileDto.builder().
                username(member.getUsername()).
                gender(member.getGender()).
                age(member.getAge()).
                subscription_start_date(subScribeStatusDto.getSubscription_start_date()).
                subscription_end_date(subScribeStatusDto.getSubscription_end_date()).
                is_cancel_scheduled(subScribeStatusDto.getIs_cancel_scheduled()).
                build();
    }
}
