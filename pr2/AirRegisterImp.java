package pr2;

import java.util.*;
import java.util.function.Supplier;

public class AirRegisterImp implements AirRegister {    
    private HashMap<Company, TreeSet<Aircraft>> companies=new HashMap<Company, TreeSet<Aircraft>>();

    @Override
    public boolean addCompany(Company c) {
        if(companies.containsKey(c)) 
        	return false;
        
        companies.put(c, new TreeSet<Aircraft>(Comparator.comparing(Aircraft::getYear)));
        return true;
    }
    @Override
    public boolean registerAircraft(Company c, Aircraft a) {
    	Company t=findCompany(a.getId());
    	if(t!=null && !t.equals(c)) {
    		throw new DifferentCompanyException("Aquest avió ja està registrat en unaltre companyia");
    	}
    	
        if (companies.containsKey(c)) {
            return companies.get(c).add(a);
        }
        throw new UnknownCompanyException("Aquesta companyia no existeix");
    }
    
    @Override
    public SortedSet<Aircraft> registeredAircrafts(Company c) {    	
        return (companies.containsKey(c)) ? companies.get(c) : new TreeSet<Aircraft>();
    }
    
    
    @Override
    public Company findCompany(AircraftID id) {
//    	for (Map.Entry<Company, TreeSet<Aircraft>> contract: companies.entrySet()) {
//			for(Aircraft a:contract.getValue()) 
//				if (a.getId().equals(id)) 
//					return contract.getKey();
//		}
//        return null;
    	SortedSet<Company> result=findC("id", true, id);
    	return (result.size()>0) ? result.first() : null;
    }
    
    @Override
    public SortedSet<Company> findCompanyByType(AircraftType t) { 
//    	SortedSet<Company> tmp=new TreeSet<Company>();
//    	for (Map.Entry<Company, TreeSet<Aircraft>> contract: companies.entrySet()) {
//			for(Aircraft a:contract.getValue()) 
//				if (a.getType().equals(t)) 
//					tmp.add(contract.getKey());
//		}
//        return tmp;
    	
    	return findC("type", false, t);
    }
    
    private SortedSet<Company> findC(String type, boolean firstOccurence, Object subject){
    	SortedSet<Company> tmp=new TreeSet<Company>();
    	for(Map.Entry<Company, TreeSet<Aircraft>> contract: companies.entrySet()) {
			for(Aircraft a:contract.getValue()) {
				if((type.equals("id") ? a.getId():a.getType()).equals(subject)) {	
					tmp.add(contract.getKey());
					
					if (firstOccurence)
						return tmp;
				}
			}
		}
        return tmp;
    }
}