package com.juliuskrah.distance;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.distance.DistanceOp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DistanceApplication {
	static GeometryFactory fact = new GeometryFactory();
	static WKTReader wktRdr = new WKTReader(fact);

	public static void main(String[] args) {
		SpringApplication.run(DistanceApplication.class, args);
	}
	
	@GetMapping(path = "/points/start/@{start}/dest/@{dest}", produces = "application/json")
	public double points(@PathVariable String start, @PathVariable String dest) {
		double distance = 0.0;
		String[] pointA = start.split(",");
		String[] pointB = dest.split(",");
		String wktA = "POINT (" + pointA[0] + " " + pointA[1] +")";
		String wktB = "POINT (" + pointB[0] + " " + pointB[1] +")";
		
		try {
			Geometry A = wktRdr.read(wktA);
			Geometry B = wktRdr.read(wktB);

			System.out.println("Geometry A: " + A);
			System.out.println("Geometry B: " + B);
			DistanceOp distOp = new DistanceOp(A, B);
			distance = distOp.distance();
			
		} catch (ParseException e) {
			// ignore
		}
		
		return distance;
	}
	
	@GetMapping("/")
	public String index() {
		// POINT (LON LAT)
		// Unis: geodetic units (degrees of arc)
		String wktA = "POINT (5.8307467 -1.2099742)";
		String wktB = "POINT (5.5790564 -0.7073872)";

		try {
			Geometry A = wktRdr.read(wktA);
			Geometry B = wktRdr.read(wktB);

			System.out.println("Geometry A: " + A);
			System.out.println("Geometry B: " + B);
			DistanceOp distOp = new DistanceOp(A, B);
			double distance = distOp.distance();
			System.out.println("Distance = " + distance);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "alive and well";
	}
}
