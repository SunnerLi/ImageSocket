# ImageSocket
[![Packagist](https://img.shields.io/packagist/l/doctrine/orm.svg?maxAge=2592000)]()
[![Packagist](https://img.shields.io/badge/Stable-1.0.0-brightgreen.svg)]()</br>   
    
The Android / Python plugin that can used to send the image rapidly.
    
    
<p align="center">
  <img src="https://github.com/SunnerLi/ImageSocket/blob/master/Image/logo2.png"/>
</p> 
    
    
     
Abstract
---------------------
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This project provide a new class `ImageSocket`. As the usual, you can easily transfer the string.
But It's not easy to transfer the image with a few code. On the Android platform, Google provide 
TCP & UDP two WiFi method to pass information. One the other hand, the python platform provide 
socket pachage to receive the infomation. This project build a basic API that the developer can 
transfer the image in a easy way!    
    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This project provide Internet( TCP & UDP ) and bluetooth methodology to reach the goal.    
    
    
     
         
    
Demo
---------------------
(Skip)    
    
    
        
    
    
Contain
---------------------
* Android_Example: There is one example to show how to send image
* PC_Example     : There're two python script show how to receive the image from phone    
    
    
    
    
    
Usage
---------------------  
Please check the wiki in detail.
- [ Android Send Image ](https://github.com/SunnerLi/ImageSocket_Android/wiki)
- [Python Receive Image](https://github.com/SunnerLi/ImageSocket_Python/wiki)    
    
    
     
    
    
Install 
--------------------    
Python:
```
sudo pip install "https://github.com/sunnerli/ImageSocket_Python/tarball/1.0.0"
```   
or    
```
sudo pip install ImageSocket --update
```
    
Android(Gradle):
```gradle
dependencies {
    compile 'com.android.support:appcompat-v7:23.2.1'
    compile 'com.sunner.imagesocket:imagesocket:1.0.0'
}
```
Please check the detail in the submodule readme.    
     
     
     
     
     
Make It Better 
--------------------
This project is the example that show how to use. You should go to the following link if you want to improve or add the new function to the ImageSocket plugin.
- [ Android Develop project ](https://github.com/SunnerLi/ImageSocket_Android)
- [ Python Develop project  ](https://github.com/SunnerLi/ImageSocket_Python)    
     
      
     
     
     
Contributer    
--------------------
* SunnerLi - <a6214123@gmail.com>
     
     
     
     
     
Version    
--------------------
1.0.0 (stable)    
     
     
     
     
     
License
---------------------
    The MIT License (MIT)
    Copyright (c) 2016 - SunnerLi

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
    and associated   documentation files (the "Software"), to deal in the Software without 
    restriction, including without limitation the rights to use, copy, modify, merge, publish, 
    distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom 
    the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or 
    substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
    BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
    DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
