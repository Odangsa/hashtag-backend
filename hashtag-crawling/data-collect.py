### jira -> WA-16 : 데이터 수집 및 DB 연동

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from time import sleep
import pymysql
import itertools
from emoji import core

# 브라우저 꺼짐 방지 옵션
chrome_options = Options()
chrome_options.add_experimental_option("detach", True)
driver = webdriver.Chrome(options=chrome_options)

# link 받아옴
driver.get("https://www.instagram.com/accounts/login/")
driver.implicitly_wait(10)

# 인스타 로그인
### ID : oodangsa
### PW : qwer1234!!
### ID : aua

# ID 입력
driver.find_element(By.XPATH, '//*[@id="loginForm"]/div/div[1]/div/label/input').send_keys("aston97103")
# PW 입력
driver.find_element(By.XPATH, '//*[@id="loginForm"]/div/div[2]/div/label/input').send_keys("dhekdtk!!")
# 로그인 버튼 클릭
driver.find_element(By.XPATH, '//*[@id="loginForm"]/div/div[3]/button').click()

driver.implicitly_wait(10)
# 로그인 정보 저장 창 - save
driver.find_element(By.XPATH, '/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[2]/section/main/div/div/div/section/div/button').click()

driver.implicitly_wait(10)
sleep(10)
# 알람 허용 창 - not now
driver.find_element(By.XPATH, '/html/body/div[3]/div[1]/div/div[2]/div/div/div/div/div[2]/div/div/div[3]/button[2]').click()

# 한글자씩 검색해서 데이터 수집
# cho_list = ['ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ']
# jung_list = ['ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ']
# jong_list = ['', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ']

jong_list = [0, 1, 4, 7, 8, 16, 17, 19, 21, 22, 23, 24, 25, 26, 27]
# jong_list = ['ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ']
# 1,000개 이하로 자르기

# 검색 돋보기 클릭
driver.find_element(By.XPATH, '/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div/div/div/div/div[2]/div[2]').click()
driver.implicitly_wait(10)

# sudo mysql -u root
# mysql -u root -p / 1234
                
DB = pymysql.connect(host='localhost', user='root', password='1234', db='hashtag', charset='utf8')
cur = DB.cursor()
sql = "show tables"

### 한글 한글자 뽑기 !!!!!!!!!
file = open("hashtags.txt", "a+")

hashtag_arr = [[] for _ in range(19*21*28)]
num = 0
# 더 까지 했음 데 부터 하면 됌!!
for cho in range(19):  # 초성 19개
    for jung in range(21):  # 중성 21개
        for jong in jong_list:  # 종성 28개
            word = "#" + chr(0xAC00 + cho * 21 * 28 + jung * 28 + jong)
            driver.implicitly_wait(10)
            
            driver.find_element(By.XPATH, '/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div/div/div[2]/div/div/div[1]/div/div/input').send_keys(Keys.BACK_SPACE)
            driver.find_element(By.XPATH, '/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div/div/div[2]/div/div/div[1]/div/div/input').send_keys(Keys.BACK_SPACE)
            
            driver.find_element(By.XPATH, '/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div/div/div[2]/div/div/div[1]/div/div/input').send_keys(word)
            sleep(3) # 팝업으로 검색 창이 뜬 이후에 해야 해시태그 나옴
            hashtags = driver.find_elements(By.CLASS_NAME, "x1lliihq.x193iq5w.x6ikm8r.x10wlt62.xlyipyv.xuxw1ft")
            for i in range(0, len(hashtags), 2):
                if '#' not in hashtags[i].text: break # 메인 페이지의 사용자 추천 안 받기 위함
                tag_tmp = core.replace_emoji(hashtags[i].text, replace="똸")
                cnt, _ = hashtags[i+1].text.split()
                cnt = int(cnt.replace(',', ''))
                if cnt < 1000: continue # 1000 이하는 X
                if '똸' in tag_tmp: continue # 이모티콘 X
                if len(tag_tmp) >= 100: continue # 100글자 이상 X
                #if "𝐁𝐄𝐀𝐑𝔤𝔯𝔬𝔲𝔭" in tag_tmp: continue # 외계어;; 차단
                if "𖤐" in tag_tmp: continue
                hashtag_arr[num].append({tag_tmp:cnt})
                file.write(f'{tag_tmp} : {cnt}\n') # 파일 입력
                sql = "insert into Entire value (%s, %s)" # DB 입력
                cur.execute(sql, (tag_tmp, cnt))
            #file.write('----------------------------------------\n')
            num = num + 1
        DB.commit()
DB.close()
file.close()

# result = cur.fetchall()