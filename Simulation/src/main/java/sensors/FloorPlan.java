package sensors;

import java.util.HashMap;
import java.util.logging.*;

import objectsDTO.CellData;
import objectsDTO.Coord;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import utils.LogFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class FloorPlan {
	
   private Logger log;
   private LogFactory logger;
   private FileInputStream inputfile;
   private 	String xmlfilename;
   HashMap<Coord, CellData> grid = new HashMap<Coord, CellData>(); //the full floor plan grid that is read in from the xml

//	ArrayList<ArrayList<CellData>> grid; 
//	ArrayList<ArrayList<CellData>> sweeperMemoryGrid;//the floor plan the sweeper encounters

	public FloorPlan() throws IOException {
	    log = new LogFactory().generateLog();
	    createInputFileStream();
	}
	
	//Sample floorplan, floorPlanLocation is the physical drive location
	public FloorPlan(String floorPlanLocation) throws IOException {
	    log = new LogFactory().generateLog();
	    xmlfilename = floorPlanLocation;
	    createInputFileStream();
	}

	private void createInputFileStream() throws IOException {
	    try {
	    	if (xmlfilename == null) {
	    		File f = new File("floorplan.xml"); // System default name
	    	}
	    	File f = new File(xmlfilename);
	    	if (!f.exists()) {
	    		f.createNewFile();
	    	}
			inputfile = new FileInputStream(xmlfilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("ERROR: XML file location does not exist !");
			throw new IOException();			
		}

	}	
	
	
	private void xml2map() throws XMLStreamException, FileNotFoundException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
	 	XMLStreamReader reader;
		try {
			reader = factory.createXMLStreamReader(inputfile);
			while (reader.hasNext()) {
				int event = reader.next();
				switch(event) {
		 		case XMLStreamConstants.START_ELEMENT:
//		 			<cell xs='7' ys='8' ss='1' ps='2121 ' ds='1' cs='0' />
					if (reader.getLocalName().equalsIgnoreCase("cell")) {
						CellData cd = new CellData();
						Coord xy = new Coord();
						int ac = reader.getAttributeCount();
						for (int i=0; i < ac; i++) {
							String atName = reader.getAttributeLocalName(i);
							Integer atValue = Integer.parseInt(reader.getAttributeValue(i).trim());
							log.info( "ATTRIBUTE: " + atName + ", " + atValue);
							switch(reader.getAttributeLocalName(i)) {
								case "xs": 
									xy.setx(atValue);
									cd.setCellX(atValue);
								break;
								case "ys": 
									xy.sety(atValue);
  								    cd.setCellY(atValue);
								break;
								case "ss": 
									cd.setSurface(atValue);
								break;
								case "ps":								   
									int count = 0;
									int[] a = new int[4];
									for (char c: String.valueOf(atValue).toCharArray()) {
										a[count] = Character.getNumericValue(c);
										count++;
									}
									cd.setPaths(a);
								break;
								case "ds": 
									cd.setDirt(atValue);
								break;							
								case "cs": 
									if (atValue.equals("0")) {
										cd.setChargingStation(false);
									} else {
										cd.setChargingStation(true);
									}
								break;								
							}							
						}
//						log.info(xy.getx() + ":" + xy.gety());
						grid.put(xy, cd);
					}
					break;			
				case XMLStreamConstants.END_ELEMENT:
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//write arraylist to xml to file
	public HashMap<Coord, CellData> read() throws FileNotFoundException, XMLStreamException {
		xml2map();
		return grid;
	}
	
	
	//write arraylist to xml to file
	public void write(ArrayList<ArrayList<CellData>> grid) {
		
	}
	
	//prints arraylist to console
	public void print(ArrayList<ArrayList<CellData>> grid) {
		
	}


	public static void main(String[] args) throws XMLStreamException {
		try {
			FloorPlan f = new FloorPlan("sample_floorplan.xml");
			HashMap<Coord, CellData> g = f.read();
			Coord a = new Coord(0,0);
			Coord b = new Coord(0,0);
			if (a.equals(b)) {
				System.out.println("True");
			} else {
				System.out.println("False");
			}
//			System.out.println(g.get(a));
			for (Coord c: g.keySet()) {
				CellData xy = g.get(c);
				if (xy.getCellX() == 0 && xy.getCellY() == 0) {
					System.out.println(c.getx() + ":" + c.gety());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
