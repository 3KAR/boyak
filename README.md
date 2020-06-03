![](https://github.com/3KAR/boyak/blob/master/docs/UI%20sample/%EB%A1%9C%EA%B3%A0800.jpg?raw=true)



# 보약(보이는 약)

### 알약 영상 인식을 통한 분류 및 검색 서비스



#### 수집 및 사용 데이터

1. 공공데이터포털

   1) 의약품 낱알식별정보(https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15057639)

   2) DUR품목정보(https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15059486)

2. 의약품안전나라



#### 사용 기술 및 분석 환경

1. Model
   - python3.7.1
   - Tensorflow2.0
   - Keras2.3.0
   - Numpy
   - Pandas
   - OpenCV
   - Pillow

2. Server/DB
   - EC2(ubuntu18.04) - Flask
   - RDS - MariaDB
3. Application
   - AndroidStudio3.6
   - Kotlin
   - Retrofit2
   - Okhttp3
   - RXpaparazzo
4. Environment
   - CPU : i5-8500
   - VGA : GTX1660 6G
   - DRAM : 16G
   - SSD : 240G
   - Cuda10.0
   - Docker - ubuntu16.04