package sensors;

import java.util.HashMap;
import java.util.logging.*;

import objectsDTO.CellData;
import objectsDTO.Coord;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.logging.Level;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class FloorPlan {
	
   Logger logger = Logger.getLogger("main");
   private FileInputStream inputfile;
   private final static String outputfile = "floorplan.xml";
   private 	String xmlfilename;
   public HashMap<Coord, CellData> grid = new HashMap<Coord, CellData>(); 


	public FloorPlan() throws IOException {
	    createInputFileStream();
	}
	
	public FloorPlan(String floorPlanLocation) throws IOException {
	    try {
		    xmlfilename = floorPlanLocation;
		    createInputFileStream();
			read();
		} catch (XMLStreamException e) {
			logger.log(Level.SEVERE, "Reading floorplan xml: " + e.toString());
		}
	}

	private void createInputFileStream() throws IOException {
	    try {
	    	File f;
	    	if (xmlfilename == null) {
	    		f = new File("floorplan.xml"); 
	    	} else {
		    	f = new File(xmlfilename);
	    	}
	    	if (!f.exists()) {
	    		f.createNewFile();
	    	}
			
		} catch (IOException e) {
			logger.info("ERROR: XML file location does not exist !");
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
			logger.log(Level.FINE, "ATTRIBUTE: " + atName + ", " + atValue);
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
					if (atValue == 0) {
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
			inputfile = new FileInputStream(xmlfilename);
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
		} catch (XMLStreamException e) {
			logger.log(Level.SEVERE, "Reading XML from floorplan file: " + e.toString());
		} catch (FileNotFoundException e) {
		    logger.log(Level.SEVERE, "Opening floorplan xml for reading: " + e.toString());
	    }
		
	}
	
	private void map2xml() throws XMLStreamException, IOException {
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xtw = null;
		try {
			xtw = xof.createXMLStreamWriter(new FileWriter(outputfile));
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
		} catch (XMLStreamException e) {
			logger.log(Level.SEVERE, "Writing XML to file: " + e.toString());
		} catch (IOException e) {
		    logger.log(Level.SEVERE, "Opening floorplan xml file for writing: " + e.toString());
	    }
		
	}

	public HashMap<Coord, CellData> read() throws FileNotFoundException, XMLStreamException {
		xml2map();
		return grid;
	}
	
	public void write() {
		try {
			map2xml();
		} catch (XMLStreamException e) {
			logger.log(Level.SEVERE, "Writing XML from file: " + e.toString());
		} catch (IOException e) {
		    logger.log(Level.SEVERE, "Opening floorplan file for writing: " + e.toString());
	    }
	}

}
