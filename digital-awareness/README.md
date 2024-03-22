## Introduction
A breakdown about "Undangan Nikah" study case from a phising standpoint, which is mainly inteded for malicious SMS banking & OTP scanning activity

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
#### Parser
- curl
- jq

### Threat Actor
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
Score             : [11/03/24] 15/64 security vendors flagged this file as malicious
                    [13/03/24] 22/62 security vendors flagged this file as malicious
                    - BitDefenderFalx: Android.Riskware.SmsSpy.EY
                    - Fortinet: Android/SmsSpy.ZKltr
                    - Kaspersky: HEUR:Trojan-Banker.AndroidOS.UdanganSteal.b
                    - Ikarus: Trojan-Spy.AndroidOS.SMSSpy
                    - WithSecure: Malware.ANDROID/SMSThief.FRMC.Gen
                    - ZoneAlarm by Check Point: HEUR:Trojan-Banker.AndroidOS.UdangaSteal.b
                    - Alibaba: TrojanBanker:Android/UdangaSteal.5804cacd
                    ...
Threat Label      : trojan.smsspy/frmc, trojan.smsspy/udangasteal
Threat Categories : trojan, banker
Family Labels     : smsspy, frmc, smsthief
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
```bash
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
```bash
class   : MainActivityAlias (AppCompatActivity extended)
package : com.example.myapplicatior
imports : android, androidx
--
Permission Setting & Message
  - android.app.action.ADD_DEVICE_ADMIN
  - android.app.extra.DEVICE_ADMIN: ComponentName(MainActivity.class)
  - android.app.extra.ADD_EXPLANATION: "Bấm zô nút đồng ý(ACTIVE á)" / "Click The OK Button (Active)"
```
```bash
class   : SendSMS (BroadcastReceiver extended)
package : com.example.myapplicatior
imports : android, java, okhttp3
--
Telegram API: https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw
  - Chat ID: 7067506660
  - Custom Message: "Berhasil Kirim SMS dari Jauh Kepada : _[dest-number]_, message : _[messsage-body]_"
```
```bash
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
```bash
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
## add frida
```
<<<<<<< Updated upstream
on progress
```
Zenbox Guest System : Android 9 Ultimate
Zenbox Verdict      : 34/100 (Non Malicious) / False Positive
Report Generated    : 10/03/2024 03:30:32
--
Indicators:
  - System Summary:
      + Requests potentially dangerous permissions
  - Data Obfuscation:
      + Uses reflection
  - Persistance Installation & Behaviour:
      + Installs an application shortcut on the screen
  - Boot Survival:
      + Starts/registers a service/receiver on phone boot (autostart)
      + Installs a new wake lock (to get activate on phone screen on)
      + Has permission to execute code after phone reboot
  - Hooking and other Techniques for Hiding and Protection:
      + Removes its application launcher (likely to stay hidden)
      + Queries list of running processes/tasks
  - Malware Analysis System Evasion:
      + Accesses android OS build fields
  - Language, Device and Operating System Detection:
      + Queries the unqiue device ID (IMEI, MEID or ESN)
  - Networking:
      + Opens an internet connection
      + Checks an internet connection is available
      + Performs DNS lookups (Java API)
  - E-Banking Fraud:
      + Has functionalty to add an overlay to other apps
  - Spam, unwanted Advertisements and Ransom Demands:
      + Sends SMS using SmsManager
      + Has permission to send SMS in the background
  - Operating System Destruction:
      + Lists and deletes files in the same context
  - Change of System Appearance:
      + May access the Android keyguard (lock screen)
  - Stealing of Sensitive Information:
      + Parses SMS data (e.g. originating address)
      + Has permission to receive SMS in the background
      + Has permission to read the SMS storage
      + Monitors incoming SMS
      + Reads boot loader settings of the device
      + Creates SMS data (e.g. PDU)
      + May take a camera picture
  - Remote Access Functionality
      + Found parser code for incoming SMS (may be used to act on incoming SMS, BOT)
      + Found suspicious command strings (may be related to BOT commands)
  - Location Tracking
      + Queries the phones location (GPS)
  - Privilege Escalation
      + Tries to add a new device administrator

```
## Threat Mapping
done by MITRE ATT&CK TTP (Technique, Tactice & Procedure) Navigator in Enterprise & Mobile Field
```bash
Initial Access (TA0001)
  1. Phising (T1566)
      - Spearphising Attachment (.001)
Execution (TA0002)
  1. User Execution (T1204) 
      - Maliciious File (.001)
Persistennce (TA0028)
  1. Boot or Logon Initialization Scripts (T1398)
  2. Foreground Persistence (T1541) 
Privilege Escalation (TA0029)
  1. Abuse Elevation Control Mechanism (T1626)
      - Device Administrator Permissions (.001)
Defense Evasion (TA0030)
  1. Indicator Removal on Host (T1630)
      - File Deletion (.002)
Discovery (TA0032)
  1. System Network Connections Discovery (T1421)
  2. Process Discovery (T1424)
  3. System Information Discovery (T1426)
  4. Location Tracking (T1430)
Collection (TA0035)
  1. Location Tracking (T1430)  
  2. Access Notification (T1517)
  3. Protected User Data (T1636)
      - SMS Messages (.004)
Command & Control (TA0037)
  1. Application Layer Protocol (T1437)
      - Web Protocols (.001)
  2. Encrypted Channel (T1573)
  3. Web Service (T1481)
      - One-Way Communication (.003)
```
=======
on progress. using adb & frida on android emulator
>>>>>>> Stashed changes

### Telegram API Call
```bash
curl -s "https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage" \
     -d "parse_mode=markdown" \
     -d "chat_id=7067506660" \
     -d "text=pwnd" \
     | jq .
```
```json
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

## References
- [Membedah Modus Penipuan Undangan Pernikahan .apk | **Medium**](https://derangga.medium.com/membedah-modus-penipuan-undangan-pernikahan-apk-900150fc7d12)
- [Analisa Malware APK Android berkedok Cek Resi Pengiriman Paket | **CISRT Tanggerang Kota**](https://csirt.tangerangkota.go.id/berita/analisa-malware-apk-android-berkedok-cek-resi-pengiriman-paket)
- [Laporan Analisis Reverse Engineering Suspicious Android Application Undangan Pernikahan Digital | **PUSHAN SIBER RI**](https://cloud.pushansiber.net/index.php/s/J3b4NHejekAWaSk)
