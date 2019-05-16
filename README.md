# Rxjava_Papago_example
- 네이버 파파고 API를 이용한 rxjava 예제 프로그램
- zip, flatMap을 사용해 rxjava를 사용한 데이터 통신에 대한 이해

### 1. flatMap 예제
![flatmap_ex](https://steemitimages.com/400x0/https://user-images.githubusercontent.com/42676880/57859612-40a69b00-782e-11e9-837e-63b8dc74a70c.png)

- 언어감지 API (A API)의 결과로 source text의 언어코드를 받아오고, 그것을 번역 API (B API)의 인자로 전달  
- flatMap을 사용하여 구현

### 2. zip 예제
![zip_ex](https://steemitimages.com/400x0/https://user-images.githubusercontent.com/42676880/57860013-04c00580-782f-11e9-8dc2-cf1dcbc7c78d.png)
- 번역 API의 2개의 결과를 각각 Observable A, B로 받아옴   
- zip을 이용하여 Observable A와 B를 합쳐 String으로 반환하도록 구현
