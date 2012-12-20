package astrometry.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

public class AstrometryCore {

    private static final String tab = "\t";

    private boolean paused = false;

    //Time in days
    private double t;
    private AngleCalc ac;

    private RealVector point;
    private RealVector current;
    BufferedWriter out;

    public AstrometryCore() {
	super();
    }

    public void init() {
	t = Parameters.params.getDaysT0();
	ac = new AngleCalc();

	//Initialize current point
	point = Parameters.params.getObsPoint();
	ac.computeAngles(t);
	current = getPointCoordinates(point);

	if (Constants.writeFile) {
	    try {
		// Create file 
		File f = new File("data.txt");
		f.delete();
		f.createNewFile();
		FileWriter fstream = new FileWriter("data.txt");
		out = new BufferedWriter(fstream);

	    } catch (Exception e) {//Catch exception if any
		System.err.println("Error: " + e.getMessage());
	    }
	}
    }

    /**
     * Works out coordinates x,y,z from the ICRS to a system with precession
     * Matrix:
     * [x',y',z'] = Rz[chi] Rx[-w] Rz[-psi] Rx[epsilon] [x, y, z]
     * @param t
     */
    private void updateCoordinates(double t) {
	//Compute value of angles at t
	if (Constants.verbose)
	    System.out.print("Year: " + getCurrentYear() + "\t");
	ac.computeAngles(t);
	current = getPointCoordinates(point);

	if (Constants.writeFile) {
	    //Write to file
	    try {
		double[] data = current.getData();
		out.write(t + tab + data[0] + tab + data[1] + tab + data[2]);
		out.newLine();
	    } catch (IOException e) {
		System.err.println("Error: " + e.getMessage());
	    }
	}
    }

    private RealVector getPointCoordinates(RealVector point) {
	RealVector c;
	if (Parameters.getParams().euler) {
	    c = getRz(-ac.z).operate(getRy(ac.theta).operate(getRz(-ac.zeta).operate(point)));
	} else {
	    //Perform coordinate transform
	    c = getRz(ac.chi).operate(getRx(-ac.w).operate(getRz(-ac.psi).operate(getRx(ac.epsilon).operate(point))));
	}
	return c;
    }

    private RealMatrix getRx(double angle) {
	RealMatrix m = new Array2DRowRealMatrix(new double[][] { { 1, 0, 0 }, { 0, Math.cos(angle), Math.sin(angle) },
		{ 0, -Math.sin(angle), Math.cos(angle) } });

	return m;
    }

    private RealMatrix getRy(double angle) {
	RealMatrix m = new Array2DRowRealMatrix(new double[][] { { Math.cos(angle), 0, -Math.sin(angle) }, { 0, 1, 0 },
		{ Math.sin(angle), 0, Math.cos(angle) } });

	return m;
    }

    private RealMatrix getRz(double angle) {
	RealMatrix m = new Array2DRowRealMatrix(new double[][] { { Math.cos(angle), Math.sin(angle), 0 }, { -Math.sin(angle), Math.cos(angle), 0 },
		{ 0, 0, 1 } });

	return m;
    }

    public RealVector getPointFromCoordinates(double alpha, double delta, double r) {
	double x = r * Math.cos(alpha) * Math.cos(delta);
	double y = r * Math.sin(alpha) * Math.cos(delta);
	double z = r * Math.sin(delta);
	return new ArrayRealVector(new double[] { x, y, z });
    }

    public RealVector getCurrent() {
	return current;
    }

    public void setCurrent(RealVector current) {
	this.current = current;
    }

    public RealVector getPoint() {
	return point;
    }

    public void setPoint(RealVector point) {
	this.point = point;
    }

    /**
     * Get elapsed time in days
     * @return
     */
    public double getT() {
	return t;
    }

    /**
     * Get elapsed time in years
     * @return
     */
    public double getYears() {
	return t / 365.25d;
    }

    /**
     * Gets the current year taking as t0 the year 2000
     * @return
     */
    public int getCurrentYear() {
	return (2000 + (int) getYears());
    }

    /**
     * Works out time step and coordinates x,y,z from the ICRS to a system with precession
     * Matrix:
     * [x',y',z'] = Rz[chi] Rx[-w] Rz[-psi] Rx[epsilon] [x, y, z]
     */
    public void update() {
	if (!paused && t <= Parameters.params.getDaysT1()) {
	    updateCoordinates(t);
	    t += Parameters.params.getDaysDt();
	}
    }

    @Override
    protected void finalize() throws Throwable {
	if (out != null) {
	    out.flush();
	    out.close();
	}
	super.finalize();
    }

    public boolean isPaused() {
	return paused;
    }

    public void pause() {
	paused = true;
    }

    public void resume() {
	paused = false;
    }

}
