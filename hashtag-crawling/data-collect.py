from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options

import pymysql

# sudo mysql -u root
# mysql -u root -p / 1234
DB = pymysql.connect(host='localhost', user='root', password='1234', db='test', charset='utf8')
cur = DB.cursor()
sql = "show databases"
cur.execute(sql)
result = cur.fetchall()
#print(result)

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

# ID 입력
driver.find_element(By.XPATH, '//*[@id="loginForm"]/div/div[1]/div/label/input').send_keys("oodangsa")
# PW 입력
driver.find_element(By.XPATH, '//*[@id="loginForm"]/div/div[2]/div/label/input').send_keys("qwer1234!!")
# 로그인 버튼 클릭
driver.find_element(By.XPATH, '//*[@id="loginForm"]/div/div[3]/button').click()