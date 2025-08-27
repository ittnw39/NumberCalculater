# 길이 분할 계산기 - JavaFX

## 🚀 다운로드

### 최신 버전 (v25.08.27)

#### Windows 실행 파일 (Java 설치 불필요)
- **독립 실행 EXE**: [길이분할계산기_v25.08.27_완전독립실행_Windows.zip](./길이분할계산기_v25.08.27_독립실행_Windows.zip?raw=true)
  - 내용: `LengthCalculator.exe` + JavaFX 런타임
  - 크기: 56.5MB (JavaFX 런타임 포함)
  - **새로운 기능**: 개수 지정 분배 기능 추가

### 이전 버전

#### Windows 실행 파일 (Java 설치 불필요)
- **독립 실행 EXE**: [길이분할계산기_v25.08.17_완전독립실행_Windows.zip](./길이분할계산기_v25.08.17_독립실행_Windows.zip?raw=true)
  - 내용: `LengthCalculator.exe` + JavaFX 런타임
  - 크기: 47.9MB (JavaFX 런타임 포함)
  - **기능**: 기본 분할 및 전체 분배 기능

> 💡 **완전 독립 실행 EXE 파일은 Java/JavaFX 설치 없이 바로 실행 가능합니다!**  
> 📌 **완전 독립 실행**: JavaFX 런타임이 내장되어 있어 어떤 PC에서도 바로 실행됩니다.

---

## 📋 프로젝트 개요

JavaFX를 사용하여 개발된 길이 분할 계산기입니다. 입력된 숫자를 선택한 단위로 나누고, 몫과 나머지를 계산하며, 나머지를 균등하게 분배하는 기능을 제공합니다.

## ✨ 주요 기능

### 1. 기본 계산 기능
- **나머지 계산**: 선택한 단위로 나누어 몫과 나머지 구하기
- **길이 분할**: 선택한 단위로 숫자 분할하기 (1단계)
- **나머지 분배**: 나머지를 균등하게 분배하기 (2단계)
- **개수 지정 분배**: 사용자가 지정한 개수만큼 나머지 분배하기 (2단계 확장)

### 2. 시각적 표현
- **1단계**: 기본 분할 결과를 바 차트로 시각화
  - 파란색 바: 단위 길이
  - 빨간색 바: 나머지
- **2단계**: 나머지 분배 결과를 바 차트로 시각화
  - 파란색 바: 원래 몫
  - 주황색 바: 추가된 나머지
- **비례적 표시**: 바의 길이가 실제 비율에 맞게 표시

### 3. 사용자 정의 기능
- **기본 단위**: 2, 3, 4, 5, 6, 8, 10, 12 선택 가능
- **사용자 지정 단위**: 원하는 단위 직접 입력 가능
- **동시 선택**: 기본 단위와 사용자 지정 단위 동시 사용 가능
- **분배 개수 지정**: 나머지 분배 시 원하는 개수만큼만 분배 가능

### 4. UI/UX 개선사항
- **통일된 폰트**: 모든 텍스트를 맑은 고딕으로 통일
- **균등한 버튼 높이**: 세 버튼이 영역을 균등하게 분할
- **포커스 방지**: 클릭 시 파란색 테두리와 폰트 크기 변화 방지
- **직관적 표시**: "최종값(원래몫+추가된 나머지)" 형태로 표시

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
# 기본 JAR 파일 생성
./gradlew jar

# 완전한 JAR 파일 생성 (모든 의존성 포함)
./gradlew shadowJar
```

### 4. 실행 파일 생성 (Windows)
```bash
# Shadow JAR 파일 생성 (모든 의존성 포함)
./gradlew shadowJar

# JPackage를 사용한 EXE 파일 생성
./gradlew createExe
```

**생성되는 파일:**
- `build/libs/길이분할계산기-25.08.27.jar` - 완전한 JAR 파일 (Java 필요)
- `build/distributions/LengthCalculator/LengthCalculator.exe` - 독립 실행 EXE 파일 (JavaFX 런타임 포함)

> 💡 **완전 독립 실행 EXE 파일은 Java/JavaFX 설치 없이도 독립적으로 실행됩니다!**

## 📱 사용법

### 0. 프로그램 실행
#### Windows 완전 독립 실행 EXE 파일
1. 위에서 ZIP 파일을 다운로드합니다.
2. ZIP 파일을 원하는 위치에 압축 해제합니다.
3. `LengthCalculator.exe` 파일을 더블클릭하여 실행합니다.
4. Java나 JavaFX 설치가 필요하지 않습니다.

### 1. 숫자 입력
- 상단 입력 필드에 나눌 숫자를 입력합니다.

### 2. 단위 선택
- **기본 단위**: 2, 3, 4, 5, 6, 8, 10, 12 중 선택
- **사용자 지정**: 라디오 버튼 선택 후 원하는 단위 입력

### 3. 계산 실행
- **나머지 구하기**: 선택한 단위로 나누어 몫과 나머지 계산
- **1단계 분할하기**: 기본 분할 결과 시각화
- **2단계 전체 분배하기**: 나머지를 몫만큼 모두 분배
- **2단계 개수 지정 분배하기**: 나머지를 지정한 개수만큼만 분배

### 4. 결과 확인
- **나머지 계산**: "1000 ÷ 1500 = 0 ... 1000" 형태로 몫과 나머지 표시
- **1단계**: 파란색 바(단위) + 빨간색 바(나머지)
- **2단계 전체 분배**: 파란색 바(원래 몫) + 주황색 바(추가된 나머지)
- **2단계 개수 지정 분배**: 파란색 바(원래 몫) + 보라색 바(지정된 개수에만 추가된 나머지) + 회색 바(분배되지 않은 단위)

## 🎨 UI 특징

### 반응형 디자인
- 창 크기 변경에 따라 버튼 높이 자동 조정
- 세 버튼이 영역을 1:1:1 비율로 균등 분할

### 시각적 강조
- 모든 텍스트를 맑은 고딕 폰트로 통일
- 중요한 정보(단위, 개수, 나머지)는 굵은 글씨로 표시
- 색상 구분으로 단계별 정보 구분
- 비례적 바 차트로 직관적 이해

### 사용성 개선
- 클릭 시 파란색 테두리와 폰트 크기 변화 방지
- 체크박스와 숫자 간 적절한 간격 유지
- 나머지 숫자를 바의 맨 오른쪽에 표시

## 🔧 기술 스택

- **Java**: 17
- **JavaFX**: 20
- **Gradle**: 8.5
- **아키텍처**: MVC 패턴
- **UI 프레임워크**: JavaFX
- **폰트**: 맑은 고딕 (Malgun Gothic)
- **빌드 도구**: Shadow JAR (의존성 포함), JPackage (네이티브 실행파일)

## 📦 배포 및 설치

### Windows 실행 파일
1. **독립 실행 EXE**: `LengthCalculator.exe`
   - JavaFX 런타임이 내장되어 있어 별도 설치 불필요
   - 폴더 전체를 복사하여 배포 가능
   - 어떤 Windows PC에서도 바로 실행 가능

2. **JAR 파일**: `길이분할계산기-25.08.27.jar`
   - Java 11 이상이 설치된 환경에서 실행
   - `java -jar 길이분할계산기-25.08.27.jar` 명령으로 실행

### 시스템 요구사항
- **EXE 파일**: Windows 10/11 (64비트)
- **JAR 파일**: Java 11+ 설치된 모든 운영체제 