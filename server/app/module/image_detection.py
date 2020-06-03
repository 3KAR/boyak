import cv2 as cv
import numpy as np 
import os

def image_preprocessing(src,dst):
    # 찍은 사진이 저장되는 폴더 경로
    path_str = src
    # 경로 밑 이미지 검색
    druglist = []
    for (path, dir, files) in os.walk(path_str):
        for filename in files:
            ext = os.path.splitext(filename)[-1]
            if ext == '.jpg':
                druglist.append("%s/%s" % (path, filename))
    for listdir in druglist:
        # 저장할 이름 지정
        name = listdir.split('/')[-1]
        # 이미지 불러오기
        img_color = cv.imread(listdir, cv.IMREAD_COLOR)
        # 이미지 카피
        img = np.zeros_like(img_color)
        # 이미지 전처리
        # 외곽 추출
        gray = cv.cvtColor(img_color,cv.COLOR_BGR2GRAY)
        hsv = cv.cvtColor(img_color, cv.COLOR_BGR2HSV)
        h, s, v = cv.split(hsv)
        ret, img_th = cv.threshold(gray, 0, 255, cv.THRESH_BINARY_INV|cv.THRESH_OTSU)
        gau = cv.GaussianBlur(img_th, (9, 9), 2)
        canny = cv.Canny(gau,20,255)
        dilation = cv.dilate(canny, cv.getStructuringElement(cv.MORPH_RECT, (3 , 3)), iterations=3)
        contours, hierarchy = cv.findContours(dilation, cv.RETR_EXTERNAL, cv.CHAIN_APPROX_SIMPLE)
        # 추출한 외곽으로 자르기
        rects = [cv.boundingRect(each) for each in contours]
        tmp = [w*h for (x,y,w,h) in rects]
        tmp.sort()
        # 카피한 이미지에 외곽안쪽 흰색(255,255,255)로 채우기
        for cnt in contours:
            cv.drawContours(img, [cnt], 0, (255, 255, 255), -1) 
        # 추출한 외곽중 면적이 2만~100만 픽셀인것만 저장
        rects = [(x,y,w,h) for (x,y,w,h) in rects if ((w*h>20000)and(w*h<5000000))]
        
        # 추출한 외곽을 기준으로 원본과 카피본 자르기
        for rect in rects:
            img_trim = img_color[rect[1]:rect[1]+rect[3], rect[0]:rect[0]+rect[2]]
            img = img[rect[1]:rect[1]+rect[3], rect[0]:rect[0]+rect[2]]
        
        # 카피본에서 흰색으로 채워진 부분이 아닌 곳은 검은색으로 채우기
        for k in range(3):
            for i in range(img.shape[0]):
                for j in range(img.shape[1]):
                    if img[i,j,k] != 255:
                        img_trim[i,j,k] = 0
        # 이미지 저장
        cv.imwrite('{}/{}'.format(dst,name),img_trim)

