# 웹툰 앱 클론코딩

## 시연영상

<img src="https://velog.velcdn.com/images/neoseurae12/post/224f1007-5d25-4f4f-84b8-4285b5dab94b/image.gif" alt="시연영상 gif" width="350" />

## 주요 학습내용

1. WebView 의 사용
2. ViewPager2 의 사용
   1. ViewPager2 를 Fragment 와 함께 쓰기
   2. TabLayoutMediator 를 통해 TabLayout 과 함께 쓰기
3. Fragment
4. SharedPreference
5. Dialog

### Fragment

[프래그먼트](https://developer.android.com/guide/fragments?hl=ko)

#### Android 4대 컴포넌트

- Activity
- Service
- BroadCast Receiver
- Content Provider

#### Activity

- 사용자와 상호작용하는 화면
- 단일 독립 실행형 모듈 (Activity 끼리는 독립적)
- 재사용 가능

#### Fragment

여러 개의 프래그먼트를 하나의 액티비티에 결합하여 창이 여러 개인 UI를 빌드할 수 있으며, 하나의 프래그먼트를 여러 액티비티에서 **재사용**할 수 있다.
프래그먼트는 액티비티의 **모듈식 섹션**이라고 생각하면 된다.

이는 **자체적인 수명 주기**를 가지고, 자체 입력 이벤트를 수신하고, 액티비티 실행 중에 추가 및 삭제가 가능하다.

- Activity 에 결합하여 → 독립적으로 사용할 수는 없음.
- 자체적인 수명주기를 가지고 → Activity 와는 별개의 수명주기를 가짐.

### Viewpager2

[ViewPager2로 프래그먼트 간 슬라이드](https://developer.android.com/training/animation/screen-slide-2?hl=ko)

- 하나의 전체화면에서 다른 전체화면으로 전환할 때 사용
- 내부 구현을 RecyclerView 로 이루어짐

## 웹툰 앱

- ViewPager2 를 이용해 N개의 Fragment 를 구성한다.
- 각 Fragment 는 WebView 를 전체화면으로 구성한다.
- TabLayout 과 ViewPager2 를 연동한다.
- Tab 이름을 동적으로 바꿀 수 있다.
- 웹툰의 마지막 조회 시점을 로컬에 저장하고, 이를 앱 실행 시에 불러온다.

### 1. WebView 의 사용
- 순서
  - XML에서 WebView 레이아웃 작성
  - Activity에서 webView 받아와 웹 url 띄움
    - WebViewClient
  - INTERNET 권한 설정
- 나의 궁금증
    - 왜 webViewClient 를 쓰지 않고 그냥 loadUrl() 만 해줬을 때 ‘Webpage not available’이라는 페이지 오류가 뜰까?
      ![](https://velog.velcdn.com/images/neoseurae12/post/f098529f-6374-4f67-aaae-3f3e93115695/image.png)
    - *`webViewClient`* 란 무엇일까?
      그리고 `webView.*settings*.*javaScriptEnabled* = true` 에 대한 자세한 원리는 무엇일까?
      위의 코드를 하게 되면 “Using `setJavaScriptEnabled` can introduce XSS vulnerabilities into your application, review carefully”라는 경고 문구가 뜨는데 이는 어떤 상황을 우려해 뜨는 것인가?
- 유용한 단축키
    - 깔끔한 코드 정렬 ⇒ command + option + L

### 2. ViewPager2 의 사용

- 버튼 누르면 해당하는 Fragment가 중앙에 뜨도록 만들어보기
- 순서
  - ‘프래그먼트’ 공식문서
    - 프래그먼트의 생명주기
  - Fragment 만듦
    - WebViewFragment 와 BFragment
  - WebViewFragment
    - onCreateView()
    - onViewCreated()
  - viewBinding 활성화
  - activity_main 수정
    - fragment 2개 조작할 수 있도록
    - FrameLayout + button1 + button2
  - MainActivity
    - button의 동작 설정해줌
      - supportFragmentManager
        : Activity 내부의 기능. Activity에 Fragment를 붙일 수 있기 때문에 Activity는 그 Fragment를 관리하는 기능을 가지고 있다.
      
      - Transaction
        : 작업의 단위.
        beginTransaction() 으로 작업 시작하고, commit() 으로 작업 끝낸다.
        그래서 작업 작업 단위로 실행이 될 수 있도록 돕는다.
      
        - apply() 를 통해 transaction 을 바로 만들어줘버림.
        - 버튼 누를 때마다 google 첫 페이지가 뜰 것이다.
          왜냐하면 replace() 안에서 WebViewFragment(), 즉 새로운 프래그먼트가 항상 생성되도록 설정해놨기 때문이다.
  - BFragment 코드 작성
    - WebViewFragment 의 코드와 굉장히 유사하기 때문에 복붙.
    단, WebViewFragmentBinding 을 BFragmentBinding 으로 바꿔주는 기본적인 수정 필요.
  - Fragment의 생명주기가 실제로 어떻게 흘러가는지 보고 싶다면 Log를 찍어봐라.
- 질문
  - ‘프래그먼트’ 공식문서 한 번 찬찬히 읽어보면서 꼭 이해하기
  - supportFragmentManager 가 더 궁금하다.
  - Transaction 에 대해 더 정확히 알고 싶다.
  - Log 찍어봐서 Fragment의 생명주기 눈으로 직접 확인해보기

### 3. 기본 UI 구성

- 순서
  - fragment_second.xml
    - 하단의 버튼 2개
    - 하드코딩 지양 경고
      - values > strings.xml
      - 다국어 지원, 공통 문구를 고려한 것
    - ProgressBar
      - 화면이 다 로딩 되고 난 후에는 progressBar 가 꺼졌으면 좋겠다면
        ⇒ 화면이 다 로딩 됐는지에 대한 참고나 콜백을 받아올 수 있는 WebViewClient 를 새로 만들어주면 된다.
  - WebtoonWebViewClient 생성
    - WebViewClient 상속 받기
      - 안에 까보면 override 해서 쓸 수 있는 함수들 보고 적절한 함수 선택
    - onPageFinished() 함수 override 하기
      - progressBar 를 WebtoonWebViewClient 안에서 다뤄야 하는데 progressBar 는 WebViewFragment 안의 binding에 있다. 어떻게 해야 할까?
        - 방법1) WebtoonWebViewClient 에 콜백을 준다
        - 방법2) progressBar 자체를 WebtoonWebViewClient 에 끌고 온다
        - 방법2 를 해볼 예정
      - progressBar 를 안 보이게 설정한다.
        - 방법1) progressBar.visibility = View.GONE
        - 방법2) progressBar.isVisible = false
    - onPageStarted() 함수 override 하기
      - progressBar 를 보이게 설정한다
    - shouldOverrideUrlLoading() 함수 override 하기
      - return false
      - if 문 걸어놓고 만약 네이버 웹툰 바깥으로 가려고 하면 url loading 이 안 먹히게 할 수 있음
      - 결국 여기 코드는 지움
  - 네이버 웹툰의 특정 웹툰의 특정 화로 url 주소 변경
  - back 버튼 누르면 무조건 앱이 꺼져버리는 것이 아니라, fragment에서 back 버튼이 먹힐 수 있으면 fragment에서의 back 을 우선하고, fragment에서는 더 이상 먹힐 게 없다면 그때 앱이 꺼지도록 변경함
    - back 버튼에 대한 이벤트는 Activity 로 들어온다.
    - onBackPressed()
      - WebViewFragment 를 받아오기 위해서는 supportFragmentManager.fragments 를 통해서 받아오면 된다.
      - 단, 그 fragments 리스트에 프래그먼트들이 어떤 순서로 있는지는 모르기 때문에 if 문으로 검사해봐야 함
    - activity 에서 fragment 에다 그 fragment에서 back 버튼이 더 먹힐 수 있는지 없는지 조회할 수 있도록 하는 canGoBack() 함수 만듦
    - fragment 상에서 back 먹히는 goBack() 함수 만듦
    - super.onBackPressed()
      - 무조건 ‘앱’이 꺼지게 만드는 함수는 아니다.
        super(부모)가 무엇으로 올지 모르기 때문이다.
        앱을 꺼지게 만든다라는 것보다는 그저 부모의 onBackPressed() 함수를 호출한다라고 말 그대로 받아들이자.
