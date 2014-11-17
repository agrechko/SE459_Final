package sensors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import objectsdto.CellData;
import objectsdto.Coord;

public class FloorPlan {

    Logger logger = Logger.getLogger("main");
    private FileInputStream inputfile;
    private static final String OUTPUTFILE = "floorplan.xml";
    private String xmlfilename;
    private Map<Coord, CellData> grid = new HashMap<Coord, CellData>();

    public FloorPlan() throws IOException {
        createInputFileStream();
    }

    public FloorPlan(final String floorPlanLocation) throws IOException {
        xmlfilename = floorPlanLocation;
        createInputFileStream();
    }

    public final CellData get(final Coord c) {
        return grid.get(c);
    }

    public final void put(final Coord c, final CellData cd) {
        grid.put(c, cd);
    }

    public final Set<Coord> gridSet() {
        return grid.keySet();
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
            logger.log(Level.SEVERE,
                    "ERROR: XML file location does not exist: ", e);
            throw new IOException();
        }

    }

    private CellData parseAttributes(final XMLStreamReader reader) {
        CellData cd = new CellData();
        int ac = reader.getAttributeCount();
        for (int i = 0; i < ac; i++) {
            String atName = reader.getAttributeLocalName(i);
            Integer atValue = Integer.parseInt(reader.getAttributeValue(i)
                    .trim());
            // <cell xs='7' ys='8' ss='1' ps='2121 ' ds='1' cs='0' />
            logger.log(Level.FINE, "ATTRIBUTE: " + atName + ", " + atValue);
            switch (atName) {
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
                for (char c : String.valueOf(atValue).toCharArray()) {
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
            default:
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
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (reader.getLocalName().equalsIgnoreCase("cell")) {
                        CellData cd = parseAttributes(reader);
                        grid.put(new Coord(cd.getCellX(), cd.getCellY()), cd);
                    }
                }
            }
        } catch (XMLStreamException e) {
            logger.log(Level.SEVERE, "Reading XML from floorplan file: ", e);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Opening floorplan xml for reading: ", e);
        }

    }

    private void map2xml() throws XMLStreamException, IOException {
        /*
         * Example of xml cell data <cell xs='7' ys='8' ss='1' ps='2121 ' ds='1'
         * cs='0' />
         */
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        XMLStreamWriter xtw = null;
        try {
            xtw = xof.createXMLStreamWriter(new FileWriter(OUTPUTFILE));
            xtw.writeStartDocument("1.0");
            xtw.writeCharacters("\r\n");
            xtw.writeStartElement("home");
            xtw.writeCharacters("\r\n");
            xtw.writeStartElement("floor");
            xtw.writeAttribute("level", "1");
            xtw.writeCharacters("\r\n");
            for (int i=0; i < 1000; i++) {
            	for (int j=0; j < 1000; j++) {
	                CellData cd = grid.get(new Coord(j, i));
	                if (cd != null) {
		                xtw.writeStartElement("cell");
		                xtw.writeAttribute("xs", Integer.toString(cd.getCellX()));
		                xtw.writeAttribute("ys", Integer.toString(cd.getCellY()));
		                xtw.writeAttribute("ss", Integer.toString(cd.getSurface()));
		                StringBuilder sb = new StringBuilder();
		                for (int p : cd.getPaths()) {
		                    sb.append(Integer.toString(p));
		                }
		                xtw.writeAttribute("ps", sb.toString());
		                xtw.writeAttribute("ds", Integer.toString(cd.getDirt()));
		                if (cd.isChargingStation()) {
		                    xtw.writeAttribute("cs", "1");
		                } else  	{
		                    xtw.writeAttribute("cs", "0");
		                }
		                xtw.writeEndElement();
		                xtw.writeCharacters("\r\n");
	                }
            	}
            }
//            for (Coord c : grid.keySet()) {
//                CellData cd = grid.get(c);
//                xtw.writeStartElement("cell");
//                xtw.writeAttribute("xs", Integer.toString(cd.getCellX()));
//                xtw.writeAttribute("ys", Integer.toString(cd.getCellY()));
//                xtw.writeAttribute("ss", Integer.toString(cd.getSurface()));
//                StringBuilder sb = new StringBuilder();
//                for (int i : cd.getPaths()) {
//                    sb.append(Integer.toString(i));
//                }
//                xtw.writeAttribute("ps", sb.toString());
//                xtw.writeAttribute("ds", Integer.toString(cd.getDirt()));
//                if (cd.isChargingStation()) {
//                    xtw.writeAttribute("cs", "1");
//                } else  	{
//                    xtw.writeAttribute("cs", "0");
//                }
//                xtw.writeEndElement();
//                xtw.writeCharacters("\r\n");
//            }
            xtw.writeEndElement();
            xtw.writeCharacters("\r\n");
            xtw.writeEndElement();
            xtw.writeCharacters("\r\n");
            xtw.writeEndDocument();
            xtw.writeCharacters("\r\n");
            xtw.flush();
            xtw.close();
        } catch (XMLStreamException e) {
            logger.log(Level.SEVERE, "Writing XML to file: ", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE,
                    "Opening floorplan xml file for writing: ", e);
        }

    }

    public final Map<Coord, CellData> read() throws FileNotFoundException,
            XMLStreamException {
        xml2map();
        return grid;
    }

    public final void write() {
        try {
            map2xml();
        } catch (XMLStreamException e) {
            logger.log(Level.SEVERE, "Writing XML from file: ", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Opening floorplan file for writing: ", e);
        }
    }

}
