# Simple Calculator

[English](README.md)

사칙연산을 하는 안드로이드 계산기 앱이다. 표시창은 `AppCompatEditText` 를 상속한 커스텀 뷰로 만들어 계산 상태를 그 안에 두고 연산자 버튼은 `AppCompatButton` 을 상속해 버튼마다 자기 연산을 담게 했다.

<p>
  <img src="docs/screenshots/screenshot-1.png" width="336" alt="계산기 화면">
</p>


## 사용 방법

Android Studio 로 `source/` 를 열어 빌드한다. Android Gradle 플러그인 3.0.1, Gradle 4.1, `compileSdkVersion 26` 프로젝트이고 `minSdkVersion` 은 22(Android 5.1)다.

| 버튼 | 동작 |
|---|---|
| 0 부터 9 | 표시창의 숫자 뒤에 붙인다 |
| `+` `-` `*` `/` | 지금 숫자를 왼쪽 피연산자로 넘기고 표시창을 비운다 |
| `=` | 연산 결과를 표시창에 띄운다 |
| AC | 0 으로 초기화한다 |
| C | 마지막 자리를 지운다 |

한 번에 연산 하나씩 계산한다. 연산자를 누른 뒤 `=` 전에 다른 연산자를 누르면 "'=' 버튼을 눌러 연산해주세요!" 토스트가 뜬다. 0 으로 나누면 "0으로 나눌 수 없습니다!" 가 뜨고 표시창은 그대로다. 숫자는 기기 로케일에 맞춰 천 단위 구분 기호를 붙여 보여 준다.


## 구현 원리

**연산은 버튼이 들고 있다.** `BtnOperator` 는 `OnCalculateListener` 인터페이스를 하나 받아 두는 버튼이다. `MainActivity` 가 시작할 때 연산자 버튼 네 개의 글자를 보고 각자에 맞는 리스너를 한 번 붙이면, 그 뒤로는 어느 버튼이 눌렸는지 따지지 않고 등록된 버튼의 `calculate` 만 부른다.

```java
public Number calculate(Number number1, Number number2)
{
    if (calculateListener != null) {
        return calculateListener.onCalculate(number1, number2);
    }
    else {
        return Double.NaN;
    }
}
```

**계산 상태는 표시창에 있다.** `EdtCalculator` 는 왼쪽 피연산자와 눌린 연산자 버튼을 필드에 저장한다. 연산자를 누르면 지금 숫자를 왼쪽 피연산자로 옮기고 그 버튼을 기억한다. `=` 를 누르면 기억한 버튼에 두 수를 넘기는데 결과가 `NaN` 이 아닐 때만 결과로 초기화하므로 0 으로 나누기 같은 실패는 화면을 바꾸지 않는다.

**표시는 `NumberFormat` 으로 서식을 입힌다.** 숫자를 덧붙이거나 지울 때마다 서식 없는 숫자 문자열로 되돌린 뒤 고치고 다시 `NumberFormat.format` 으로 표시한다. 빈 문자열은 0 으로 읽는다.

**키패드는 비율로 배치한다.** `ConstraintLayout` 에 가로 가이드라인 둘(`0.3`, `0.88`)을 두어 위 30% 는 표시창, 그 아래는 숫자와 연산자, 맨 아래 12% 는 `=` 버튼이 차지한다.


## 파일

| 경로 | 내용 |
|---|---|
| `source/app/src/main/java/com/jungu/simplecalculator/MainActivity.java` | 버튼 연결과 연산 리스너 등록 |
| `source/app/src/main/java/com/jungu/simplecalculator/EdtCalculator.java` | 표시창 겸 계산 상태 |
| `source/app/src/main/java/com/jungu/simplecalculator/BtnOperator.java` | 연산을 들고 있는 연산자 버튼 |
| `source/app/src/main/res/layout/activity_main.xml` | 화면 배치 |
| `source/app/src/main/res/values/strings.xml` | 앱 이름과 토스트 문구 |
| `docs/screenshots/` | 스크린샷 |


## 라이선스

zlib 라이선스다. 자세한 내용은 [LICENSE](LICENSE) 에 있다.
