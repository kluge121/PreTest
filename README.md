# PreTest
다음 image 검색 API를 이용한 결과를 보여주는 App입니다.

## 아키텍처
MVVM

## 주요 사용 라이브러리
* DataBinding
* LiveData
* RxAndorid
* Retrofit
  
## 설명
* Retrofit + Rgson + Rx를 통해서 API를 호출하고 이미지 검색 결과를 가져옵니다.
가져온 결과는 Endless RecyclerView에 전달되고 스크롤을 통해 추가 검색 결과를 불러옵니다.
표시되는 View는 LiveData와 Data Binding을 통하여 변경사항을 감지하여 리스트를 새로고침합니다.


 