- 질문
  - WebViewClient 의 shouldOverrideUrlLoading() 함수는 어떻게, 왜 동작하는가?
  - super.onBackPressed() 가 그 사이에 deprecated 됨. 이를 대체할 작동은?
    - *`onBackPressedDispatcher`*
    - 그 동작에 대해 더 자세히 알아보자.

### 4. WebView Tab 구성

- 순서
  - activity_main.xml
    - 버튼 2개, frame layout 1개 다 지워버림
    - TabLayout & ViewPager2 를 작성해줌
    - ViewPager2
      : 리스트 형태로 fragment를 관리해줌
    
    - 실제로 스와이프는 ViewPager2 에서 발생하는 것이고, Tab Layout 은 ViewPager2 와 연동되어 작동하는 것이다.
  - MainActivity
    - ViewPager2 는 내부적으로 RecyclerView 로 이뤄져 있다고 한다. 그래서 adapter 를 달아줄 수 있다.
      - adapter 를 가지고 리스트를 구성해준다.
      - supportFragmentManager 로 바로 replace 해주는 것이 아니라, 그런 과정을 adapter 내부에서 position에 따라 replace 및 add 등이 이뤄질 수 있도록 해주려 함
      - adapter 도 하나 만들어주기로.
      ⇒ ViewPagerAdapter 생성
    - TODO 걸어놓기
  - ViewPagerAdapter
    - FragmentStateAdapter 상속 받기
    - 생성자들에 무엇들이 있는지 확인하고 적절한 인자 건네주기
    - member implementation 필요
  - MainActivity
    - ViewPagerAdapter 연결해주기
      - 이렇게만 해주는 것으로 알아서 replace 및 add 가 됨
    - onBackPressed() 수정 (TODO 해결)
      - 0번째 → currentItem
    - TabLayout 연결해주기
      - TabLayoutMediator()
      - tab 의 이름 설정하기
        - run { … } 의 사용
      - attach() 까지 해줘야 보인다
      - tab 을 커스텀해줄 수도 있다
        - `tab.customView = textView` 이런 식으로 커스텀한 textView 를 넣어줄 수도 있고
        - TabLayout 에도 콜백 함수들이 있어서 어떤 탭이 선택되면 그 탭의 텍스트를 볼드 처리한다든지, 색깔을 바꿔준다든지 하는 작업들을 할 수 있음
