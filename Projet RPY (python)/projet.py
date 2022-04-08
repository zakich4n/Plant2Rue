
import thingspeak
import time
import json

channel_id = 1679025 #THINGSPEAK CHANNEL ID
write_key  = 'XGY8L6DFK8EDVYA0' # OUR WRITE KEY
read_key   = 'BMMPWGP3UG66ZQ6O' # OUR READ KEY
pin = 4
# Connect the Grove Temperature & Humidity Sensor Pro to digital port D4
sensor = 4  # The Sensor goes on digital port 4.
light_sensor = 0 # Light sensor goes on analog port A0

# Connect the LED to digital port D3
led = 3
# Turn on LED once sensor exceeds threshold resistance
threshold = 10

grovepi.pinMode(light_sensor,"INPUT")
grovepi.pinMode(led,"OUTPUT")
grovepi.pinMode(2,"OUTPUT")

def measure(channel) :
        try:
                                action_value= 0 #Allows fetched value for actuators not to be NULL
                                sensor_value = grovepi.analogRead(light_sensor)
                                resistance = (float)(1023 - sensor_value) * 10 / sensor_value
                                if resistance > threshold:
                                        grovepi.digitalWrite(5,1)
                                else:
                                        grovepi.digitalWrite(5,0)
                                print(grovepi.analogRead(1))
                                moisture_value = grovepi.analogRead(1)

                                print("sensor_value = %d resistance = %.2f" %(sensor_value,  resistance))

                                [temp,humidity] = grovepi.dht(sensor,0)
                                if math.isnan(temp) == False or math.isnan(humidity) == False:
                                        print("temp = %.02f C humidity =%.02f%%"%(temp, humidity))
                                        response = channel.update({'field1': temp , 'field2': humidity, 'field3': sensor_value, 'field4': moisture_value, 'field5': action_value})

                                #On the same channel, field 5 is the actuator's value, if it switches to 1, user requested action
                                url = "https://thingspeak.com/channels/1679025/field/5/last.json"
                                response = urllib.urlopen(url)
                                data = json.loads(response.read())
                                data_str = json.dumps(data)
                                valeur=data_str[-3]
                                print("valeur = ",valeur)
                                if valeur=='1':
                                        try:
                                                print("Watering the plant")
                                                grovepi.digitalWrite(led,1) #If action detected, we water the plant for 5 sec
                                                time.sleep(5)             #Watering device is represented by the blue LED
                                                grovepi.digitalWrite(led,0)
                                        except KeyboardInterrupt:
                                                print("No watering, problem")
                                                grovepi.digitalWrite(led,0)

                                        except IOError:
                                                print ("Error")
                                else :
                                        grovepi.digitalWrite(led,0)
                                        print("No water")

        except:
                print("Connection failed")





if __name__ == "__main__":
        while True:
                channel = thingspeak.Channel(id=channel_id, write_key=write_key, api_key=read_key)
                measure(channel)
                # free account has an api limit of 15sec
                time.sleep(15)






