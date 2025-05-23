package com.goormplay.memberservice.member.service;

import com.goormplay.memberservice.member.client.ContentClient;
import com.goormplay.memberservice.member.client.SubscribeClient;
import com.goormplay.memberservice.member.client.UserInteractionClient;
import com.goormplay.memberservice.member.dto.ContentIdsRequest;
import com.goormplay.memberservice.member.dto.Member.*;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import com.goormplay.memberservice.member.entity.Member;
import com.goormplay.memberservice.member.exception.Member.MemberException;
import com.goormplay.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.goormplay.memberservice.member.exception.Member.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.goormplay.memberservice.member.exception.Member.MemberExceptionType.SIGN_UP_FAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SubscribeClient subscribeClient;
    private final UserInteractionClient userInteractionClient;
    private final ContentClient contentClient;

    @Override
    @Transactional
    public String joinMember(SignUpRequestDto dto) {
        log.info("Member Service : 회원가입");
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
            log.error("member 등록 실패");
            subscribeClient.deleteSubscribe(dto.getUsername());
            throw new MemberException(SIGN_UP_FAIL);
        }

    }

    @Transactional
    public void deleteMember(String username) {
        log.info("Member Service : 회원가입 보상 트랜잭션 유저 삭제");
        memberRepository.findByUsername(username)
                .ifPresent(memberRepository::delete);
    }


    //현재는 두 서비스가 직접 연결
    //비동기로 처리해서 응답 시간을 줄이면 좋음
    @Override
    @Transactional(readOnly = true)
    public MemberProfileDto findMemberProfile(String memberId) {
        log.info("Member Service : 멤버 프로필 조회");
        // 1. 회원 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        // 2. 구독 정보 조회
        SubScribeStatusDto subScribeStatusDto = subscribeClient.getSubScribeStatus(memberId);

        // 3. 좋아요한 컨텐츠 조회
        List<String> likedContentsIds = userInteractionClient.getLikedContentsId(memberId);
        List<VideoDto> likedContents = likedContentsIds.isEmpty()
                ? Collections.emptyList()
                : contentClient.getContentCardsByContentIds(new ContentIdsRequest(likedContentsIds));

        // 4. DTO 구성
        return MemberProfileDto.builder()
                .username(member.getUsername())
                .gender(member.getGender())
                .age(member.getAge())
                .subscription_start_date(subScribeStatusDto.getSubscription_start_date())
                .subscription_end_date(subScribeStatusDto.getSubscription_end_date())
                .isCancelScheduled(subScribeStatusDto.getIsCancelScheduled())
                .isSubscribed(subScribeStatusDto.getIsSubscribed())
                .liked(likedContents) // 좋아요한 컨텐츠 목록 추가
                .build();
    }
}
