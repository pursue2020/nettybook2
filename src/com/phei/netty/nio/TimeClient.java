/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phei.netty.nio;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class TimeClient {

    /**
     * @param args
     */
    public static void main(String[] args) {

	int port = 8080;
	if (args != null && args.length > 0) {
	    try {
		port = Integer.valueOf(args[0]);
	    } catch (NumberFormatException e) {
		// 采用默认值
	    }
	}
//	new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10,new NettyThreadFactory("Netty"));

		//ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);


		int threadSize=20;
		for (int i = 0; i <threadSize ; i++) {
			executor.submit(new TimeClientHandle("127.0.0.1",port));
		}

    }


    static class NettyThreadFactory implements ThreadFactory{

    	AtomicInteger threadCount=new AtomicInteger(1);

    	private final String namePrefix;

		public NettyThreadFactory(String namePrefix) {
			this.namePrefix = namePrefix+"-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread thread=new Thread(r,this.namePrefix+threadCount.getAndIncrement());
//			thread.setDaemon(false);
			return thread;
		}
	}

}
