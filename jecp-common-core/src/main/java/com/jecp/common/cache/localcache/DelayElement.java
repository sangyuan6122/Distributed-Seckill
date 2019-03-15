package com.jecp.common.cache.localcache;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @功能 实现Dealyed通用元素
 * @作者 WWT
 * @修改时间 2018年6月2日
 */
public class DelayElement<T> implements Delayed {
    /** Base of nanosecond timings, to avoid wrapping */
    private static final long NANO_ORIGIN = System.nanoTime();

    /**
     * Returns nanosecond time offset by origin
     */
    final static long now() {
        return System.nanoTime() - NANO_ORIGIN;
    }

    /**
     * Sequence number to break scheduling ties, and in turn to guarantee FIFO order among tied
     * entries.
     */
    private static final AtomicLong sequencer = new AtomicLong(0);

    /** Sequence number to break ties FIFO */
    private  long sequenceNumber;

    /** The time the task is enabled to execute in nanoTime units */
    private final long time;

    private final T element;

    public DelayElement(T submit, long timeout) {
        this.time = now() + timeout;
        this.element = submit;
        this.sequenceNumber = sequencer.getAndIncrement();
    }

    public T getElement() {
        return this.element;
    }

    public long getDelay(TimeUnit unit) {
        long d = unit.convert(time - now(), TimeUnit.NANOSECONDS);
        return d;
    }

    public int compareTo(Delayed other) {
        if (other == this) // compare zero ONLY if same object
            return 0;
        if (other instanceof DelayElement) {
            DelayElement x = (DelayElement) other;
            long diff = time - x.time;
            if (diff < 0)
                return -1;
            else if (diff > 0)
                return 1;
            else if (sequenceNumber < x.sequenceNumber)
                return -1;
            else
                return 1;
        }
        long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }
    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DelayElement other = (DelayElement) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}
	
	public static void main(String[] args) {
	
		DelayElement<Object> d2=new DelayElement<Object>(new Object(),9000000000L);
		DelayElement d3=new DelayElement("d12@",9000000000L);
		System.out.println(TimeUnit.SECONDS.convert((System.nanoTime()+9000000000L)-System.nanoTime(),TimeUnit.NANOSECONDS));
		System.out.println(d2.sequenceNumber);
		System.out.println(d2.getDelay(TimeUnit.NANOSECONDS));


	}
}