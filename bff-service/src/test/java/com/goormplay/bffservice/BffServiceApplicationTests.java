package com.goormplay.bffservice;

import com.goormplay.bffservice.bff.service.BFFService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Map;

@ExtendWith(MockitoExtension.class) // Mockito를 JUnit 5와 연동
class BFFServiceTest {

	@Mock // KafkaTemplate을 모킹
	private KafkaTemplate<String, String> mockKafkaTemplate;

	@Mock // ObjectMapper를 모킹 (혹은 실제 인스턴스 사용도 가능)
	private ObjectMapper mockObjectMapper;

	@Mock // BCryptPasswordEncoder를 모킹
	private BCryptPasswordEncoder mockBCryptPasswordEncoder;

	@InjectMocks // mock 객체들을 주입받을 대상 (테스트 대상)
	private BFFService bffService;

	// 테스트에 사용할 사용자 정보
	private String testUsername = "testUser";
	private String testPassword = "plainTextPassword123";
	private String testGender = "MALE";
	private int testAge = 25;
	private String testHashedPassword = "hashedPasswordABC"; // 해싱된 비밀번호 모킹 값

	@BeforeEach
	void setUp() throws JsonProcessingException {
		// Mocking setup:
		// BCryptPasswordEncoder.encode()가 호출될 때 미리 정의된 해싱된 비밀번호 반환
		when(mockBCryptPasswordEncoder.encode(testPassword)).thenReturn(testHashedPassword);

		// ObjectMapper.writeValueAsString()이 호출될 때 어떤 문자열이든 반환하도록 설정
		// 실제 테스트에서는 캡처된 Map을 검증하는 것이 더 중요
		when(mockObjectMapper.writeValueAsString(any(Map.class))).thenReturn("{\"some\":\"json\"}");
		// (선택 사항) KafkaTemplate.send() 호출 시 반환값 모킹 (CompletableFuture 반환하므로 복잡할 수 있음)
		// 여기서는 send() 호출 여부만 검증하므로, 반환값 모킹은 생략 가능
	}

	@Test
	@DisplayName("회원가입 요청 시 Kafka 메시지가 올바르게 전송되어야 한다")
	void testJoinSendsCorrectKafkaMessage() throws JsonProcessingException {
		// When
		bffService.join(testUsername, testPassword, testGender, testAge);

		// Then
		// 1. bCryptPasswordEncoder.encode()가 평문 비밀번호로 한 번 호출되었는지 검증
		verify(mockBCryptPasswordEncoder, times(1)).encode(testPassword);

		// 2. ObjectMapper.writeValueAsString()이 올바른 데이터 맵으로 한 번 호출되었는지 검증
		// ArgumentCaptor를 사용하여 writeValueAsString에 전달된 Map을 캡처
		ArgumentCaptor<Map<String, Object>> mapCaptor = ArgumentCaptor.forClass(Map.class);
		verify(mockObjectMapper, times(1)).writeValueAsString(mapCaptor.capture());

		Map<String, Object> capturedMap = mapCaptor.getValue();
		assertThat(capturedMap).isNotNull();
		assertThat(capturedMap.get("username")).isEqualTo(testUsername);
		assertThat(capturedMap.get("password")).isEqualTo(testHashedPassword); // 해싱된 비밀번호가 포함되었는지 확인
		assertThat(capturedMap.get("gender")).isEqualTo(testGender);
		assertThat(capturedMap.get("age")).isEqualTo(testAge);
		assertThat(capturedMap).containsKey("timestamp"); // timestamp 필드 존재 여부 확인

		// 3. KafkaTemplate.send()가 올바른 토픽, 키, JSON 메시지로 한 번 호출되었는지 검증
		ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> jsonCaptor = ArgumentCaptor.forClass(String.class); // ObjectMapper가 반환할 JSON 문자열 캡처

		verify(mockKafkaTemplate, times(1))
				.send(topicCaptor.capture(), keyCaptor.capture(), jsonCaptor.capture());

		assertThat(topicCaptor.getValue()).isEqualTo("user-join-logs");
		assertThat(keyCaptor.getValue()).isEqualTo(testUsername); // 키로 username이 사용되었는지 확인
		assertThat(jsonCaptor.getValue()).isEqualTo("{\"some\":\"json\"}"); // mockObjectMapper가 반환하도록 설정한 값
	}

	@Test
	@DisplayName("JsonProcessingException 발생 시 예외가 전파되거나 적절히 처리되어야 한다")
	void testJoinHandlesJsonProcessingException() throws JsonProcessingException {
		// Given
		// ObjectMapper.writeValueAsString() 호출 시 JsonProcessingException 발생하도록 모킹
		when(mockObjectMapper.writeValueAsString(any(Map.class)))
				.thenThrow(new JsonProcessingException("Test JSON error") {}); // 익명 클래스 사용

		// When & Then
		// 예외가 발생하더라도 서비스 로직이 멈추지 않고 예외가 처리되는지 확인
		// 현재 코드에서는 catch 블록에서 printStackTrace만 하고 있어,
		// 이 테스트는 예외가 발생했음을 검증하기 어렵습니다.
		// 실제 프로덕션 코드에서는 예외 로깅 및 특정 예외를 throw 하도록 수정하는 것이 좋습니다.

		// 현재 구현대로라면 예외가 발생해도 메서드 자체는 정상 종료됩니다.
		// 따라서 `assertThatThrownBy` 대신 단순히 호출하고 `verify`로 다른 부분이 호출 안 됐는지 확인
		bffService.join(testUsername, testPassword, testGender, testAge);

		// ObjectMapper 예외 발생 시 KafkaTemplate.send는 호출되지 않음을 검증
		verify(mockKafkaTemplate, never()).send(anyString(), anyString(), anyString());
		// console output으로 에러가 찍혔는지 확인해야 하지만, 단위 테스트에서는 어려움.
		// 대신 log.error() 사용 시 logback/log4j 모킹하여 확인 가능
	}
}