- 질문
  - (그냥) ViewPager 와 ViewPager2 는 어떤 차이점이 있는 것인가?
  - `ViewPager2
  : 리스트 형태로 fragment를 관리해줌`
  위의 말을 확실히 이해하자.
  
  - iewPager2 가 내부적으로 RecyclerView 로 이뤄져 있다는 말이 정확히 무슨 말인가?
  - RecyclerView의 adapter 의 역할, 그 외 RecyclerView 자체에 대한 내용 다시 알아보자.
  - TabLayoutMediator 공식문서 함 읽어보기
  - ★ run { … } 는 무엇인가..?

### 5. 마지막 시점 돌아가기

- 순서
  - WebViewFragment
    - 저장된 마지막 회차 불러오기
      - backToLastButton 에 setOnClickListener 달아주기
      - SharedPreferences 을 사용해야 함
      - Activity 에서 SharedPreferences 사용은 해봤을 것임
        - `getSharedPreferences(”WEB_HISTORY”, Context.MODE_PRIVATE)`
      - 하지만 이번에는 Fragment 에서 SharedPreferences 사용을 해봐야 함
        - Fragment 는 자기가 붙어있는 Activity 를 알고 있음
          ⇒ `activity` 키워드로 접근 가능
        - `activity?.getSharedPreferences(”WEB_HISTORY”, Context.MODE_PRIVATE)`
          ⇒ SharedPreferences XML 파일이 WEB_HISTORY 라는 이름으로 생성됨
        - sharedPreference 로부터 `getString()` 해서 url 받아오기
          - null 체크 필요
          - null 이면 Toast 메시지 띄우기
          - null 이 아니면 webView 에다 그 url 로드 시키기
  - WebtoonWebViewClient
    - 마지막 회차 저장하기
      - 웹툰과 관련 있는 페이지라면 ‘마지막 회차’로 저장을 해두어야 함
      - shouldOverrideUrlLoading() 함수 오버라이드
        - 리퀘스트가 널이 아님 && url의 string 이 comic.naver.~/detail 을 포함하고 있다면
          sharedPreferences 에다 그 url 을 저장해라
          - 그런데 sharedPreferences 를 받아오려면 어떻게 해야 할까?
            - 방법1) progressBar 주입 받았던 것처럼 WebtoonWebViewClient() 의 매개변수로 fragment 를 받아와서, fragment → activity → sharedPreference 를 받아온다.
            - 방법2) 아예 sharedPreference 자체를 WebtoonWebViewClient() 의 매개변수로 받아온다.
            - 방법3) sharedPreference 에 저장하는 함수 자체를 받아 WebtoonWebViewClient() 에서 실행을 한다.
            - 방법3 을 사용할 예정
          - 방법3
            - saveData 라는 이름의 ‘함수’를 WebtoonWebViewClient() 의 매개변수로 받는다
            - sharedPreference 에다 `edit()` 열고 `putString()` 해서 해당되는 페이지의 url 저장하기
            - `edit()` 함수의 작동
              - `commit()` ⇒ 동기 (곧바로 실행)
              - `apply()` ⇒ 비동기 (때 되면 실행)
- 질문
    - SharedPreferences 가 정확히 무엇인가?
      XML 파일이라고 하는데 왜 XML 을 받아오는 것인가?
    - sharedPreferences 는 한 번 뭘 넣으면 앱을 다시 run 시켜도 계속 남아있던데 왜 이러나?
    - sharedPreferences 공식문서 살짝 봤더니 sharedPreferences을 대신해 **DataStore** 를 사용하라고 뜨던데, 둘 간의 차이는 무엇이고 왜 대체하라는 건가?
      바로 위의 저 문제와 관련이 있나?
    - shouldOverrideUrlLoading() 함수가 정확히 언제 콜백되는 것인지
    - `Context.*MODE_PRIVATE`* 이 뭐지?
    - `Toast.makeText(*context*, "돌아갈 마지막 시점이 없습니다.", Toast.*LENGTH_SHORT*).show()`
      에서 대체 context 가 뭐지?
    - `edit()` 함수의 `commit()`, `apply()` 작동 더 자세히 알아보기

