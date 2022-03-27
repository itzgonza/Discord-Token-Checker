package net.ranktw.discord.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import net.ranktw.utilities.Util;

public abstract class ThreadUtil {

	public static ExecutorService threadPool = Executors.newFixedThreadPool(1);
	public static List<Future> run = new ArrayList<Future>();
	private static AtomicInteger integer = new AtomicInteger();
	private String status;
	private boolean canceled;
	private AtomicInteger finished = new AtomicInteger();
	private AtomicInteger tokenIn = new AtomicInteger();
	private List<String> tokenRun;

	private void setStatus(String status) {
		this.status = status;
		this.update();
	}

	public String getStatus() {
		return this.status;
	}

	private void submitThread(Runnable task) {
		try {
			run.add(threadPool.submit(task));
			Util.sleep(30L);
			while (run.size() >= (Config.isMultiThreaded() ? Config.getThreads() : 1)) {
				Util.sleep(1000L);
			}
		} catch (Exception e) {
			System.out.println("Has an Error while submit Thread\n |_ " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void poll() {
		if (this.getTokenRun().size() == 0) {
			this.finished();
			return;
		}
		if (this.isCanceled()) {
			this.finished();
			return;
		}
		this.setStatus(this.getTokenIn() + " /  " + this.getSize());
		while (this.hasNext()) {
			if (this.isCanceled()) {
				this.finished();
				return;
			}
			this.submitThread(() -> {
				try {
					this.start(this.getNext());
				} catch (IndexOutOfBoundsException ignored) {
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.done();
			});
			if (!this.isCanceled())
				continue;
			this.finished();
			return;
		}
	}

	public void cancel() {
		this.canceled = true;
	}

	private boolean isCanceled() {
		return this.canceled;
	}

	private void done() {
		if (this.finished.incrementAndGet() == this.getTokenRun().size()) {
			this.finished();
		} else {
			this.setStatus(this.getTokenIn() + " /  " + this.getSize());
		}
	}

	public int getTokenIn() {
		return this.tokenIn.get();
	}

	public List<String> getTokenRun() {
		return this.tokenRun;
	}

	public int getSize() {
		return this.getTokenRun().size();
	}

	private boolean hasNext() {
		return this.tokenIn.get() < this.tokenRun.size();
	}

	private String getNext() {
		return this.tokenRun.get(this.tokenIn.getAndIncrement());
	}

	public ThreadUtil(List<String> tokenRun) {
		this.tokenRun = tokenRun;
		new Thread(this::poll).start();
	}

	public void update() {
	}

	public void finished() {
	}

	public void start(String token) {
	}
}