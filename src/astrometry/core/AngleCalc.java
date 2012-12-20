package astrometry.core;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AngleCalc {
    private static final int UNIT_DEG = 0;
    private static final int UNIT_RAD = 1;
    private static final int UNIT_AS = 2;

    private static final int unit = UNIT_DEG;

    private static final Constants ct = null;
    private static final NumberFormat f = new DecimalFormat("#####0.000");

    public double psi;
    public double w;
    public double epsilon;
    public double chi;

    //Euler angles
    public double zeta;
    public double theta;
    public double z;

    public AngleCalc() {
	super();
    }

    public void computeAngles(double days) {
	//compute t in centuries, from January 1st 2000
	double t = days / 36525;
	double t2 = Math.pow(t, 2);
	double t3 = Math.pow(t, 3);

	if (Parameters.getParams().euler) {
	    double t4 = Math.pow(t, 4);
	    double t5 = Math.pow(t, 5);
	    zeta = (ct.zeta0 + t * ct.zeta1 + t2 * ct.zeta2 + t3 * ct.zeta3 - t4 * ct.zeta4 - t5 * ct.zeta5) % ct.dpi;
	    z = (-ct.z0 + t * ct.z1 + t2 * ct.z2 + t3 * ct.z3 + t4 * ct.z4 - t5 * ct.z5) % ct.dpi;
	    theta = (t * ct.theta1 - t2 * ct.theta2 - t3 * ct.theta3 - t4 * ct.theta4 - t5 * ct.theta5) % ct.dpi;
	} else {
	    //Compute angles
	    psi = (ct.psi1 * t - ct.psi2 * t2 - ct.psi3 * t3) % ct.dpi;
	    w = (ct.epsilon0 - ct.w1 * t + ct.w2 * t2 - ct.w3 * t3) % ct.dpi;
	    epsilon = (ct.epsilon0 - ct.epsilon1 * t - ct.epsilon2 * t2 + ct.epsilon3 * t3) % ct.dpi;
	    chi = (ct.chi1 * t - ct.chi2 * t2 - ct.chi3 * t3) % ct.dpi;
	}
	if (Constants.verbose)
	    System.out.println(toString());

    }

    @Override
    public String toString() {
	if (Parameters.getParams().euler) {
	    switch (unit) {
	    case UNIT_DEG:
		return "zeta: " + f.format(Math.toDegrees(zeta)) + "\ttheta: " + f.format(Math.toDegrees(theta)) + "\tz: "
			+ f.format(Math.toDegrees(z));
	    case UNIT_RAD:
		return "zeta: " + f.format(zeta) + "\ttheta: " + f.format(theta) + "\tz: " + f.format(z);
	    case UNIT_AS:
		return "zeta: " + f.format(toArcsec(zeta)) + "\ttheta: " + f.format(toArcsec(theta)) + "\tz: " + f.format(toArcsec(z));
	    }
	} else {
	    switch (unit) {
	    case UNIT_DEG:
		return "psi: " + f.format(Math.toDegrees(psi)) + "\tw: " + f.format(Math.toDegrees(w)) + "\te: " + f.format(Math.toDegrees(epsilon))
			+ "\tchi: " + f.format(Math.toDegrees(chi));
	    case UNIT_RAD:
		return "psi: " + f.format(psi) + "\tw: " + f.format(w) + "\te: " + f.format(epsilon) + "\tchi: " + f.format(chi);
	    case UNIT_AS:
		return "psi: " + f.format(toArcsec(psi)) + "\tw: " + f.format(toArcsec(w)) + "\te: " + f.format(toArcsec(epsilon)) + "\tchi: "
			+ f.format(toArcsec(chi));
	    }
	}
	return "";
    }

    private double toArcsec(double rad) {
	return Math.toDegrees(rad) * 3600;
    }
}