### 6. 탭 이름을 동적으로 바꾸기

- 순서
  - BFragment 와 그 xml 삭제
  - WebViewFragment
    - 외부에서 매개변수로 들어오는 webViewUrl 를 각 탭의 fragment 마다 load 해주기
  - ViewPagerAdapter
    - WebViewFragment 를 생성하는 곳
    - 각 fragment 마다 다른 webViewUrl 넣어줌
  - 탭 이름 바꾸기
    - changeTabNameButton 에 setOnClickListener 달아줌
    - Dialog 로 바꿀 이름 입력 받기
      - AlertDialog
      - Dialog 는 Builder 패턴으로 만들 수 있다
        `AlertDialog.Builder(context)`
      - editText 만들어서 dialog 에 붙여주기
        `dialog.setView(editText)`
      - ‘취소’ 버튼 & ‘저장’ 버튼
        - setNegativeButton() — 취소
          - onClickListener 의 받아야 하는 매개변수들에 무엇이 있는지 안쪽 까보기
          - 다이얼로그 취소하기(끄기)
            ⇒ `cancel()`
        - setPositiveButton() — 저장
          - onClickListener 의 받아야 하는 매개변수들에 무엇이 있는지 안쪽 까보기
          - sharedPreference 에다 저장해두기
            - 파일 이름(`”WEB_HISTORY”`) 실수할 수 있는 가능성을 고려해 ‘상수(const)’로 선언해두기
            - companion object
              - const 를 쓰기 때문에 companion object 를 선언해주어야 한다
              - companion object — 자바의 static
              - const — 자바의 final
            - 키: `“tab${position}_name”`
          - MainActivity 의 TabLayoutMediator 가 tab의 name 이 바뀌었다는 것을 알아차리도록 해야 한다.
            - sharedPreference 에다 저장하고 받아옴
            - WebtoonWebViewFragment 에서 dialog 를 통해 이름을 바꿀 때마다 MainActivity 에다 탭 이름이 바뀌었다는 것을 알려주는 신호를 줘야 한다.
              ⇒ onTabNameChangedListener 인터페이스 생성
            - onTabNameChangedListener 인터페이스
              - tabNameChanged(position, tabName) 함수
            - WebtoonWebViewFragment
              - listener 를 변수로 선언 (nullable)
              - ‘저장’ 버튼 누르면
                - sharedPreference 에다 입력 받은 탭 이름 넣어줌
                - listener 한테 탭 이름 변경되었다고 신호 줌
            - MainActivity
              - onTabNameChangedListener 인터페이스를 구현함
                - tabNameChanged(position, tabName) 함수:
                  몇 번째 탭인가(position)를 받아서 그 탭 이름을 바뀐 이름(changedTabName)으로 바꿔줌
              - 앱 열면
                - sharedPreference 로부터 탭마다 각자의 탭 이름 받아옴
            - ViewPager2Adapter
              - WebtoonWebViewFragment 생성 시, WebtoonWebViewFragment의 listener(현재 null) 에다 MainActivity(onTabNameChangedListener 인터페이스 구현) 를 연결해줘야 함
- 질문
  - AlertDialog 말고도 다른 dialog 들로는 무엇이 있나? 그것들 각자의 쓰임은?
  - `Dialog 는 Builder 패턴으로 만들 수 있다` 고 하는데 ‘Builder 패턴’이 무엇인가?
  - setNegative/PositiveButton 의 onClickListener 에 대해 정확히 알아보기.
    특히, 그 안의 변수들의 역할.
  - companion object 과 const 의 관계에 대해 알아보자.
    자바의 static 과 final 에 각각 대응된다고 하는데 일단 자바의 static 과 final 의 역할과 동작을 어렴풋하게만 (아무튼 변하지 않는다…?) 알고 있어서 더 알아볼 필요가 있다.
  - 왜 listener 에다 일단 null 을 넣어줄까?
    lateinit var 로 선언하면 안 되나?
    ’nullable 변수’와 ‘lateinit var 변수’ 간에는 어떤 차이가 있을까?
  - 탭을 변경하면 아래 2개 버튼들까지 스와이프가 되어버린다.
    탭을 변경해도 버튼들은 가만히 자리를 유지했으면 좋겠으면 어떻게 코드를 바꿔야 하는지 리팩터링 해보자.
