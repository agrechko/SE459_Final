package sensors;

import java.util.HashMap;
import java.util.logging.*;

import objectsDTO.CellData;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class FloorPlan {
	
	
	HashMap<int[], CellData> fpmap = new HashMap<int[], CellData>();

//	ArrayList<ArrayList<CellData>> grid;//the full floor plan grid that is read in from the xml
//	ArrayList<ArrayList<CellData>> sweeperMemoryGrid;//the floor plan the sweeper encounters
	
	//floorPlanLocation is the physical drive location
//	public FloorPlan(String floorPlanLocation)
//	{
//		grid = new ArrayList<ArrayList<CellData>>();
//	}
//	
//	//write arraylist to xml to file
//	public void writeFloorPlan(ArrayList<ArrayList<CellData>> grid)
//	{
//		
//	}
//	
//	//prints arraylist to console
//	public void printFloorPlan(ArrayList<ArrayList<CellData>> grid)
//	{
//		
//	}
//}


public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
	Logger log = Logger.getAnonymousLogger();
	log.setLevel(Level.ALL);
	ConsoleHandler hdlr = new ConsoleHandler();
	hdlr.setLevel(Level.ALL);
	SimpleFormatter logformat = new SimpleFormatter();
	hdlr.setFormatter(logformat);
	log.addHandler(hdlr);	
	
	String inputfile = "sample_floorplan.xml";
	String tags;
	XMLInputFactory factory = XMLInputFactory.newInstance();
//	XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream("sample_floorplan.xml"));
 	XMLStreamReader reader;
	try {
		reader = factory.createXMLStreamReader(inputfile, new FileInputStream(inputfile));
		while (reader.hasNext()) {
			int event = reader.next();
			switch(event) {
	 		case XMLStreamConstants.START_ELEMENT:
	            log.info("START_ELEMENT: " + reader.getLocalName());			
				if (reader.getLocalName().equalsIgnoreCase("cell")) {
					CellData c = new CellData();
					int ac = reader.getAttributeCount();
					for (int i=0; i < ac; i++) {
						log.info(reader.getAttributeLocalName(i) + ", " + reader.getAttributeValue(i)); 
					}
//					log.info("CELL ATTRIBUTES: " + ac);
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
}
