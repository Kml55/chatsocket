Free chat sdk for your apps(currently just for android)

Use example project and send [me](mailto:anten.apps@gmail.com) an email for a key and a username for your project.

Download and use [chatin](https://play.google.com/store/apps/details?id=com.anten.chatin) as a real example using this infrastructure.


**SDK TUTORIAL**

Add these in your app level build.gradle

```
repositories {
    maven {
        url 'https://dl.bintray.com/kml55/maven'
    }
}
```

```
dependencies {
    compile 'com.anten.socketconnector:socketconnector:0.0.2'
}
```

Use below java Client code. Start to fun!!

```
import com.anten.socketconnector.Client;
import com.anten.socketconnector.SocketListener;

Client socketClient = new Client(USERNAME,SECRET,new SocketListener(){
    @Override
    public void connected() {
        Log.i("connected");
    }

    @Override
    public void messageRecieved(String s) {
        Log.i("Message Received",s);
    }

    @Override
    public void socketClosed() {
        Log.i("socketClosed");
    }
});
```

