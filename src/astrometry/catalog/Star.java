package astrometry.catalog;

public class Star {
    String popularName = null;
    String name;
    double vmag;
    //Right ascension in radians
    double ra;
    //Declination in radians
    double dec;

    public Star() {
	super();
    }

    public Star(String... values) {
	super();
	this.name = values[0];
	this.vmag = Double.parseDouble(values[1]);
	this.ra = Math.toRadians(Double.parseDouble(values[2]));
	this.dec = Math.toRadians(Double.parseDouble(values[3]));
	if (values.length > 4)
	    this.popularName = values[4];
    }

    public Star(String name, double vmag, double ra, double dec) {
	super();
	this.name = name;
	this.vmag = vmag;
	this.ra = ra;
	this.dec = dec;
    }

    public String getName() {
	return name;
    }

    public String getBestName() {
	if (popularName != null)
	    return popularName;
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public double getVmag() {
	return vmag;
    }

    public void setVmag(double vmag) {
	this.vmag = vmag;
    }

    public double getRa() {
	return ra;
    }

    public void setRa(double ra) {
	this.ra = ra;
    }

    public double getDec() {
	return dec;
    }

    public void setDec(double dec) {
	this.dec = dec;
    }

    public double getX() {
	return Math.cos(ra) * Math.cos(dec);
    }

    public double getY() {
	return Math.sin(ra) * Math.cos(dec);
    }

    public double getZ() {
	return Math.sin(dec);
    }

    public String getPopularName() {
	return popularName;
    }

    public void setPopularName(String popularName) {
	this.popularName = popularName;
    }

    public String toString() {
	return "dec: " + dec + ", x: " + getX() + ", y: " + getY() + ", dist: " + Math.sqrt(getX() * getX() + getY() * getY());
    }

}
