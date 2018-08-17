package com.juliuskrah.distance;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.distance.DistanceOp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DistanceApplication {
	private static final Logger log = LoggerFactory.getLogger(DistanceApplication.class);
	static GeometryFactory fact = new GeometryFactory();
	static WKTReader wktRdr = new WKTReader(fact);

	public static void main(String[] args) {
		SpringApplication.run(DistanceApplication.class, args);
	}
	
	@GetMapping(path = "/distance/start/@{start}/dest/@{dest}", produces = "application/json")
	public double points(@PathVariable String start, @PathVariable String dest) {
		double distance = 0.0;
		String[] pointA = start.split(",");
		String[] pointB = dest.split(",");
		String wktA = "POINT (" + pointA[0] + " " + pointA[1] +")";
		String wktB = "POINT (" + pointB[0] + " " + pointB[1] +")";
		
		try {
			Geometry A = wktRdr.read(wktA);
			Geometry B = wktRdr.read(wktB);

			log.info("Geometry A: " + A);
			log.info("Geometry B: " + B);
			DistanceOp distOp = new DistanceOp(A, B);
			distance = distOp.distance();
			
		} catch (ParseException e) {
			// ignore
		}
		
		return distance;
	}
	
}
