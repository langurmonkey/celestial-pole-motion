package astrometry.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.RealVector;

public class Parameters extends Properties {

    public static final double daysInYear = 365.25;
    public static final Parameters params = new Parameters();

    /**
     * These are the values for the default parameters
     */

    //dt in years
    public Double dt = 20d;

    //Initial and final times in years. t=0 -> J2000
    public Double t0 = -13000d;
    public Double t1 = 13000d;

    //North hemisphere -> true, south hemisphere -> false
    public Boolean hemisphere = true;

    //Euler angles or not
    public Boolean euler = true;

    /**
     * Initialize using parameters defined in file
     */
    public Parameters() {
	super();
	// Read properties file.
	try {
	    File props = new File("parameters.properties");
	    FileInputStream is = new FileInputStream(props);
	    load(is);
	    dt = Double.parseDouble((String) get("icrs.dt"));
	    t0 = Double.parseDouble((String) get("icrs.tini"));
	    t1 = Double.parseDouble((String) get("icrs.tfinal"));
	    hemisphere = ((String) get("icrs.hemisphere")).equals("north");
	    euler = ((String) get("icrs.euler")).equals("true");
	    is.close();
	} catch (IOException e) {
	    System.err.println(e);
	}
    }

    public void commit() {
	put("icrs.dt", dt.toString());
	put("icrs.tini", t0.toString());
	put("icrs.tfinal", t1.toString());
	put("icrs.hemisphere", hemisphere ? "north" : "south");
	put("icrs.euler", euler.toString());

	File props = new File("parameters.properties");
	FileOutputStream os;
	try {
	    os = new FileOutputStream(props);
	    this.store(os, "Parameters that persist the state of the program");
	    os.close();
	} catch (FileNotFoundException e) {
	    System.err.println(e);
	} catch (IOException e) {
	    System.err.println(e);
	}

    }

    public static Parameters getParams() {
	return params;
    }

    public double getDt() {
	return dt;
    }

    public void setDt(double dt) {
	this.dt = dt;
    }

    public double getDaysDt() {
	return dt * daysInYear;
    }

    public double getT0() {
	return t0;
    }

    public double getDaysT0() {
	return t0 * daysInYear;
    }

    public void setT0(double t0) {
	this.t0 = t0;
    }

    public double getT1() {
	return t1;
    }

    public double getDaysT1() {
	return t1 * daysInYear;
    }

    public void setT1(double t1) {
	this.t1 = t1;
    }

    public RealVector getObsPoint() {
	if (hemisphere) {
	    return new ArrayRealVector(new double[] { 0, 0, 1 });
	} else {
	    return new ArrayRealVector(new double[] { 0, 0, -1 });
	}
    }

    public static double getDaysinyear() {
	return daysInYear;
    }

}
