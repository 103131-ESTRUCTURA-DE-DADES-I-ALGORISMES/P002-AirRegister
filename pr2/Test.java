package pr2;

import static utilsProva.UtilsProva.*;
import java.util.*;


public class Test {
	
	public static void main (String [] args) {
		long time = System.currentTimeMillis();
		int l = 500000;
		Company [] companies = new Company[l];
		Random r = new Random();

		for(int i=0; i<l; i++){
			companies[i] = new Company("Company"+i, "Address"+i);
		}

		Company [] unknownCompanies = new Company[l];

		for(int i=0; i<l; i++){
			unknownCompanies[i] = new Company("unknowCompany"+i, "unknowAddress"+i);
		}

		// Company [] companies = {
		// 		new Company("AIR MEATBALL","Canada"),
		// 		new Company("AEROPLOF","Russia"),
		// 		new Company("LIMPBIRD","North Korea"),
		// 		new Company("FEATHERLESS","Spain")
		// };
		
		// Company [] unknownCompanies = {
		// 		new Company("FLY HIGH","North Korea"),
		// 		new Company("AIR xMEATBALL","Canada"),
		// 		new Company("LIMPBRD","North Korea"),
		// 		new Company("FEATHERLES","Spain")
		// };
		
		TreeSet [] sets = new TreeSet[l];

		Aircraft [] aircrafts = new Aircraft[l];

		for(int i=0; i<l;i++){
			aircrafts[i] = new Aircraft(
				"Aircraft"+i,
				r.nextInt(1977, 2010),
				AircraftType.values()[r.nextInt(AircraftType.values().length)], 
				"arc",
				"000"+i);
		}
		
		// Aircraft [] aircrafts = {
		// 	new Aircraft("Flying Saucer", 1999, AircraftType.TWO_ENG, "arc", "0001"),
		// 	new Aircraft("Fat goose", 2005, AircraftType.FOUR_ENG, "arc", "0002"),
		// 	new Aircraft("Stella ", 2010, AircraftType.FOUR_ENG, "zet", "0001"),
		// 	new Aircraft("Sky Queen", 1980, AircraftType.SEV_BLADE_PROP, "kad", "0001"),
		// 	new Aircraft("Robin & Batman", 2000, AircraftType.ONE_BLADE_PROP, "kad", "0005"),
		// 	new Aircraft("Pink heavens", 1997, AircraftType.ONE_ENG, "kad", "0009"),
		// 	new Aircraft("Dizzy wizzy", 2012, AircraftType.ONE_BLADE_PROP, "kad", "0022"),
		// 	new Aircraft("Rainbow candy", 2008, AircraftType.TWO_ENG, "arc", "00021"),
		// 	new Aircraft("Squared light", 2010, AircraftType.FOUR_ENG, "arc", "0033"),
		// 	new Aircraft("Desiree", 2007, AircraftType.TWO_ENG, "zet", "00011"),
		// 	new Aircraft("Candy crush", 2013, AircraftType.TWO_ENG, "zet", "00015"),
		// 	new Aircraft("Pop Corn Wings", 1996, AircraftType.ONE_BLADE_PROP, "arc", "00045"),
		// 	new Aircraft("Flying Kiss", 2001, AircraftType.ONE_BLADE_PROP, "arc", "00046"),
		// 	new Aircraft("Flying French Kiss", 2009, AircraftType.ONE_ENG, "kad", "00046"),
		// 	new Aircraft("Enola Guay", 1977, AircraftType.SEV_BLADE_PROP, "kad", "0099"),
		// };
		
		//Arrays.sort(aircrafts, (a,b)->new java.util.Random().nextInt(10)-5); //shuffle
		
		AircraftID [] unknownIDs = new AircraftID[l];

		for(int i=0; i<l;i++){
			unknownIDs[i] = new AircraftID("arc", "000"+i);
		}

		// AircraftID [] unknownIDs = {
		// 		new AircraftID("arc", "0000"),
		// 		new AircraftID("arc", "0003"),
		// 		new AircraftID("zed", "0001"),
		// 		new AircraftID("zet", "1000"),
		// 		new AircraftID("KAs", "0001"),
		// 		new AircraftID("kad", "0300")
		// };
		
		TreeMap<Company, TreeSet<Aircraft>> all = new TreeMap<Company, TreeSet<Aircraft>>();
		
		for (int i=0; i<companies.length; i++) {
			sets[i] = new TreeSet<Aircraft>();
		}
		
		for (int i=0; i<aircrafts.length; i++) {
			sets[i%companies.length].add(aircrafts[i]);
		}
		
		for (int i=0; i<companies.length; i++) {
			all.put(companies[i], sets[i]);
		}
		
		iniciar("Prova RegisterImp");
		
		informar("creaci� de registre...");
		AirRegisterImp registre = new AirRegisterImp();
		informar("...registre creat\n");
		
		informar("registre de companyies...");
		try {
			for (int i=0; i<companies.length; i++) {
				if (!registre.addCompany(companies[i])) {
					notificarError("addCompany. Resultat incorrecte s'esperava true", SORTIR);
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...registre companyies aparantment ok\n");
		
		//-----------------------------------------------------
		
		informar("registre de companyies repetides...");
		try {
			for (int i=0; i<companies.length; i++) {
				if (registre.addCompany(companies[i])) {
					notificarError("addCompany. Resultat incorrecte s'esperava true", SORTIR);
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...registre de companyies repetides aparentment ok\n");
		
		
		// ---------------------------------------------------------
		
		
		informar("registre d'avions a companyies desconegudes...");
		try {
			int i = 0;
			for (Aircraft aircraft : aircrafts) {
				try {
					registre.registerAircraft(unknownCompanies[i], aircraft);
					notificarError("avio registrat a companyia inexistent. S'esperava excepci� UnknownCompanyException.", SORTIR);
				}
				catch(UnknownCompanyException e) {}
				i = (i+1)%unknownCompanies.length;
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("... registre d'avions a companyies desconegudes aparentment ok\n");
		
		
		
		//-----------------------------------------------------
		
		informar("registre d'avions...");
		try {
			for (Company company : all.keySet()) {
				for (Aircraft aircraft : all.get(company)) {
					if (!registre.registerAircraft(company, aircraft)) {
						notificarError("registerAircraft. Resultat incorrecte. S'esperava true", SORTIR);
					}
 				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...registre d'avions aparantment ok\n");
		
		// ---------------------------------------------------------
		
		informar("Registre d'avions per segona vegada (mateixa companyia)...");
		try {
			for (Company owner : all.keySet()) {
				for (Aircraft aircraft : all.get(owner)) {
					if (registre.registerAircraft(owner, aircraft)) {
						notificarError("registerAircraft. Resultat incorrecte. S'esperava false", SORTIR);
					}
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...Registre d'avions per segona vegada (mateixa companyia) aparentment ok\n");
		
		//----------------------------------------------------------
		
		informar("Registre d'avions per segona vegada (diferent companyia)...");
		try {
			for (int i=0; i<companies.length; i++) {
				Company owner = companies[i];
				Company other = companies[(i+1)%companies.length];
				Set<Aircraft> theRegisteredAircrafts = all.get(owner);
				for (Aircraft aircraft : theRegisteredAircrafts) {
					try {
						registre.registerAircraft(other, aircraft);
						notificarError("avio ja registrat a diferent companyia. S'esperava excepci� DifferentCompanyException.", SORTIR);
					}
					catch(DifferentCompanyException e) {}
				}
				
			}
		} catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...Registre d'avions per segona vegada (diferent companyia) aparentment ok\n");
		
		//----------------------------------------------------------
		informar ("findCompany...");
		try {
			for (Company realCompany : all.keySet()) {
				for (Aircraft aircraft : all.get(realCompany)) {
					Company company = registre.findCompany(new AircraftID(aircraft.getId().getAlpha(), aircraft.getId().getBeta()));
					if (!realCompany.equals(company)) {
						notificarError("findCompany. Resultat incorrecte. Esperat: "+realCompany+" obtingut "+company, SORTIR);
					}
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("... findCompany ha proporcionat els resultats esperats\n");
		
		// -----------------------------------------------------------
		
		informar ("findCompany per a avions (ids) no registrats...");
		try {
			for (AircraftID id : unknownIDs) {
				if (registre.findCompany(id)!=null) {
					notificarError("findCompany ha trobat una companyia per a un avio no registrat"+id, SORTIR);
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar ("...findCompany per a avions (ids) no registrats aparentment ok\n");
		
		
		//-------------------------------------------------------------
		
		informar("findCompanyByType...");
		
		TreeSet<Company> expected = new TreeSet<Company>();
		TreeSet<Aircraft> itsAircrafts;
		SortedSet<Company> result = null;
		
		// iterate over each type of aircraft
		for (AircraftType type : AircraftType.values()) {
			expected.clear();
			for (Company owner : companies) {
				itsAircrafts = all.get(owner);
				for (Aircraft a : itsAircrafts) {
					if (a.getType()==type) {
						expected.add(owner);
					}
				}
			}
	
			try {
				result = registre.findCompanyByType(type);
			}
			catch(Exception e) {
				notificarExcepcio(e, SORTIR);
			}
			
			informar("   verificant "+type+" ...");
			if (!expected.equals(result)) {
				notificarError("findCompanyByType. Resultat incorrecte", SORTIR);
			}
			if (!Arrays.equals(expected.toArray(), result.toArray() )) {
				System.out.println();
				System.out.println(expected);
				System.out.println();
				System.out.println(result);
				notificarError("findCompanyByType. Resultat mal ordenat", SORTIR);
			}
		}
		
		informar("...findCompanyByType comportament aparentment correcte\n");
		
		//-------------------------------------------------------------
				
		informar("registeredAircrafts...");
		try {
			for (Company company : all.keySet()) {
				TreeSet<Aircraft> realSet = all.get(company);
				SortedSet<Aircraft> returned = registre.registeredAircrafts(company);

				if (!realSet.equals(returned)) {
					notificarError("registeredAircrafts ha proporcionat un resultat incorrecte. ESPERAT:\n  " + realSet
							+ "\nOBTINGUT\n  " + returned, SORTIR);
				}

				Aircraft [] obs = realSet.toArray(new Aircraft[0]);
				Arrays.sort(obs, (a,b)->a.getYear()-b.getYear());
				if (!Arrays.equals(obs, returned.toArray())) {
					notificarError("registeredAircrafts ha proporcionat un resultat mal ordenat. ESPERAT:\n  " + Arrays.asList(obs)
							+ "\nOBTINGUT\n  " + returned, SORTIR);
				}
			}
		} catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("... registeredAircrafts ha proporcionat els resultats esperats\n");
		
		finalitzar();
		time = time - System.currentTimeMillis();
		System.out.println("\nExecuted in " + time);
	}

}
