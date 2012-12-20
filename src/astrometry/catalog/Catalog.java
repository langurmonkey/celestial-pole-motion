package astrometry.catalog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private static final String file = "catalog.txt";
    private static final String separator = "[ \t\n\f\r]+";

    private List<Star> stars = new ArrayList<Star>();

    /**
     * Creates and initialized the catalog
     */
    public Catalog() {
	super();
	try {
	    loadStars(file);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    public void loadStars(String file) throws FileNotFoundException {
	BufferedReader br = new BufferedReader(new FileReader(new File(file)));

	try {
	    //Skip first line
	    br.readLine();
	    String line;
	    while ((line = br.readLine()) != null) {
		//Add star
		addStar(line);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	System.out.println("Catalog initialized, " + stars.size() + " stars.");
    }

    private void addStar(String line) {
	String[] st = line.split(separator);
	Star star = new Star(st);
	stars.add(star);
    }

    public List<Star> getStars(boolean hem) {
	if (hem)
	    return getStars(0, 90);
	else
	    return getStars(-90, 0);
    }

    public List<Star> getStars(double fromdec, double todec) {
	List<Star> res = new ArrayList<Star>();
	for (Star star : stars)
	    if (star.getDec() >= fromdec && star.getDec() <= todec)
		res.add(star);
	return res;
    }

}
