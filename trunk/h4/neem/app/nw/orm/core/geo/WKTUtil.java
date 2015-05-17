package nw.orm.core.geo;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class WKTUtil {

	public Geometry fromWktText(String text) throws ParseException{
		WKTReader wr = new WKTReader();
		return wr.read(text);
	}

}
