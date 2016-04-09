package gokhan.java.spring.batch.common;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ExecutorMonitor implements Runnable {
    private final ThreadPoolTaskExecutor tpte;

    public ExecutorMonitor(ThreadPoolTaskExecutor tpte) {
        this.tpte = tpte;
    }
    public void run() {
        while (true) {
            System.out.println("active:" + tpte.getActiveCount() + ", core:" + tpte.getCorePoolSize() + ", max:" + tpte.getMaxPoolSize() + ", pool:" + tpte.getPoolSize());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}