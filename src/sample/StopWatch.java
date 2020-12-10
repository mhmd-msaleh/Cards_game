package sample;

public class StopWatch implements Comparable<StopWatch> {
    private int second = 0;
    private int minute = 0;
    private int hour = 0;

    public void perSec() {
        second++;
        if (second == 60) {
            second = 0;
            minute++;
        }
        if (minute == 60) {
            minute = 0;
            hour++;
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return String.format("%2d:%2d:%2d", hour, minute, second);
    }

    @Override
    public int compareTo(StopWatch o) {
        if (hour > o.getHour() || hour == o.getHour() && minute > o.getMinute() || hour == o.getHour() && minute == o.getMinute() && second > o.getSecond())
            return -1;
        else if (hour == o.getHour() && minute == o.getMinute() && second == o.getSecond())
            return 0;
        else
            return 1;
    }
}
