#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ESP8266WebServer.h>
#include <ESP8266HTTPClient.h>
 
const char *ssid = "rajjo";
const char *password = "rajjo1234";
 
const char *host = "192.168.43.219";

const int trigPin1 = D1;
const int echoPin1 = D0;
const int trigPin2 = D4;
const int echoPin2 = D3;
const int trigPin3 = D7;
const int echoPin3 = D6;

long duration1, duration2, duration3;
int distance1, distance2, distance3;
int new_status1, new_status2, new_status3;
int status1 = 2;
int status2 = 2;
int status3 = 2;

void setup() {
  pinMode(D2, OUTPUT);
  pinMode(D5, OUTPUT);
  pinMode(D8, OUTPUT);
  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);
  pinMode(trigPin3, OUTPUT);
  pinMode(echoPin3, INPUT);
  delay(1000);
  
  Serial.begin(115200);
  WiFi.mode(WIFI_OFF);
  delay(1000);
  WiFi.mode(WIFI_STA);
  
  WiFi.begin(ssid, password);
  Serial.println("");
 
  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
 
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}
 
void loop() {
  HTTPClient http;

  digitalWrite(trigPin1, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin1, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin1, LOW);
  duration1 = pulseIn(echoPin1, HIGH);
  
  digitalWrite(trigPin2, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin2, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin2, LOW);
  duration2 = pulseIn(echoPin2, HIGH);
  
  digitalWrite(trigPin3, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin3, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin3, LOW);
  duration3 = pulseIn(echoPin3, HIGH);
  
  distance1= duration1*0.034/2;
  distance2= duration2*0.034/2;
  distance3= duration3*0.034/2;
  
  Serial.print("Distance1: ");
  Serial.println(distance1);
  Serial.print("Distance2: ");
  Serial.println(distance2);
  Serial.print("Distance3: ");
  Serial.println(distance3);

  if(distance1 < 7) {
    digitalWrite(D2, LOW);
    new_status1 = 1;
  }
  else {
    digitalWrite(D2, HIGH); 
    new_status1 = 2;
  }

  if(distance2 < 7) {
    digitalWrite(D5, LOW);
    new_status2 = 1;
  }
  else {
    digitalWrite(D5, HIGH); 
    new_status2 = 2;
  }
  
  if(distance3 < 7) {
    digitalWrite(D8, LOW);
    new_status3 = 1;
  }
  else {
    digitalWrite(D8, HIGH); 
    new_status3 = 2;
  }

  if(new_status1 != status1) {
    String space_id1 = "A1";
    http.begin("http://192.168.43.219/post_data2.php");
    http.addHeader("Content-Type", "application/x-www-form-urlencoded");
    String postData1 = "space_id="+space_id1+"&status="+new_status1;
    int httpCode1 = http.POST(postData1);
    String payload1 = http.getString();
    Serial.println(postData1);
    Serial.println(httpCode1);
    http.end();
    status1 = new_status1;
  }

  if(new_status2 != status2) {
    String space_id2 = "A2";
    http.begin("http://192.168.43.219/post_data2.php");
    http.addHeader("Content-Type", "application/x-www-form-urlencoded");
    String postData2 = "space_id="+space_id2+"&status="+new_status2;
    int httpCode2 = http.POST(postData2);
    String payload2 = http.getString();
    Serial.println(postData2);
    Serial.println(httpCode2);
    Serial.println(payload2);
    http.end();
    status2 = new_status2;
  }

  if(new_status3 != status3) {
    String space_id3 = "A3";
    http.begin("http://192.168.43.219/post_data2.php");
    http.addHeader("Content-Type", "application/x-www-form-urlencoded");
    String postData3 = "space_id="+space_id3+"&status="+new_status3;
    int httpCode3 = http.POST(postData3);
    String payload3 = http.getString();
    Serial.println(postData3);
    Serial.println(httpCode3);
    http.end();
    status3 = new_status3;
  }
  
  delay(2000);
}
