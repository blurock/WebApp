package info.esblurock.react.mechanisms.chemkin.entity;

import info.esblurock.react.common.ReadToString;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import com.google.appengine.api.datastore.Entity;

public class MechanismEntityFactory {
	private boolean restrictNameLength = true;
	private int maxNameLength = 30;

	private String MechanismData = "MechanismData";
	private String IndexName = "Index";
	private String Mechanism = "Mechanism";
	private String Thermodynamics = "Thermodynamics";
	private String Transport = "Transport";
	private String SourceDate = "SourceDate";
	private String LineDescription = "LineDescription";
	private String FullDescription = "FullDescription";
	private String EntryDate = "EntryDate";

	private String indexname = null;
	private Date entrydate = null;
	private Date sourcedate = null;
	
	private String mechanism = null;
	private String thermodynamics = null;
	private String transport = null;
	
	private String linedescription = null;
	private String description = null;

	public void init() {
		indexname = null;
		entrydate = null;
		sourcedate = null;
		mechanism = null;
		thermodynamics = null;
		transport = null;
		linedescription = null;
		description = null;
		
	}
	
	public MechanismEntityFactory() {
	}
	/**
	 * @param source
	 *            The short index name of the person/institute/university
	 * @param mechanism
	 *            Usually the species that the mechanism is modeling (but could
	 *            also be PRE, JET-A...)
	 * @param year
	 *            /version of the mechanism
	 * @return concatenated string (must be less than 20 characters)
	 * @throws IOException
	 */
	public String buildName(String source, String mechanism, String version)
			throws IOException {
		StringBuilder build = new StringBuilder();

		build.append(source);
		build.append("-");
		build.append(mechanism);
		build.append("-");
		build.append(version);
		String name = build.toString();
		if (restrictNameLength && name.length() > maxNameLength) {
			throw new IOException("Name exceeds " + maxNameLength
					+ " characters");
		}
		return name;
	}

	public Date buildDate(int year, int month, int day) throws ParseException {
		Date date = null;
		if (day != 0) {
			String twodigits = "%4d%02d%02d";
			String dateS = String.format(twodigits, year, month, day);
			DateFormat formatter = new SimpleDateFormat("YYYYMMDD");
			date = formatter.parse(dateS);
		} else if (month != 0) {
			String twodigits = "%4d%02d";
			String dateS = String.format(twodigits, year, month);
			DateFormat formatter = new SimpleDateFormat("YYYYMM");
			date = formatter.parse(dateS);

		} else {
			String twodigits = "%4d";
			String dateS = String.format(twodigits, year);
			DateFormat formatter = new SimpleDateFormat("YYYY");
			date = formatter.parse(dateS);
		}
		return date;
	}

	public Date buildSourceDate(StringTokenizer tok) throws ParseException {
		int year = 0;
		int month = 0;
		int day = 0;
		String sourcedateS = tok.nextToken();
		StringTokenizer datetok = new StringTokenizer(sourcedateS, " ");
		if (datetok.hasMoreElements()) {
			String yearS = datetok.nextToken();
			Integer yearI = new Integer(yearS);
			year = yearI.intValue();
		}
		if (datetok.hasMoreElements()) {
			String monthS = datetok.nextToken();
			Integer monthI = new Integer(monthS);
			month = monthI.intValue();
		}
		if (datetok.hasMoreElements()) {
			String dayS = datetok.nextToken();
			Integer dayI = new Integer(dayS);
			day = dayI.intValue();
		}

		Date sourcedate = this.buildDate(year, month, day);

		return sourcedate;

	}

	String buildIndexName(StringTokenizer tok) throws IOException {
		String indexname = null;
		String namespec = tok.nextToken();
		StringTokenizer spectok = new StringTokenizer(namespec, " ");
		if (spectok.countTokens() == 3) {
			String sourceS = spectok.nextToken();
			String mechanismS = spectok.nextToken();
			String versionS = spectok.nextToken();
			indexname = buildName(sourceS, mechanismS, versionS);
		} else {
			throw new IOException(
					"Expected 3 elements: source, mechanism, version");
		}
		return indexname;
	}

	public void readFromSourceSpecification(String spec)
			throws IOException, ParseException {

		init();
		ReadToString read = new ReadToString();
		String source = read.parseFromLineSpecification(spec);

		StringTokenizer tok = new StringTokenizer(source, "\n");
		String indexS = tok.nextToken();
		if (indexS.compareToIgnoreCase(IndexName) == 0) {
			indexname = buildIndexName(tok);
			String dateS = tok.nextToken();
			if (dateS.compareToIgnoreCase(SourceDate) == 0) {
				sourcedate = buildSourceDate(tok);
				String linedS = tok.nextToken();
				if (linedS.compareToIgnoreCase(LineDescription) == 0) {
					linedescription = tok.nextToken();

					String mechanismS = tok.nextToken();
					if (mechanismS.compareToIgnoreCase(Mechanism) == 0) {
						mechanism = tok.nextToken();
						
						String thermodynamicsS = tok.nextToken();
						if (thermodynamicsS.compareToIgnoreCase(Thermodynamics) == 0) {
							thermodynamics = tok.nextToken();
							
							String transportS = tok.nextToken();
							if (transportS.compareToIgnoreCase(Transport) == 0) {
								transport = tok.nextToken();
								
								String fulldS = tok.nextToken();
								if (fulldS.compareToIgnoreCase(FullDescription) == 0) {
									StringBuilder build = new StringBuilder();
									while (tok.hasMoreElements()) {
										build.append(tok.nextElement());
										build.append("\n");
									}
									description = build.toString();

								} else {
									throw new IOException("Expected "
											+ FullDescription + " found '"
											+ fulldS + "'");
								}
							} else {
								throw new IOException("Expected " + Transport
										+ " found '" + transportS + "'");
							}
						} else {
							throw new IOException("Expected " + Thermodynamics
									+ " found '" + thermodynamicsS + "'");
						}
					} else {
						throw new IOException("Expected " + Mechanism
								+ " found '" + mechanismS + "'");
					}
				} else {
					throw new IOException("Expected " + LineDescription
							+ " found '" + linedS + "'");
				}
			} else {
				throw new IOException("Expected " + SourceDate + " found '"
						+ dateS + "'");
			}
		} else {
			throw new IOException("Expected " + IndexName + " found '"
					+ indexS + "'");
		}
	}
	public Entity createEntity() {
		
		entrydate = new Date(System.currentTimeMillis());
		Entity entity = new Entity(MechanismData);
		entity.setProperty(IndexName,indexname);
		entity.isUnindexedProperty(IndexName);
		entity.setProperty(EntryDate,entrydate);
		entity.setProperty(SourceDate,sourcedate);
		entity.setProperty(Mechanism,mechanism);
		entity.setProperty(Thermodynamics,thermodynamics);
		entity.setProperty(Transport,transport);
		entity.setProperty(LineDescription,linedescription);
		entity.setProperty(FullDescription,description);

		return entity;

	}
	/*
	public MechanismEntity createMechanismEntity() {
		
		entrydate = new Date(System.currentTimeMillis());
		MechanismEntity entity = new MechanismEntity();
		entity.setIndexname(indexname);
		entity.setEntrydate(entrydate);
		entity.setSourcedate(sourcedate);
		entity.setMechanismSource(mechanism);
		entity.setThermodynamicsSource(thermodynamics);
		entity.setTransportSource(transport);
		entity.setLinedescription(linedescription);
		entity.setDescription(description);

		return entity;

	}
*/
}
