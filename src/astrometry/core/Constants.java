package astrometry.core;

import geodesy.util.Converter;

public class Constants {

    /**
     * Flags
     */
    public static final boolean verbose = false;
    public static final boolean writeFile = false;

    /**
     * Constants
     */
    public static final double dpi = Math.PI * 2d;
    public static final double hpi = Math.PI / 2d;

    /**
     * First set of angle constants
     */
    public static final double psi1 = Converter.getRadiansFromArc(0, 0, 5038.47875d);
    public static final double psi2 = Converter.getRadiansFromArc(0, 0, 1.07259d);
    public static final double psi3 = Converter.getRadiansFromArc(0, 0, 0.001147d);

    public static final double w1 = Converter.getRadiansFromArc(0, 0, 0.02524d);
    public static final double w2 = Converter.getRadiansFromArc(0, 0, 0.05127d);
    public static final double w3 = Converter.getRadiansFromArc(0, 0, 0.007726d);

    public static final double epsilon0 = Converter.getRadiansFromArc(0, 0, 84381.448d);
    public static final double epsilon1 = Converter.getRadiansFromArc(0, 0, 46.84024d);
    public static final double epsilon2 = Converter.getRadiansFromArc(0, 0, 0.00059d);
    public static final double epsilon3 = Converter.getRadiansFromArc(0, 0, 0.001813d);

    public static final double chi1 = Converter.getRadiansFromArc(0, 0, 10.5526d);
    public static final double chi2 = Converter.getRadiansFromArc(0, 0, 2.38064d);
    public static final double chi3 = Converter.getRadiansFromArc(0, 0, 0.001125d);

    /**
     * Euler angle constants
     */
    public static final double zeta0 = Converter.getRadiansFromArc(0, 0, 2.5976176);
    public static final double zeta1 = Converter.getRadiansFromArc(0, 0, 2306.0809506);
    public static final double zeta2 = Converter.getRadiansFromArc(0, 0, 0.3019015);
    public static final double zeta3 = Converter.getRadiansFromArc(0, 0, 0.0179663);
    public static final double zeta4 = Converter.getRadiansFromArc(0, 0, 0.0000327);
    public static final double zeta5 = Converter.getRadiansFromArc(0, 0, 0.0000002);

    public static final double z0 = Converter.getRadiansFromArc(0, 0, 2.5976176);
    public static final double z1 = Converter.getRadiansFromArc(0, 0, 2306.0803226);
    public static final double z2 = Converter.getRadiansFromArc(0, 0, 1.0947790);
    public static final double z3 = Converter.getRadiansFromArc(0, 0, 0.0182273);
    public static final double z4 = Converter.getRadiansFromArc(0, 0, 0.0000470);
    public static final double z5 = Converter.getRadiansFromArc(0, 0, 0.0000003);

    public static final double theta1 = Converter.getRadiansFromArc(0, 0, 2004.1917476);
    public static final double theta2 = Converter.getRadiansFromArc(0, 0, 0.4269353);
    public static final double theta3 = Converter.getRadiansFromArc(0, 0, 0.0418251);
    public static final double theta4 = Converter.getRadiansFromArc(0, 0, 0.0000601);
    public static final double theta5 = Converter.getRadiansFromArc(0, 0, 0.0000001);
}
