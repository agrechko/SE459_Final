package sensors;

import java.util.HashMap;
import java.util.logging.*;

import objectsDTO.CellData;
import objectsDTO.Coord;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import utils.LogFactory;
import java.util.logging.Level;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class FloorPlan {
	
   private Logger log;
   private LogFactory logger;
   private FileInputStream inputfile;
   private final static String outputfile = "floorplan.xml";
   private 	String xmlfilename;
   public HashMap<Coord, CellData> grid = new HashMap<Coord, CellData>(); //the full floor plan grid that is read in from the xml

//	ArrayList<ArrayList<CellData>> grid; 
//	ArrayList<ArrayList<CellData>> sweeperMemoryGrid;//the floor plan the sweeper encounters

	public FloorPlan() throws IOException {
	    log = new LogFactory().generateLog("Floorplan", Level.INFO);
	    createInputFileStream();
	}
	
	//Sample floorplan, floorPlanLocation is the physical drive location
	public FloorPlan(String floorPlanLocation) throws IOException {
	    try {
		    log = new LogFactory().generateLog();
		    xmlfilename = floorPlanLocation;
		    createInputFileStream();
			read();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
    private CellData parseAttributes(XMLStreamReader reader) {
		CellData cd = new CellData();
		int ac = reader.getAttributeCount();
		for (int i=0; i < ac; i++) {
			String atName = reader.getAttributeLocalName(i);
			Integer atValue = Integer.parseInt(reader.getAttributeValue(i).trim());
// 			<cell xs='7' ys='8' ss='1' ps='2121 ' ds='1' cs='0' />
			log.log(Level.FINE, "ATTRIBUTE: " + atName + ", " + atValue);
			switch(atName) {
				case "xs": 
					cd.setCellX(atValue);
				break;
				case "ys": 
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
		return cd;
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
						if (reader.getLocalName().equalsIgnoreCase("cell")) {
							CellData cd = parseAttributes(reader);
							grid.put(new Coord(cd.getCellX(), cd.getCellY()), cd);
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
	
	private void map2xml() throws XMLStreamException, FileNotFoundException {
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xtw = null;
		try {
			xtw = xof.createXMLStreamWriter(new FileWriter(outputfile));
//			xtw = xof.createXMLStreamWriter(System.out);
			xtw.writeStartDocument("1.0");
			xtw.writeStartElement("home");
			xtw.writeStartElement("floor");
			xtw.writeAttribute("level", "1");
			for (Coord c: grid.keySet()) {
				CellData cd = grid.get(c);
//	 			<cell xs='7' ys='8' ss='1' ps='2121 ' ds='1' cs='0' />
				xtw.writeStartElement("cell");
				xtw.writeAttribute("xs", Integer.toString(cd.getCellX()));
				xtw.writeAttribute("ys", Integer.toString(cd.getCellY()));
				xtw.writeAttribute("ss", Integer.toString(cd.getSurface()));
				StringBuilder sb = new StringBuilder();
				for (int i: cd.getPaths()) {
					sb.append(Integer.toString(i));
				}
				xtw.writeAttribute("ps", sb.toString());
				xtw.writeAttribute("ds", Integer.toString(cd.getDirt()));
				if (cd.isChargingStation()) {
					xtw.writeAttribute("cs", "1");	
				} else {
					xtw.writeAttribute("cs", "0");
				}
				xtw.writeEndElement();
			}
			xtw.writeEndElement();
			xtw.writeEndElement();
			xtw.writeEndDocument();
			xtw.flush();
			xtw.close();
//		    System.out.println();
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
	public void write() {
		try {
			map2xml();
		} catch (FileNotFoundException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//prints arraylist to console
	public void print(ArrayList<ArrayList<CellData>> grid) {
		
	}


	public static void main(String[] args) throws XMLStreamException {
		try {
			FloorPlan f = new FloorPlan("sample_floorplan.xml");
//			HashMap<Coord, CellData> g = f.read();
			Coord a = new Coord(0,0);
			Coord b = new Coord(0,0);
			if (a.equals(b)) {
				System.out.println("True");
			} else {
				System.out.println("False");
			}
//			CellData xy = g.get(new Coord(1,2));
//			System.out.println(xy.getCellX());
			for (Coord c: f.grid.keySet()) {
				CellData xy1 = f.grid.get(c);
				CellData xy2 = f.grid.get(a);
				if (xy1.getCellX() == 0 && xy1.getCellY() == 0) {
					System.out.println(a.equals(c));
					System.out.println(c.getx() + ":" + c.gety());
					System.out.println(xy2);
				}
			}
			f.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
