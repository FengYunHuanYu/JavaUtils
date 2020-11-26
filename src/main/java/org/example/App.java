package org.example;

import com.sun.xml.internal.ws.addressing.WsaTubeHelperImpl;
import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import sun.lwawt.macosx.CSystemTray;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.nio.ByteBuffer;
import java.lang.String;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * Hello world!
 *
 */
public class App extends Thread
{
    int i;
    Thread previousThread; //上一个线程
    public App(Thread previousThread,int i){
        this.previousThread=previousThread;
        this.i=i;
    }
    @Override
    public void run() {
        try {
            //调用上一个线程的join方法，大家可以自己演示的时候可以把这行代码注释掉
            System.out.println("begin num:"+i);
            previousThread.join();
            System.out.println("num:"+i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num:"+i);
    }
    public static void main(String[] args) {
        Thread previousThread=Thread.currentThread();
        for(int i=0;i<10;i++){
            App joinDemo=new App(previousThread,i);
            joinDemo.start();
            previousThread=joinDemo;
        }
    }
    static class ParkThread implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"开始阻塞" + " "+ System.currentTimeMillis());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName()+"第一次结束阻塞"+ " "+ System.currentTimeMillis());
            LockSupport.park();
            System.out.println("第二次结束阻塞"+ " "+ System.currentTimeMillis());
            Thread.interrupted();
            LockSupport.park();
            System.out.println("第三次结束阻塞"+ " "+ System.currentTimeMillis());
            LockSupport.park();
            System.out.println("第二次结束阻塞"+ " "+ System.currentTimeMillis());
        }
    }




}
