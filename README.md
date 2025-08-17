# Number Calculator - JavaFX 치수 재고 확인 계산기

## 📋 프로젝트 개요

JavaFX를 사용하여 개발된 치수 재고 확인 계산기입니다. 입력된 숫자를 선택한 단위로 나누고, 나머지를 분배하는 기능을 제공합니다.

## ✨ 주요 기능

### 1. 기본 계산 기능
- **나머지 계산**: 선택한 단위로 나머지 구하기
- **길이 분할**: 선택한 단위로 숫자 분할하기
- **나머지 분배**: 나머지를 균등하게 분배하기

### 2. 시각적 표현
- **1단계**: 기본 분할 결과를 바 차트로 시각화
- **2단계**: 나머지 분배 결과를 바 차트로 시각화
- **비례적 표시**: 바의 길이가 실제 비율에 맞게 표시

### 3. 사용자 정의 기능
- **기본 단위**: 1400, 1500, 1600, 1700, 1800 선택 가능
- **사용자 지정 단위**: 원하는 단위 직접 입력 가능
- **동시 선택**: 기본 단위와 사용자 지정 단위 동시 사용 가능

## 🏗️ 아키텍처

### MVC 패턴 적용
- **Model**: `CalculationResult` - 계산 결과 데이터
- **View**: UI 컴포넌트들 (`NumberCalculatorApp`, `ResultDisplayComponent`, `NumberSelectionComponent`, `LengthVisualizationComponent`)
- **Controller**: `CalculatorController` - 사용자 입력 처리 및 비즈니스 로직 조정

### 컴포넌트 구조
```
src/main/java/org/example/
├── NumberCalculatorApp.java          # 메인 애플리케이션
├── controller/
│   └── CalculatorController.java     # 컨트롤러
├── model/
│   └── CalculationResult.java        # 데이터 모델
├── service/
│   └── CalculationService.java       # 비즈니스 로직
└── ui/
    ├── ResultDisplayComponent.java   # 결과 표시 컴포넌트
    ├── NumberSelectionComponent.java # 숫자 선택 컴포넌트
    └── LengthVisualizationComponent.java # 길이 시각화 컴포넌트
```

## 🚀 실행 방법

### 1. 개발 환경 요구사항
- JDK 17 이상
- Gradle 8.5 이상

### 2. 빌드 및 실행
```bash
# 프로젝트 클론
git clone https://github.com/ittnw39/NumberCalculater.git
cd NumberCalculater

# Gradle 빌드
./gradlew build

# 애플리케이션 실행
./gradlew run
```

### 3. JAR 파일 생성
```bash
# JAR 파일 생성
./gradlew jar
```

## 📱 사용법

### 1. 숫자 입력
- 상단 입력 필드에 나눌 숫자를 입력합니다.

### 2. 단위 선택
- **기본 단위**: 1400, 1500, 1600, 1700, 1800 중 선택
- **사용자 지정**: 라디오 버튼 선택 후 원하는 단위 입력

### 3. 계산 실행
- **나머지 구하기**: 선택한 단위로 나머지만 계산
- **숫자 분할하기**: 1단계 - 기본 분할 결과 시각화
- **나머지 분배하기**: 2단계 - 나머지 분배 결과 시각화

### 4. 결과 확인
- **1단계**: 파란색 바(단위) + 빨간색 바(나머지)
- **2단계**: 초록색 바(분배된 단위)

## 🎨 UI 특징

### 반응형 디자인
- 창 크기 변경에 따라 버튼 높이 자동 조정
- 텍스트 크기 최적화로 가독성 향상

### 시각적 강조
- 중요한 정보(단위, 개수, 나머지)는 큰 글씨로 표시
- 색상 구분으로 단계별 정보 구분
- 비례적 바 차트로 직관적 이해

## 🔧 기술 스택

- **Java**: 17
- **JavaFX**: 20
- **Gradle**: 8.5
- **아키텍처**: MVC 패턴
- **UI 프레임워크**: JavaFX

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 연락처

프로젝트 링크: [https://github.com/ittnw39/NumberCalculater](https://github.com/ittnw39/NumberCalculater) 