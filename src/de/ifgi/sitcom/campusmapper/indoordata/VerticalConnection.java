package de.ifgi.sitcom.campusmapper.indoordata;

import java.util.ArrayList;

import de.ifgi.sitcom.campusmapper.indoordata.geometry.Geometry;

/*
 * parent class of stairs and elevator
 * 
 * connects rooms and corridors of different floors
 * 
 */
public class VerticalConnection extends Connection{

	private ArrayList<Geometry> destinations;
	
	public VerticalConnection(int type, Geometry geometry) {
		super(type, geometry);
	}

	public ArrayList<Geometry> getDestinations() {
		return destinations;
	}


	public void setDestinations(ArrayList<Geometry> destinations) {
		this.destinations = destinations;
	}

	
}
