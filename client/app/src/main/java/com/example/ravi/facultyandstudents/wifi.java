package com.example.ravi.facultyandstudents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class wifi extends AppCompatActivity {

    ListView items;Button wifionoff;Button discover;ImageButton sendButt;
    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    TextView status,readmsg;
    BroadcastReceiver mReceiver;
    private IntentFilter intentFilter;
    List<WifiP2pDevice> peers =new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;
    ArrayAdapter<String> adapter;
    static  final int Messageread=1;
    ServerClass serverClass;
    ClientClass clientClass;
    sendReceive sendreceive;
    EditText writemsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        initialwork();
        execListener();
    }
    Handler handler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch(msg.what)
            {
                case Messageread:
                byte[] readbuff= (byte[])msg.obj;
                String tmpmsg=new String(readbuff,0,msg.arg1);
                readmsg.setText(tmpmsg);
                break;


            }
            return true;
        }
    });

    private void execListener() {
        wifionoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wifiManager.isWifiEnabled()){wifiManager.setWifiEnabled(false); wifionoff.setText("WIFI_ON");wifionoff.setBackgroundColor(getResources().getColor(R.color.color_transparent_white));}
                else {wifiManager.setWifiEnabled(true); wifionoff.setText("WIFI_OFF"); wifionoff.setBackgroundColor(getResources().getColor(R.color.coloyellow));}
            }
        });
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        status.setText("Discovery started");

                    }

                    @Override
                    public void onFailure(int i) {
                        status.setText("Discovery failed");

                    }
                });
            }
        });

        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final WifiP2pDevice device =deviceArray[i];
                WifiP2pConfig config =new WifiP2pConfig();
                config.deviceAddress =device.deviceAddress;
                final String devices = deviceNameArray[i];
                mManager.connect(mChannel,config,new WifiP2pManager.ActionListener(){
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"Connected To "+device.deviceName,Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(int i) {
                        Toast.makeText(getApplicationContext(),"Not conected ",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        sendButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String msg=writemsg.getText().toString();
              sendreceive.write(msg.getBytes());

            }
        });


    }

    private void initialwork(){
        items=findViewById(R.id.listView);
        wifionoff=findViewById(R.id.on_off);
        discover=findViewById(R.id.disc);
        status=findViewById(R.id.status);
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        readmsg=findViewById(R.id.readmsg);
        sendButt=findViewById(R.id.sendbutton);
        writemsg=findViewById(R.id.wmessage);

        //mReceiver =new wifidirecrbroadreceiver(mManager,mChannel,this);


        // Indicates this device's details have changed.
        wifiManager= (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mManager= (WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel= mManager.initialize(this,getMainLooper(),null);
        mReceiver=new wifidirecrbroadreceiver(mManager,mChannel,this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }

    WifiP2pManager.PeerListListener peerListListener =new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if (!peerList.getDeviceList().equals(peers)) {
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];
                int index = 0;
                for (WifiP2pDevice device : peerList.getDeviceList()) {
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    index++;
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                items.setAdapter(adapter);
            }
            if (peers.size() == 0)
                Toast.makeText(getApplicationContext(), "NO DEVICE FOUND", Toast.LENGTH_SHORT).show();
            return;
        }
    };
    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener(){
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress groupOwnerAddress =wifiP2pInfo.groupOwnerAddress;
            if(wifiP2pInfo.groupFormed&& wifiP2pInfo.isGroupOwner){
                status.setText("Host");
                serverClass=new ServerClass();
                serverClass.start();
            }
            else if(wifiP2pInfo.groupFormed){
                status.setText("client");
                clientClass=new ClientClass(groupOwnerAddress);
                clientClass.start();

            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
    public class ServerClass extends  Thread{
        Socket socket;
        ServerSocket serverSocket;

        @Override
        public void run() {
            try{
                serverSocket=new ServerSocket(8888);
                socket = serverSocket.accept();
                sendreceive=new sendReceive(socket);
                sendreceive.start();

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }



    }
    private class sendReceive extends Thread{
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public sendReceive(Socket skt){
            socket= skt;
            try {
                inputStream = socket.getInputStream();
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void run() {
            byte[] buffer =new byte[1024];
            int bytes;
            while(socket!=null){
                try{
                    bytes=inputStream.read(buffer);
                    if(bytes>0){
                        handler.obtainMessage(Messageread,bytes,-1,buffer).sendToTarget();
                    }
                }catch (IOException e){
                    e.printStackTrace();

                }

            }
        }
        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public class ClientClass extends  Thread{
        Socket socket;
        String hostAdd;

        public ClientClass(InetAddress hostAddress){
            hostAdd =hostAddress.getHostAddress();
            socket =new Socket();

        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
                sendreceive=new sendReceive(socket);
                sendreceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
