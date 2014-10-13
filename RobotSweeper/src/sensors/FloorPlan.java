package sensors;

import java.util.HashMap;
import java.util.logging.*;

import objectsDTO.CellData;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.LogManager;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import utils.ProjectLogFormat;


public class FloorPlan {
	
	Logger log;
	HashMap<int[], CellData> fpmap = new HashMap<int[], CellData>();

//	ArrayList<ArrayList<CellData>> grid;//the full floor plan grid that is read in from the xml
//	ArrayList<ArrayList<CellData>> sweeperMemoryGrid;//the floor plan the sweeper encounters
	
	//floorPlanLocation is the physical drive location
	public FloorPlan(String floorPlanLocation)
	{
	    generateLog();
	}

	private void generateLog() {
		Logger log = Logger.getAnonymousLogger();
		log.setUseParentHandlers(false);
		log.setLevel(Level.ALL);
		ConsoleHandler hdlr = new ConsoleHandler();
		hdlr.setFormatter(new ProjectLogFormat());
		log.addHandler(hdlr);	
	}
	
	public void readfloorplan(String inputfile) throws XMLStreamException, FileNotFoundException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
	 	XMLStreamReader reader;
		try {
			reader = factory.createXMLStreamReader(inputfile, new FileInputStream(inputfile));
			while (reader.hasNext()) {
				int event = reader.next();
				switch(event) {
		 		case XMLStreamConstants.START_ELEMENT:
//		 			<cell xs='7' ys='8' ss='1' ps='2121 ' ds='1' cs='0' />
					if (reader.getLocalName().equalsIgnoreCase("cell")) {
						CellData cd = new CellData();
						int[] xy = new int[2];
						int ac = reader.getAttributeCount();
						for (int i=0; i < ac; i++) {							
							log.info("ATTRIBUTE: " + reader.getAttributeLocalName(i) + ", " + reader.getAttributeValue(i));
							switch(reader.getAttributeLocalName(i)) {
								case "xs": xy[0] =  Integer.parseInt(reader.getAttributeValue(i));
								break;
								case "ys": xy[1] =  Integer.parseInt(reader.getAttributeValue(i));
								break;
								case "ss": cd.setSurface(Integer.parseInt(reader.getAttributeValue(i)));
								break;
								case "ps": 
									int count = 0;
									int[] a = new int[4];
									for (char c: reader.getAttributeValue(i).toCharArray()) {
										a[count] = Character.getNumericValue(c);
										count++;
									}
									cd.setPaths(a);
								break;
								case "ds": cd.setDirt(Integer.parseInt(reader.getAttributeValue(i)));
								break;							
								case "cs": 
									if (reader.getAttributeValue(i).equals("0")) cd.setChargingStation(false);
									else cd.setChargingStation(true);
								break;								
							}							
						}
						fpmap.put(xy, cd);
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
	public void writeFloorPlan(ArrayList<ArrayList<CellData>> grid)
	{
		
	}
	
	//prints arraylist to console
	public void printFloorPlan(ArrayList<ArrayList<CellData>> grid)
	{
		
	}


	public static void main(String[] args) {
		
	}
}
