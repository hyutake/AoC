package AoC.day5;

public class MappedValue {
    private double src;
    private double dest;
    private double range;

    public MappedValue(double src, double range) {
        this.src = src;
        this.dest = -1;
        this.range = range;
    }
    public MappedValue(double src, double dest, double range) {
        this.src = src;
        this.dest = dest;
        this.range = range;
    }

    public double getSrc() {
        return src;
    }
    public void setSrc(double src) {
        this.src = src;
    }
    public double getDest() {
        return dest;
    }
    public void setDest(double dest) {
        this.dest = dest;
    }
    public double getRange() {
        return range;
    }
    public void setRange(double range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "{\n\tdest: " + dest + "\n\tsrc: " + src + "\n\trange: " + range + "\n}";
    }

}
