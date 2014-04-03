package com.example;

public class WaitNotifyTest {
	public static void main(String[] args) {
		Message msg = new Message("Init Message");
		new Thread(new Waiter(msg), "waiter1").start();
		new Thread(new Waiter(msg), "waiter2").start();

		new Thread(new Notifier(msg), "notifier").start();
		System.out.println("Main thread end.");
	}
}

class Message {
	private String msg;

	public Message(String str) {
		this.msg = str;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String str) {
		this.msg = str;
	}
}

class Waiter implements Runnable {
	private Message msg;

	public Waiter(Message m) {
		this.msg = m;
	}
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		synchronized (msg) {
			try {
				System.out.println("*" + name + "*" + " waiting msg at: " + System.currentTimeMillis());
				msg.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("*" + name + "*" + " got msg at: " + System.currentTimeMillis());
			// process the message now
			System.out.println("*" + name + "*" + " process msg: " + msg.getMsg());
		}
	}
}

class Notifier implements Runnable {
	private Message msg;

	public Notifier(Message msg) {
		this.msg = msg;
	}
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println("*" + name + "*" + " started.");
		try {
			Thread.sleep(1000);
			synchronized (msg) {
				msg.setMsg("\"This is " + "*" + name + "*'s" + " msg.\"");
//				msg.notify();
				msg.notifyAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
