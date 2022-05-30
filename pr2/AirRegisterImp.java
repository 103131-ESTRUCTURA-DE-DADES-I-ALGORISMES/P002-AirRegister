package pr2;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    	for (Map.Entry<Company, TreeSet<Aircraft>> contract: companies.entrySet()) {
    		if(contract.getValue().stream().anyMatch(a->a.getId().equals(id)))
    			return contract.getKey();
    	}
    	return null;
    	
//    	Solucio massa OP
//    	return companies.entrySet().stream().filter(e->e.getValue().stream().anyMatch(a->a.getId().equals(id))).map(e->e.getKey()).findFirst().orElse(null);
    }
    
    @Override
    public SortedSet<Company> findCompanyByType(AircraftType t) { 
    	SortedSet<Company> tmp=new TreeSet<Company>();
    	for (Map.Entry<Company, TreeSet<Aircraft>> contract: companies.entrySet()) {
			for(Aircraft a:contract.getValue()) 
				if (a.getType().equals(t)) tmp.add(contract.getKey());
		}
        return tmp;
    	
//    	Solucio massa OP
//    	return companies.entrySet().stream().filter(e->e.getValue().stream().anyMatch(a->a.getType().equals(t))).map(c->c.getKey()).collect(Collectors.toCollection(TreeSet::new));
    }
}