#!/usr/bin/python3

#modules imported
from datetime import datetime
from tokenize import String
import requests
from time import sleep
from mfrc522 import SimpleMFRC522
#https://github.com/pimylifeup/MFRC522-python
import RPi.GPIO as GPIO

#global variables
reader = SimpleMFRC522()
LED_PIN_GREEN = 15
LED_PIN_RED = 13
LED_ACTIVE = True

#Access token for API being read
def readToken(path: str = "./token.txt"):
    token = ""
    deviceId = None
    with open(path, "r") as file:
        lines = file.readlines()
        for line in lines:
            if "token: " in line and token == "":
                token = line.replace("token: ", "")
            elif "token: " in line:
                raise Exception("\"token: \" appeared twice")
            elif "deviceId: " in line and deviceId == None:
                deviceId = int(line.replace("deviceId: ", ""))
            elif "deviceId: " in line:
                raise Exception("\"deviceId: \" appeared twice")
            else:
                raise Exception("only the lines with \"token:\" or \"deviceId\" allowed")
    if token == "" or deviceId == None:
        raise Exception("Something went wrong")
    return (token, deviceId)

#API Request and Response filtered for permission to open the car
def getPermission(workCard: str, deviceId: int, token: str) -> bool:
    api_url = "https://api.vams.server-welt.com/raspi/api/v1/summon"
    body = {"deviceId": deviceId, "token": token, "workCard": workCard}
    response = requests.post(api_url, json=body)
    if response.status_code == 401:
        return False
    elif response.status_code == 404:
        return False
    elif response.status_code == 200:
        if response.json().get("authorizedOpening") == True:
            return True
        return False
    name = response.json().get("firstName")
    print(response.json().get("firstName"))
    print(name)
    print(response)
    raise Exception("Something went wrong")

#API Request and Response filtered for name of the person with the card
def getName(workCard: str, deviceId: int, token: str):
    api_url = "https://api.vams.server-welt.com/raspi/api/v1/summon"
    body = {"deviceId": deviceId, "token": token, "workCard": workCard}
    response = requests.post(api_url, json=body)
    if response.status_code == 401:
        return False
    elif response.status_code == 404:
        return False
    elif response.status_code == 200:
        if response.json().get("firstName") and response.json().get("lastName"):
            name = response.json().get("firstName")+" "+response.json().get("lastName")
            return name
        return "ERROR OCCURED WHILE REACHING DATABASE"


#API Request and Response filtered for response
def getReservation(workCard: str, deviceId: int, token: str):
    api_url = "https://api.vams.server-welt.com/raspi/api/v1/summon"
    body = {"deviceId": deviceId, "token": token, "workCard": workCard}
    response = requests.post(api_url, json=body)
    if response.status_code == 401:
        return False
    elif response.status_code == 404:
        return False
    elif response.status_code == 200:
        if response.json().get("beginn") and response.json().get("end"):
            
            #filter the response to convert the date in a readable format
            datetimeBegin = response.json().get("beginn").split("T")
            dateBegin = datetimeBegin[0].split('-')
            timeBegin = datetimeBegin[1].split(".")
            formatBeginDate = dateBegin[2]+"."+dateBegin[1]+"."+dateBegin[0]
            formatBeginTime = timeBegin[0].split(":")
            formattedBeginTime = formatBeginTime[0]+":"+formatBeginTime[1]

            datetimeEnd = response.json().get("end").split("T")
            dateEnd = datetimeEnd[0].split('-')
            timeEnd = datetimeEnd[1].split(".")
            formatEndDate = dateEnd[2]+"."+dateEnd[1]+"."+dateEnd[0]
            formatEndTime = timeEnd[0].split(":")
            formattedEndTime = formatEndTime[0]+":"+formatEndTime[1]

            if formattedEndTime =="00:00":
                
                reservation = "NO RESERVATION NEEDED"
            else:
                reservation = "begin: "+formatBeginDate+" at "+formattedBeginTime+"\nend: "+formatEndDate+" at "+formattedEndTime
                
            return reservation
        return "ERROR OCCURED WHILE REACHING DATABASE"

#LED variables set up with the GPIO board
def LEDSetup(led_pin: int):
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(led_pin, GPIO.OUT)
    GPIO.output(led_pin, not LED_ACTIVE)

#Both LEDs active not at the same time
def setLEDStatus(status: bool, duration: int = 2):
    if status == True:
        GPIO.output(LED_PIN_GREEN, LED_ACTIVE)
        sleep(duration)
        GPIO.output(LED_PIN_GREEN, not LED_ACTIVE)
    else:
        GPIO.output(LED_PIN_RED, LED_ACTIVE)
        sleep(duration)
        GPIO.output(LED_PIN_RED, not LED_ACTIVE)

#RFID Tag as output (workcard)
def readRFIDTagBlocking() -> str:
    id = reader.read()
    return id


while True:
    #cancel warnings
    GPIO.setwarnings(False)
    #Setup LED Pins
    LEDSetup(LED_PIN_GREEN)
    LEDSetup(LED_PIN_RED)
    #Access token being read
    (token, deviceId) = readToken()

    #workCard being read (RFID Tag returned)
    workCard = readRFIDTagBlocking()
    print("\nScanning...")
    #Print WorkCard details
    if(workCard):
        print("-----------WORK CARD-----------")
        workCardStr = str(workCard)
        print("Card-number: "+workCardStr)
    permission = getPermission(workCard, deviceId, token)
    name = getName(workCard, deviceId, token)
    if name != False:
        print("Name: "+name)
    else:
        print("NOT AUTHORIZED TO OPEN")
    setLEDStatus(permission)
    res = getReservation(workCard, deviceId, token)
    print("\n"+name+"'s reservation status:")
    print(res)
    print("-------------------------------")

    
